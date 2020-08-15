package com.tianshuo.thread.aqs;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;


public abstract class AbstractQueuedSynchronizer
        extends AbstractOwnableSynchronizer
        implements java.io.Serializable {
    private static final long serialVersionUID = 7373984972572414691L;

    /**
     * Creates a new {@code AbstractQueuedSynchronizer} instance
     * with initial synchronization state of zero.
     */
    protected AbstractQueuedSynchronizer() { }

    /**
     * Wait queue node class.
     *
     * <p>The wait queue is a variant of a "CLH" (Craig, Landin, and
     * Hagersten) lock queue. CLH locks are normally used for
     * spinlocks.  We instead use them for blocking synchronizers, but
     * use the same basic tactic of holding some of the control
     * information about a thread in the predecessor of its node.  A
     * "status" field in each node keeps track of whether a thread
     * should block.  A node is signalled when its predecessor
     * releases.  Each node of the queue otherwise serves as a
     * specific-notification-style monitor holding a single waiting
     * thread. The status field does NOT control whether threads are
     * granted locks etc though.  A thread may try to acquire if it is
     * first in the queue. But being first does not guarantee success;
     * it only gives the right to contend.  So the currently released
     * contender thread may need to rewait.
     *
     * <p>To enqueue into a CLH lock, you atomically splice it in as new
     * tail. To dequeue, you just set the head field.
     * <pre>
     *      +------+  prev +-----+       +-----+
     * head |      | <---- |     | <---- |     |  tail
     *      +------+       +-----+       +-----+
     * </pre>
     *
     * <p>Insertion into a CLH queue requires only a single atomic
     * operation on "tail", so there is a simple atomic point of
     * demarcation from unqueued to queued. Similarly, dequeuing
     * involves only updating the "head". However, it takes a bit
     * more work for nodes to determine who their successors are,
     * in part to deal with possible cancellation due to timeouts
     * and interrupts.
     *
     * <p>The "prev" links (not used in original CLH locks), are mainly
     * needed to handle cancellation. If a node is cancelled, its
     * successor is (normally) relinked to a non-cancelled
     * predecessor. For explanation of similar mechanics in the case
     * of spin locks, see the papers by Scott and Scherer at
     * http://www.cs.rochester.edu/u/scott/synchronization/
     *
     * <p>We also use "next" links to implement blocking mechanics.
     * The thread id for each node is kept in its own node, so a
     * predecessor signals the next node to wake up by traversing
     * next link to determine which thread it is.  Determination of
     * successor must avoid races with newly queued nodes to set
     * the "next" fields of their predecessors.  This is solved
     * when necessary by checking backwards from the atomically
     * updated "tail" when a node's successor appears to be null.
     * (Or, said differently, the next-links are an optimization
     * so that we don't usually need a backward scan.)
     *
     * <p>Cancellation introduces some conservatism to the basic
     * algorithms.  Since we must poll for cancellation of other
     * nodes, we can miss noticing whether a cancelled node is
     * ahead or behind us. This is dealt with by always unparking
     * successors upon cancellation, allowing them to stabilize on
     * a new predecessor, unless we can identify an uncancelled
     * predecessor who will carry this responsibility.
     *
     * <p>CLH queues need a dummy header node to get started. But
     * we don't create them on construction, because it would be wasted
     * effort if there is never contention. Instead, the node
     * is constructed and head and tail pointers are set upon first
     * contention.
     *
     * <p>Threads waiting on Conditions use the same nodes, but
     * use an additional link. Conditions only need to link nodes
     * in simple (non-concurrent) linked queues because they are
     * only accessed when exclusively held.  Upon await, a node is
     * inserted into a condition queue.  Upon signal, the node is
     * transferred to the main queue.  A special value of status
     * field is used to mark which queue a node is on.
     *
     * <p>Thanks go to Dave Dice, Mark Moir, Victor Luchangco, Bill
     * Scherer and Michael Scott, along with members of JSR-166
     * expert group, for helpful ideas, discussions, and critiques
     * on the design of this class.
     */
    static final class Node {
        /**
         * 标记节点为共享模式
         * */
        static final Node SHARED = new Node();
        /**
         *  标记节点为独占模式
         */
        static final Node EXCLUSIVE = null;

        /**
         * 在同步队列中等待的线程等待超时或者被中断，需要从同步队列中取消等待
         * */
        static final int CANCELLED =  1;
        /**
         *  后继节点的线程处于等待状态，而当前的节点如果释放了同步状态或者被取消，
         *  将会通知后继节点，使后继节点的线程得以运行。
         */
        static final int SIGNAL    = -1;
        /**
         *  节点在等待队列中，节点的线程等待在Condition上，当其他线程对Condition调用了signal()方法后，
         *  该节点会从等待队列中转移到同步队列中，加入到同步状态的获取中
         */
        static final int CONDITION = -2;
        /**
         * 表示下一次共享式同步状态获取将会被无条件地传播下去
         */
        static final int PROPAGATE = -3;

        /**
         * 标记当前节点的信号量状态 (1,0,-1,-2,-3)5种状态
         * 使用CAS更改状态，volatile保证线程可见性，高并发场景下，
         * 即被一个线程修改后，状态会立马让其他线程可见。
         */
        volatile int waitStatus;

        /**
         * 前驱节点，当前节点加入到同步队列中被设置
         */
        volatile Node prev;

        /**
         * 后继节点
         */
        volatile Node next;

        /**
         * 节点同步状态的线程
         */
        volatile Thread thread;

        /**
         * 等待队列中的后继节点，如果当前节点是共享的，那么这个字段是一个SHARED常量，
         * 也就是说节点类型(独占和共享)和等待队列中的后继节点共用同一个字段。
         */
        Node nextWaiter;

        /**
         * Returns true if node is waiting in shared mode.
         */
        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        /**
         * 返回前驱节点
         */
        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        Node() {    // Used to establish initial head or SHARED marker
        }

        Node(Thread thread, Node mode) {     // Used by addWaiter
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) { // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }

    /**
     * 指向同步等待队列的头节点
     */
    private transient volatile Node head;

    /**
     * 指向同步等待队列的尾节点
     */
    private transient volatile Node tail;

    /**
     * 同步资源状态
     */
    private volatile int state;

    /**
     * Returns the current value of synchronization state.
     * This operation has memory semantics of a {@code volatile} read.
     * @return current state value
     */
    protected final int getState() {
        return state;
    }

    /**
     * Sets the value of synchronization state.
     * This operation has memory semantics of a {@code volatile} write.
     * @param newState the new state value
     */
    protected final void setState(int newState) {
        state = newState;
    }

    /**
     * Atomically sets synchronization state to the given updated
     * value if the current state value equals the expected value.
     * This operation has memory semantics of a {@code volatile} read
     * and write.
     *
     * @param expect the expected value
     * @param update the new value
     * @return {@code true} if successful. False return indicates that the actual
     *         value was not equal to the expected value.
     */
    protected final boolean compareAndSetState(int expect, int update) {
        // See below for intrinsics setup to support this
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    // Queuing utilities

    /**
     * The number of nanoseconds for which it is faster to spin
     * rather than to use timed park. A rough estimate suffices
     * to improve responsiveness with very short timeouts.
     */
    static final long spinForTimeoutThreshold = 1000L;

    /**
     * 1、自旋，判断尾部节点为空，表示当前队列还未被初始化
     * 2、new Node ，使用Cas操作让head指向新创建的Node节点，同时尾部节点也执行head
     * 3、传递过来的Node节点的前驱节点指向新创建的Node，也就是head，然后使用Cas操作，让队列Tail指向传递过来的Node
     * 然后返回
     *
     * 节点加入CLH同步队列
     */
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                //队列为空需要初始化，创建空的头节点 head = new Node();
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                //当前的节点前驱节点 = tail ；
                node.prev = t;
                //set尾部节点   tail = node;
                if (compareAndSetTail(t, node)) {//当前节点置为尾部
                    t.next = node; //前驱节点的next指针指向当前节点
                    return t;
                }
            }
        }
    }

    /**
     *
     * 1、创建一个节点，节点的线程为当前线程
     * 2、判断队列是否初始化，如果已经初始化，使用Cas操作把当前节点加入到队列的尾部
     * 3、如果当前队列未被初始化，创建空属性head，同事把当前节点加入到head之后
     *
     * Creates and enqueues node for current thread and given mode.
     *
     * @param mode Node.EXCLUSIVE for exclusive, Node.SHARED for shared
     * @return the new node
     */
    private Node addWaiter(Node mode) {
        // 1. 将当前线程构建成Node  排他类型
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        // 2. 1 当前节点不为空，队列已经初始化完成了，直接将数据用尾插法，放入队列的末尾
        if (pred != null) {
            // 2.2 将当前节点尾插入的方式
            node.prev = pred;
            // 2.3 CAS将节点插入同步队列的尾部  与 hashQueueProcessors 里边的  (s=h.next)==null呼应
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        /**
         * 如果队列还未初始化，Node入队列
         */
        enq(node);
        return node;
    }

    /**
     * Sets head of queue to be node, thus dequeuing. Called only by
     * acquire methods.  Also nulls out unused fields for sake of GC
     * and to suppress unnecessary signals and traversals.
     *
     * @param node the node
     */
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    /**
     *  判断node的信号量，如果信号量小于0，Cas更新为0
     *  如果如果不小于0，判断当前节点后继节点是否为null或者信号量为0，如果为0，设置null，移除
     *  然后从tail开始向前找，如果找到信号量小于0的node节点，然后解除阻塞。
     *
     */
    private void unparkSuccessor(Node node) {
        //获取wait状态
        int ws = node.waitStatus;
        /**
         * head能唤醒下级节点，此时head的ws肯定为-1，此处为什么要再次改成0呢？
         *
         * 目的其实很简单，这个操作在公平锁的情况下没有作用.
         * 但是在非公平锁的情况下，head后面的节点在获取的锁的时候(公平锁的情况下肯定能获得锁，因为其他的节点都被park了)
         * 很有可能会失败，此时head节点后面的这个要被重新阻塞，这个节点要能被正常阻塞，就要把head的ws从0，改为-1，
         * 如果unparkSuccessor方法没有head的ws重新设置为0，那么head后面的这个节点在拿锁失败之后，这个节点就不可能再被唤醒了。
         */
        if (ws < 0)
            // 将等待状态waitStatus设置为初始值0
            compareAndSetWaitStatus(node, ws, 0);

        /**
         * 若后继结点为空，或状态为CANCEL（已失效），
         * 则从后尾部往前遍历找到最前的一个处于正常阻塞状态的结点唤醒
         *
         */
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
            LockSupport.unpark(s.thread);//唤醒线程
    }

    /**
     * 把当前结点设置为SIGNAL或者PROPAGATE
     * 唤醒head.next(B节点)，B节点唤醒后可以竞争锁，成功后head->B，然后又会唤醒B.next，一直重复直到共享节点都唤醒
     * head节点状态为SIGNAL，重置head.waitStatus->0，唤醒head节点线程，唤醒后线程去竞争共享锁
     * head节点状态为0，将head.waitStatus->Node.PROPAGATE传播状态，表示需要将状态向后继节点传播
     */
    private void doReleaseShared() {
        for (;;) {
            Node h = head;
            if (h != null && h != tail) {
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL) {//head是SIGNAL状态
                    /* head状态是SIGNAL，重置head节点waitStatus为0，这里不直接设为Node.PROPAGATE,
                     * 是因为unparkSuccessor(h)中，如果ws < 0会设置为0，所以ws先设置为0，再设置为PROPAGATE
                     * 这里需要控制并发，因为入口有setHeadAndPropagate跟release两个，避免两次unpark
                     */
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                        continue; //设置失败，重新循环
                    /* head状态为SIGNAL，且成功设置为0之后,唤醒head.next节点线程
                     * 此时head、head.next的线程都唤醒了，head.next会去竞争锁，成功后head会指向获取锁的节点，
                     * 也就是head发生了变化。看最底下一行代码可知，head发生变化后会重新循环，继续唤醒head的下一个节点
                     */
                    unparkSuccessor(h);
                    /*
                     * 如果本身头节点的waitStatus是出于重置状态（waitStatus==0）的，将其设置为“传播”状态。
                     * 意味着需要将状态向后一个节点传播
                     */
                }
                else if (ws == 0 &&
                        !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                    continue;                // loop on failed CAS
            }
            if (h == head) //如果head变了，重新循环
                break;
        }
    }

    /**
     * 把node节点设置成head节点，且Node.waitStatus->Node.PROPAGATE
     */
    private void setHeadAndPropagate(Node node, int propagate) {
        Node h = head; //h用来保存旧的head节点
        setHead(node);//head引用指向node节点
        /* 这里意思有两种情况是需要执行唤醒操作
         * 1.propagate > 0 表示调用方指明了后继节点需要被唤醒
         * 2.头节点后面的节点需要被唤醒（waitStatus<0），不论是老的头结点还是新的头结点
         */
        if (propagate > 0 || h == null || h.waitStatus < 0 ||
                (h = head) == null || h.waitStatus < 0) {
            Node s = node.next;
            if (s == null || s.isShared())//node是最后一个节点或者 node的后继节点是共享节点
                /* 如果head节点状态为SIGNAL，唤醒head节点线程，重置head.waitStatus->0
                 * head节点状态为0(第一次添加时是0)，设置head.waitStatus->Node.PROPAGATE表示状态需要向后继节点传播
                 */
                doReleaseShared();
        }
    }

    // Utilities for various versions of acquire

    /**
     *
     * 能进入此方法，说明节点在获取锁的过程中被stop了或者报错了
     * 1、此时判断当前节点是否为空，如果为空return;
     * 2、把当前节点的thread属性设置为空
     * 3、如果从当前节点往前循环，移除队列中的ws为CANCELED的节点，确保当前节点的前驱节点状态 <0
     * 4、设置当前节点的状态为CANCELED
     * 5、判断当前节点是否是尾节点，如果是直接移除，然后把当前节点的前驱节点设置为尾节点
     * 6、如果不是尾节点，如果时head节点，那么就是直接唤醒下个节点。
     * 7、如果也不是head节点，则获取当前节点下个节点，如果下个节点状态<0或者能被改成-1,然后就当前节点下级节点，放到他的上级节点后面
     * （其实也是相当删除自己了)
     * Cancels an ongoing attempt to acquire.
     *
     * @param node the node
     */
    private void cancelAcquire(Node node) {

        // Ignore if node doesn't exist
        if (node == null)
            return;

        node.thread = null;

        // Skip cancelled predecessors
        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;

        // predNext is the apparent node to unsplice. CASes below will
        // fail if not, in which case, we lost race vs another cancel
        // or signal, so no further action is necessary.
        Node predNext = pred.next;

        // Can use unconditional write instead of CAS here.
        // After this atomic step, other Nodes can skip past us.
        // Before, we are free of interference from other threads.
        node.waitStatus = Node.CANCELLED;

        // If we are the tail, remove ourselves.
        //如果节点是尾结点，直接用cas方式移除，同时把tail指针指向pred
        if (node == tail && compareAndSetTail(node, pred)) {
            //使用方式pred的next节点设置为null
            compareAndSetNext(pred, predNext, null);
        } else {
            // If successor needs signal, try to set pred's next-link
            // so it will get one. Otherwise wake it up to propagate.
            int ws;
            //1、如果pred接待不是头结点，同时的他的信号量是-1或者<=能用cas设置为-1并且pred的线程不为空
            //2、此时获取他的下个节点，如果下个节点不为空并且信号量小于等于0
            //3、使用cas操作，把pred的下个节点设置为node的next节点
            if (pred != head &&
                    ((ws = pred.waitStatus) == Node.SIGNAL ||
                            (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                    pred.thread != null) {
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    compareAndSetNext(pred, predNext, next);
            } else {
                //如果pred节点是head节点，直接唤醒下个一个节点
                unparkSuccessor(node);
            }
            //代码走到这里，让这个节点自己执行自己
            node.next = node; // help GC
        }
    }

    /**
     *
     * 检查和更新前驱节点的信号量
     *    a、如果前驱节点的信号量==-1 如果是直接返回true
     *    b、如果>0 前驱节点是被中断或者被取消的状态，此时从此节点开始向前开始找信号量<=0的节点
     *    ，(从队列中移除所有中断或者被取消的节点)，  然后把当前挂载这样的节点的后边，返回false
     *    c、如果<0 使用Cas操作把这个前驱节点的状态修改为-1，Cas操作成功会返回true，操作失败之后返回false
     *
     *  如果返回false之后，会继续自旋，返回true之后，线程才能被安全park
     *
     * Checks and updates status for a node that failed to acquire.
     * Returns true if thread should block. This is the main signal
     * control in all acquire loops.  Requires that pred == node.prev.
     *
     * @param pred node's predecessor holding status
     * @param node the node
     * @return {@code true} if thread should block
     */
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        /**
         * 判断前驱节点的等待状态，如果状态signal，直接返回true
         */
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            /*
             * 若前驱结点的状态是SIGNAL（-1），意味着当前结点可以被安全地park
             */
            return true;
        if (ws > 0) {
            /*
             * 循环判断所有的前驱节点的等待状态，如果是1（取消，或者线程被中断的状态），将此节点移除队列
             * 然后将当前节点，加入到的剩下的节点的后边
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * 当前驱节点waitStatus为 0 or PROPAGATE（传播状态）状态时
             * 将其设置为SIGNAL状态，然后当前结点才可以可以被安全地park
             * 1、能上级节点的waitStatus改成-1，说明当前节点目前是正常运行，是可以被唤醒
             * 2、当然从另外一个方面来说，下级节点的状态能否被唤醒这个状态记录在上级节点，上级节点在唤醒下级节点的时候，就非常
             * 方便
             */
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    /**
     * 中断当前线程  重新设置中断状态
     * Convenience method to interrupt current thread.
     */
    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    /**
     * 阻塞当前节点，返回当前Thread的中断状态
     * LockSupport.park 底层实现逻辑调用系统内核功能 pthread_mutex_lock 阻塞线程
     */
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);//阻塞
        //测试是否中断，同时重置中断状态为false
        return Thread.interrupted();
    }

    /*
     * Various flavors of acquire, varying in exclusive/shared and
     * control modes.  Each is mostly the same, but annoyingly
     * different.  Only a little bit of factoring is possible due to
     * interactions of exception mechanics (including ensuring that we
     * cancel if tryAcquire throws exception) and other control, at
     * least not without hurting performance too much.
     */

    /**
     * 已经在队列当中的Thread节点，准备阻塞等待获取锁
     *
     * 1、只有当线程的前驱节点为head时，线程才可以尝试获取锁
     *
     * 2、否则的话，需要判断前驱节点的状态是否为-1，直接返回true ，执行阻塞当前线程
     * 如果大于-1的，需要向前寻找知道找一个前驱节点为-1的节点，然后让这个前驱节点的后继指向自己，return false 继续自旋尝试
     * 如果小于-1，使用Cas操作把前驱节点信号量设置为-1 ，return false; 继续自旋
     *
     *
     */
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            /**
             * 自旋判断当前的节点的前驱节点是否是head
             * 如果当前节点的前置节点是的head的话，说明队列只有他一个人
             * 高并发的场景下，有可能这个的第一个拿到锁的线程已经的释放了锁，此时可以再尝试是否可以获取到锁
             */
            for (;;) {//死循环
                //找到当前结点的前驱结点
                final Node p = node.predecessor();
                /**
                 * 1、如果前驱结点是头结点，才tryAcquire，其他结点是没有机会tryAcquire的。
                 * 2、因为在高并发的场景下有一定的可能，此时第一个线程已经释放了锁
                 *
                 */
                if (p == head && tryAcquire(arg)) {
                    /**
                     * head节点在唤醒后继节点时，没有被移除队列，那么他什么时候，被移除的呢？
                     * 答案：
                     * 就在后继节点获取到了锁之后，就把让head指针直接指向自己，然后把prev属性和thread属性设置为空
                     * 同时还是会让原head节点的next指向空，此时原head节点的就变成一个没有引用的空对象，会被gc回收掉
                     */

                    //获取锁成功，让head节点的指针指向当前节点，同时清空thread属性，及prev属性
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                /**
                 * 如果前驱节点不是Head，通过shouldParkAfterFailedAcquire判断是否应该阻塞
                 * 必须前驱节点信号量为-1，当前线程才可以安全被parkAndCheckInterrupt用来阻塞线程
                 */
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            //线程stop之后（强制停止），会出现异常，此时会执行此方法。
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 与acquireQueued逻辑相似，唯一区别节点还不在队列当中需要先进行入队操作
     */
    private void doAcquireInterruptibly(int arg)
            throws InterruptedException {
        final Node node = addWaiter(Node.EXCLUSIVE);//以独占模式放入队列尾部
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 独占模式定时获取
     */
    private boolean doAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.EXCLUSIVE);//加入队列
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return true;
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;//超时直接返回获取失败
                if (shouldParkAfterFailedAcquire(p, node) &&
                        nanosTimeout > spinForTimeoutThreshold)
                    //阻塞指定时长，超时则线程自动被唤醒
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())//当前线程中断状态
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 尝试获取共享锁
     */
    private void doAcquireShared(int arg) {
        final Node node = addWaiter(Node.SHARED);//入队
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();//前驱节点
                if (p == head) {
                    int r = tryAcquireShared(arg); //非公平锁实现，再尝试获取锁
                    //state==0时tryAcquireShared会返回>=0(CountDownLatch中返回的是1)。
                    // state为0说明共享次数已经到了，可以获取锁了
                    if (r >= 0) {//r>0表示state==0,前继节点已经释放锁，锁的状态为可被获取
                        //这一步设置node为head节点设置node.waitStatus->Node.PROPAGATE，然后唤醒node.thread
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                        return;
                    }
                }
                //前继节点非head节点，将前继节点状态设置为SIGNAL，通过park挂起node节点的线程
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Acquires in shared interruptible mode.
     * @param arg the acquire argument
     */
    private void doAcquireSharedInterruptibly(int arg)
            throws InterruptedException {
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Acquires in shared timed mode.
     *
     * @param arg the acquire argument
     * @param nanosTimeout max wait time
     * @return {@code true} if acquired
     */
    private boolean doAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return true;
                    }
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) &&
                        nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    // Main exported methods

    /**
     * Attempts to acquire in exclusive mode. This method should query
     * if the state of the object permits it to be acquired in the
     * exclusive mode, and if so to acquire it.
     *
     * <p>This method is always invoked by the thread performing
     * acquire.  If this method reports failure, the acquire method
     * may queue the thread, if it is not already queued, until it is
     * signalled by a release from some other thread. This can be used
     * to implement method {@link Lock#tryLock()}.
     *
     * <p>The default
     * implementation throws {@link UnsupportedOperationException}.
     *
     * @param arg the acquire argument. This value is always the one
     *        passed to an acquire method, or is the value saved on entry
     *        to a condition wait.  The value is otherwise uninterpreted
     *        and can represent anything you like.
     * @return {@code true} if successful. Upon success, this object has
     *         been acquired.
     * @throws IllegalMonitorStateException if acquiring would place this
     *         synchronizer in an illegal state. This exception must be
     *         thrown in a consistent fashion for synchronization to work
     *         correctly.
     * @throws UnsupportedOperationException if exclusive mode is not supported
     */
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to set the state to reflect a release in exclusive
     * mode.
     *
     * <p>This method is always invoked by the thread performing release.
     *
     * <p>The default implementation throws
     * {@link UnsupportedOperationException}.
     *
     * @param arg the release argument. This value is always the one
     *        passed to a release method, or the current state value upon
     *        entry to a condition wait.  The value is otherwise
     *        uninterpreted and can represent anything you like.
     * @return {@code true} if this object is now in a fully released
     *         state, so that any waiting threads may attempt to acquire;
     *         and {@code false} otherwise.
     * @throws IllegalMonitorStateException if releasing would place this
     *         synchronizer in an illegal state. This exception must be
     *         thrown in a consistent fashion for synchronization to work
     *         correctly.
     * @throws UnsupportedOperationException if exclusive mode is not supported
     */
    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 共享式：共享式地获取同步状态。对于独占式同步组件来讲，同一时刻只有一个线程能获取到同步状态，
     * 其他线程都得去排队等待，其待重写的尝试获取同步状态的方法tryAcquire返回值为boolean，这很容易理解；
     * 对于共享式同步组件来讲，同一时刻可以有多个线程同时获取到同步状态，这也是“共享”的意义所在。
     * 本方法待被之类覆盖实现具体逻辑
     *  1.当返回值大于0时，表示获取同步状态成功，同时还有剩余同步状态可供其他线程获取；
     *
     *　2.当返回值等于0时，表示获取同步状态成功，但没有可用同步状态了；

     *　3.当返回值小于0时，表示获取同步状态失败。
     */
    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to set the state to reflect a release in shared mode.
     *
     * <p>This method is always invoked by the thread performing release.
     *
     * <p>The default implementation throws
     * {@link UnsupportedOperationException}.
     *
     * @param arg the release argument. This value is always the one
     *        passed to a release method, or the current state value upon
     *        entry to a condition wait.  The value is otherwise
     *        uninterpreted and can represent anything you like.
     * @return {@code true} if this release of shared mode may permit a
     *         waiting acquire (shared or exclusive) to succeed; and
     *         {@code false} otherwise
     * @throws IllegalMonitorStateException if releasing would place this
     *         synchronizer in an illegal state. This exception must be
     *         thrown in a consistent fashion for synchronization to work
     *         correctly.
     * @throws UnsupportedOperationException if shared mode is not supported
     */
    protected boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns {@code true} if synchronization is held exclusively with
     * respect to the current (calling) thread.  This method is invoked
     * upon each call to a non-waiting {@link ConditionObject} method.
     * (Waiting methods instead invoke {@link #release}.)
     *
     * <p>The default implementation throws {@link
     * UnsupportedOperationException}. This method is invoked
     * internally only within {@link ConditionObject} methods, so need
     * not be defined if conditions are not used.
     *
     * @return {@code true} if synchronization is held exclusively;
     *         {@code false} otherwise
     * @throws UnsupportedOperationException if conditions are not supported
     */
    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * 尝试获取的获取锁，获取锁失败，阻塞当前线程，同时中断此线程。
     *
     * Acquires in exclusive mode, ignoring interrupts.  Implemented
     * by invoking at least once {@link #tryAcquire},
     * returning on success.  Otherwise the thread is queued, possibly
     * repeatedly blocking and unblocking, invoking {@link
     * #tryAcquire} until success.  This method can be used
     * to implement method {@link Lock#lock}.
     *
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquire} but is otherwise uninterpreted and
     *        can represent anything you like.
     */
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
                acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }

    /**
     * Acquires in exclusive mode, aborting if interrupted.
     * Implemented by first checking interrupt status, then invoking
     * at least once {@link #tryAcquire}, returning on
     * success.  Otherwise the thread is queued, possibly repeatedly
     * blocking and unblocking, invoking {@link #tryAcquire}
     * until success or the thread is interrupted.  This method can be
     * used to implement method {@link Lock#lockInterruptibly}.
     *
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquire} but is otherwise uninterpreted and
     *        can represent anything you like.
     * @throws InterruptedException if the current thread is interrupted
     */
    public final void acquireInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (!tryAcquire(arg))
            doAcquireInterruptibly(arg);
    }

    /**
     * Attempts to acquire in exclusive mode, aborting if interrupted,
     * and failing if the given timeout elapses.  Implemented by first
     * checking interrupt status, then invoking at least once {@link
     * #tryAcquire}, returning on success.  Otherwise, the thread is
     * queued, possibly repeatedly blocking and unblocking, invoking
     * {@link #tryAcquire} until success or the thread is interrupted
     * or the timeout elapses.  This method can be used to implement
     * method {@link Lock#tryLock(long, TimeUnit)}.
     *
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquire} but is otherwise uninterpreted and
     *        can represent anything you like.
     * @param nanosTimeout the maximum number of nanoseconds to wait
     * @return {@code true} if acquired; {@code false} if timed out
     * @throws InterruptedException if the current thread is interrupted
     */
    public final boolean tryAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquire(arg) ||
                doAcquireNanos(arg, nanosTimeout);
    }

    /**
     * 释放独占模式持有的锁
     */
    public final boolean release(int arg) {
        //如果锁已经释放完成
        //如果head不为null并且head的信号量不等于0，唤醒后继节点
        if (tryRelease(arg)) {//释放一次锁
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);//唤醒后继结点
            return true;
        }
        return false;
    }

    /**
     * 请求获取共享锁
     */
    public final void acquireShared(int arg) {
        if (tryAcquireShared(arg) < 0)//返回值小于0，获取同步状态失败，排队去；获取同步状态成功，直接返回去干自己的事儿。
            doAcquireShared(arg);
    }

    /**
     * Acquires in shared mode, aborting if interrupted.  Implemented
     * by first checking interrupt status, then invoking at least once
     * {@link #tryAcquireShared}, returning on success.  Otherwise the
     * thread is queued, possibly repeatedly blocking and unblocking,
     * invoking {@link #tryAcquireShared} until success or the thread
     * is interrupted.
     * @param arg the acquire argument.
     * This value is conveyed to {@link #tryAcquireShared} but is
     * otherwise uninterpreted and can represent anything
     * you like.
     * @throws InterruptedException if the current thread is interrupted
     */
    public final void acquireSharedInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (tryAcquireShared(arg) < 0)
            doAcquireSharedInterruptibly(arg);
    }

    /**
     * Attempts to acquire in shared mode, aborting if interrupted, and
     * failing if the given timeout elapses.  Implemented by first
     * checking interrupt status, then invoking at least once {@link
     * #tryAcquireShared}, returning on success.  Otherwise, the
     * thread is queued, possibly repeatedly blocking and unblocking,
     * invoking {@link #tryAcquireShared} until success or the thread
     * is interrupted or the timeout elapses.
     *
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquireShared} but is otherwise uninterpreted
     *        and can represent anything you like.
     * @param nanosTimeout the maximum number of nanoseconds to wait
     * @return {@code true} if acquired; {@code false} if timed out
     * @throws InterruptedException if the current thread is interrupted
     */
    public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquireShared(arg) >= 0 ||
                doAcquireSharedNanos(arg, nanosTimeout);
    }

    /**
     * Releases in shared mode.  Implemented by unblocking one or more
     * threads if {@link #tryReleaseShared} returns true.
     *
     * @param arg the release argument.  This value is conveyed to
     *        {@link #tryReleaseShared} but is otherwise uninterpreted
     *        and can represent anything you like.
     * @return the value returned from {@link #tryReleaseShared}
     */
    public final boolean releaseShared(int arg) {
        if (tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }

    // Queue inspection methods

    /**
     * Queries whether any threads are waiting to acquire. Note that
     * because cancellations due to interrupts and timeouts may occur
     * at any time, a {@code true} return does not guarantee that any
     * other thread will ever acquire.
     *
     * <p>In this implementation, this operation returns in
     * constant time.
     *
     * @return {@code true} if there may be other threads waiting to acquire
     */
    public final boolean hasQueuedThreads() {
        return head != tail;
    }

    /**
     * Queries whether any threads have ever contended to acquire this
     * synchronizer; that is if an acquire method has ever blocked.
     *
     * <p>In this implementation, this operation returns in
     * constant time.
     *
     * @return {@code true} if there has ever been contention
     */
    public final boolean hasContended() {
        return head != null;
    }

    /**
     * Returns the first (longest-waiting) thread in the queue, or
     * {@code null} if no threads are currently queued.
     *
     * <p>In this implementation, this operation normally returns in
     * constant time, but may iterate upon contention if other threads are
     * concurrently modifying the queue.
     *
     * @return the first (longest-waiting) thread in the queue, or
     *         {@code null} if no threads are currently queued
     */
    public final Thread getFirstQueuedThread() {
        // handle only fast path, else relay
        return (head == tail) ? null : fullGetFirstQueuedThread();
    }

    /**
     * Version of getFirstQueuedThread called when fastpath fails
     */
    private Thread fullGetFirstQueuedThread() {
        /*
         * The first node is normally head.next. Try to get its
         * thread field, ensuring consistent reads: If thread
         * field is nulled out or s.prev is no longer head, then
         * some other thread(s) concurrently performed setHead in
         * between some of our reads. We try this twice before
         * resorting to traversal.
         */
        Node h, s;
        Thread st;
        if (((h = head) != null && (s = h.next) != null &&
                s.prev == head && (st = s.thread) != null) ||
                ((h = head) != null && (s = h.next) != null &&
                        s.prev == head && (st = s.thread) != null))
            return st;

        /*
         * Head's next field might not have been set yet, or may have
         * been unset after setHead. So we must check to see if tail
         * is actually first node. If not, we continue on, safely
         * traversing from tail back to head to find first,
         * guaranteeing termination.
         */

        Node t = tail;
        Thread firstThread = null;
        while (t != null && t != head) {
            Thread tt = t.thread;
            if (tt != null)
                firstThread = tt;
            t = t.prev;
        }
        return firstThread;
    }

    /**
     * 判断当前线程是否在队列当中
     */
    public final boolean isQueued(Thread thread) {
        if (thread == null)
            throw new NullPointerException();
        for (Node p = tail; p != null; p = p.prev)
            if (p.thread == thread)
                return true;
        return false;
    }

    /**
     * Returns {@code true} if the apparent first queued thread, if one
     * exists, is waiting in exclusive mode.  If this method returns
     * {@code true}, and the current thread is attempting to acquire in
     * shared mode (that is, this method is invoked from {@link
     * #tryAcquireShared}) then it is guaranteed that the current thread
     * is not the first queued thread.  Used only as a heuristic in
     * ReentrantReadWriteLock.
     */
    final boolean apparentlyFirstQueuedIsExclusive() {
        Node h, s;
        return (h = head) != null &&
                (s = h.next)  != null &&
                !s.isShared()         &&
                s.thread != null;
    }

    /**
     * 判断队列是否初始化,未初始化，尝试加锁
     * 如果已经初始化，判断队列中的第二个节点是否是自己，如果不是自己，去排队，如果是自己如尝试加锁
     * 如果队列已经初始化，对列中有第二节点，但是第二个节点还没有完全插入，那肯定不是自己，直接去排队
     */
    public final boolean hasQueuedPredecessors() {
        // Read fields in reverse initialization order
        Node t = tail;
        Node h = head;
        Node s;

        //1、如果h!=t == false 此时队列没有初始化，直接去获取锁
        //2、如果h!=t == true ,接着判断s == null == false 此时队列中的不止有一个节点 ，接着判断当前线程是不是排在对头的节点s.thread != Thread.currentThread()
        // 如果是的话，也不需要排队，如果不是就去排队
        //3、如果h!=t && (s=h.next) == null 但是线程在执行cas设置队尾操作时还未完成时,肯定不是自己，直接去排队

        return h != t &&
                ((s = h.next) == null || s.thread != Thread.currentThread());
    }


    // Instrumentation and monitoring methods

    /**
     * 同步队列长度
     */
    public final int getQueueLength() {
        int n = 0;
        for (Node p = tail; p != null; p = p.prev) {
            if (p.thread != null)
                ++n;
        }
        return n;
    }

    /**
     * 获取队列等待thread集合
     */
    public final Collection<Thread> getQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            Thread t = p.thread;
            if (t != null)
                list.add(t);
        }
        return list;
    }

    /**
     * 获取独占模式等待thread线程集合
     */
    public final Collection<Thread> getExclusiveQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (!p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    /**
     * 获取共享模式等待thread集合
     */
    public final Collection<Thread> getSharedQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    /**
     * Returns a string identifying this synchronizer, as well as its state.
     * The state, in brackets, includes the String {@code "State ="}
     * followed by the current value of {@link #getState}, and either
     * {@code "nonempty"} or {@code "empty"} depending on whether the
     * queue is empty.
     *
     * @return a string identifying this synchronizer, as well as its state
     */
    public String toString() {
        int s = getState();
        String q  = hasQueuedThreads() ? "non" : "";
        return super.toString() +
                "[State = " + s + ", " + q + "empty queue]";
    }


    // Internal support methods for Conditions

    /**
     * 判断节点是否在同步队列中
     */
    final boolean isOnSyncQueue(Node node) {
        //快速判断1：节点状态或者节点没有前置节点
        //注：同步队列是有头节点的，而条件队列没有
        if (node.waitStatus == Node.CONDITION || node.prev == null)
            return false;
        //快速判断2：next字段只有同步队列才会使用，条件队列中使用的是nextWaiter字段
        if (node.next != null) // If has successor, it must be on queue
            return true;
        //上面如果无法判断则进入复杂判断
        return findNodeFromTail(node);
    }

    /**
     * 只能从尾部开始循环，因为head节点一定为空，
     * 如果从头判断很大概率会死循环，因为tail节点很大概率不为空，只有当队列只有一个元素时，此时head==tail==null，条件太苛刻
     * 无法作为自旋停止条件
     * 1、从尾部往头部查找，找到匹配的元素之后返回true，退出循环
     * 2、当上级节点为null是退出循环
     * Returns true if node is on sync queue by searching backwards from tail.
     * Called only when needed by isOnSyncQueue.
     * @return true if present
     */
    private boolean findNodeFromTail(Node node) {
        Node t = tail;
        for (;;) {
            if (t == node)
                return true;
            if (t == null)
                return false;
            t = t.prev;
        }
    }

    /**
     * 将节点从条件队列当中移动到同步队列当中，等待获取锁
     *
     * 1、如果把Node.CONDITION 改为初始化状态0失败，直接返回false
     * 2、入队成功，返回前驱节点
     * 如果前驱节点ws>0及是cancel状态，或者修改前驱节点的状态为-1失败，
     * 此时直接唤醒当前线程。
     * 然后返回true
     */
    final boolean transferForSignal(Node node) {
        /*
         * 修改节点信号量状态为0，失败直接返回false
         */
        if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
            return false;

        /*
         * 加入同步队列尾部当中，返回前驱节点
         */
        Node p = enq(node);
        int ws = p.waitStatus;
        //前驱节点不可用 或者 修改信号量状态失败
        if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
            LockSupport.unpark(node.thread); //唤醒当前节点
        return true;
    }

    /**
     * Transfers node, if necessary, to sync queue after a cancelled wait.
     * Returns true if thread was cancelled before being signalled.
     *
     * @param node the node
     * @return true if cancelled before the node was signalled
     */
    final boolean transferAfterCancelledWait(Node node) {
        /**
         * 把等待状态设置为初始化状态，然后加入CLH队列，如果Cas修改的状态失败，说明此时signal方法正在把节点转一个同步对类中
         * 状态Node.CONDITION，说明线程是在通知前被取消了，直接放到同步队列中
         *
         * 判断节点是不是已经在同步队列中了
         */
        if (compareAndSetWaitStatus(node, Node.CONDITION, 0)) {
            enq(node);
            return true;
        }
        /*
         * If we lost out to a signal(), then we can't proceed
         * until it finishes its enq().  Cancelling during an
         * incomplete transfer is both rare and transient, so just
         * spin.
         */
        //状态如果不是CONDITION，说明是在通知后，中断的线程，此时可能正在入队
        //此时是线程调用了signal()，正在把节点转义到同步队列中，此时让出cpu执行权，
        //等待signal()方法把等待队列的节点转义到同步队列中，然后判断当前已经在同步对垒中之后，就跳出循环返回false
        while (!isOnSyncQueue(node))
            Thread.yield();
        return false;
    }

    /**
     * 入参就是新创建的节点，即当前节点
     */
    final int fullyRelease(Node node) {
        boolean failed = true;
        try {
            //这里这个取值要注意，获取当前的state并释放，这从另一个角度说明必须是独占锁
            //可以考虑下这个逻辑放在共享锁下面会发生什么？
            int savedState = getState();
            if (release(savedState)) {
                failed = false;
                return savedState;
            } else {
                //如果这里释放失败，则抛出异常
                throw new IllegalMonitorStateException();
            }
        } finally {
            /**
             * 如果释放锁失败，则把节点取消，由这里就能看出来上面添加节点的逻辑中
             * 只需要判断最后一个节点是否被取消就可以了
             */
            if (failed)
                node.waitStatus = Node.CANCELLED;
        }
    }

    // Instrumentation methods for conditions

    /**
     * Queries whether the given ConditionObject
     * uses this synchronizer as its lock.
     *
     * @param condition the condition
     * @return {@code true} if owned
     * @throws NullPointerException if the condition is null
     */
    public final boolean owns(ConditionObject condition) {
        return condition.isOwnedBy(this);
    }

    /**
     * Queries whether any threads are waiting on the given condition
     * associated with this synchronizer. Note that because timeouts
     * and interrupts may occur at any time, a {@code true} return
     * does not guarantee that a future {@code signal} will awaken
     * any threads.  This method is designed primarily for use in
     * monitoring of the system state.
     *
     * @param condition the condition
     * @return {@code true} if there are any waiting threads
     * @throws IllegalMonitorStateException if exclusive synchronization
     *         is not held
     * @throws IllegalArgumentException if the given condition is
     *         not associated with this synchronizer
     * @throws NullPointerException if the condition is null
     */
    public final boolean hasWaiters(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.hasWaiters();
    }

    /**
     * 获取条件队列长度
     */
    public final int getWaitQueueLength(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitQueueLength();
    }

    /**
     * 获取条件队列当中所有等待的thread集合
     */
    public final Collection<Thread> getWaitingThreads(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitingThreads();
    }

    /**
     * 条件对象，实现基于条件的具体行为
     */
    public class ConditionObject implements Condition, java.io.Serializable {
        private static final long serialVersionUID = 1173984872572414699L;
        /** First node of condition queue. */
        private transient Node firstWaiter;
        /** Last node of condition queue. */
        private transient Node lastWaiter;

        /**
         * Creates a new {@code ConditionObject} instance.
         */
        public ConditionObject() { }

        // Internal methods

        /**
         * 1.与同步队列不同，条件队列头尾指针是firstWaiter跟lastWaiter
         * 2.条件队列是在获取锁之后，也就是临界区进行操作，因此很多地方不用考虑并发
         * 为什么判断最后一个节点?
         * 因为新的等待节点都是会加载最后
         *
         * a、判断lastWaiter不为空，并且状态不是Node.CONDITION即-2 ，调用unlinkCancelledWaiters，删除的所有被取消的等待节点
         * 然后t设置为新的lastWaiter
         * b、新建一个等待节点node，如果lastWaiter为空，说明此时等待队列中没有元素，然后firstWaiter可以直接指向node
         * c、如果lastWaiter不为空，把t.nextWaiter单链表指向node
         * d、然后让这个lastWaiter指针指向node
         * 返回node
         */
        private Node addConditionWaiter() {
            //当第一次进入此方法时，lastWaiter和firstWaiter都是null
            Node t = lastWaiter;
            //如果最后一个节点被取消，则删除队列中被取消的节点,重新获取lastWaiter
            //至于为啥是最后一个节点后面会分析
            if (t != null && t.waitStatus != Node.CONDITION) {
                //删除所有被取消的节点
                unlinkCancelledWaiters();
                //t有可能为空
                t = lastWaiter;
            }
            //创建一个类型为CONDITION的节点并加入队列，由于在临界区，所以这里不用并发控制
            Node node = new Node(Thread.currentThread(), Node.CONDITION);
            //如果t为空，则firstWaiter为新创建的node节点。
            if (t == null)
                firstWaiter = node;
            else
                //如果最后一个节点不为空，把当前节点加入队列的尾部(单向链表)
                t.nextWaiter = node;
            //lastWaiter指向这个新创建的节点，->第一个次时firstWaiter and lastWaiter same
            lastWaiter = node;
            return node;
        }

        /**
         * 发信号，通知遍历条件队列当中的节点转移到同步队列当中，准备排队获取锁
         * 从队列的firstWaiter开始遍历，如果节点转换为signal成功并且入队成功，停止循环
         * 否则入队失败，并且是下个节点不为空，则继续循环直接其中一个节点设置signal成功并且入队成功，或者等待队列为空
         */
        private void doSignal(Node first) {
            do {
                if ( (firstWaiter = first.nextWaiter) == null)
                    lastWaiter = null;
                first.nextWaiter = null;
            } while (!transferForSignal(first) && //转移节点
                    (first = firstWaiter) != null);
        }

        /**
         * 通知所有节点移动到同步队列当中，并将节点从条件队列删除
         */
        private void doSignalAll(Node first) {
            lastWaiter = firstWaiter = null;
            do {
                Node next = first.nextWaiter;
                first.nextWaiter = null;
                transferForSignal(first);
                first = next;
            } while (first != null);
        }

        /**
         * 删除条件队列当中被取消的节点
         * 从首节点开始，剔除waitState!= Node.CONDITION == -2节点
         * 条件队列时，Node使用的是单链表结构
         */
        private void unlinkCancelledWaiters() {
            Node t = firstWaiter;
            Node trail = null;
            while (t != null) {
                Node next = t.nextWaiter;
                if (t.waitStatus != Node.CONDITION) {
                    //如果 t的waitState不是Condition，设置他的下级节点为空
                    t.nextWaiter = null;
                    /**
                     * 1、如果trail为空，还是目录还有一个节点的状态为-2
                     * 2、此时直接把firstWaiter设置为他的下级节点
                     *
                     */
                    if (trail == null)
                        firstWaiter = next;
                    else
                        trail.nextWaiter = next;
                    /**
                     * 当等待队列中只有一个node，并且这个node的waiteState不是-2
                     * 此时firstWaiter和nextWaiter都会被设置为空
                     * 当等待队列中只有一个waitState的状态为-2的Node时，此时firstNode和nextNode节点是都指向这个节点，
                     * 并且这个节点的nextWaiter为空
                     *
                     * 如果有多个节点，并且有节点的waiterState为-2，
                     * 此时next节点为空，则把最后一个等待节点设置为trail，这时的trail是这个等待状态为-2的节点。
                     */

                    if (next == null)
                        lastWaiter = trail;
                }
                else
                    //记录上个为waitState==-2的节点
                    trail = t;
                t = next;
            }
        }

        // public methods

        /**
         * 发新号，通知条件队列当中节点到同步队列当中去排队
         *
         */
        public final void signal() {
            if (!isHeldExclusively())//节点不能已经持有独占锁
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                /**
                 * 发信号通知条件队列的节点准备到同步队列当中去排队
                 */
                doSignal(first);
        }

        /**
         * 唤醒所有条件队列的节点转移到同步队列当中
         */
        public final void signalAll() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignalAll(first);
        }

        /**
         * Implements uninterruptible condition wait.
         * <ol>
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * </ol>
         */
        public final void awaitUninterruptibly() {
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean interrupted = false;
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if (Thread.interrupted())
                    interrupted = true;
            }
            if (acquireQueued(node, savedState) || interrupted)
                selfInterrupt();
        }

        /*
         * For interruptible waits, we need to track whether to throw
         * InterruptedException, if interrupted while blocked on
         * condition, versus reinterrupt current thread, if
         * interrupted while blocked waiting to re-acquire.
         */

        /** 该模式表示在退出等待时重新中断 */
        private static final int REINTERRUPT =  1;
        /** 异常中断 */
        private static final int THROW_IE    = -1;

        /**
         * 这里的判断逻辑是：
         * 1.如果现在不是中断的，即正常被signal唤醒则返回0
         * 2、如果节点是中断状态加入到tongue队列的，此时返回THROW_IE，如果不是则返回REINTERRUPT
         */
        private int checkInterruptWhileWaiting(Node node) {
            return Thread.interrupted() ?
                    (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
                    0;
        }

        /**
         * 根据中断时机选择抛出异常或者设置线程中断状态
         */
        private void reportInterruptAfterWait(int interruptMode)
                throws InterruptedException {
            if (interruptMode == THROW_IE)
                throw new InterruptedException();
            else if (interruptMode == REINTERRUPT)
                selfInterrupt();
        }

        /**
         * 加入条件队列等待，条件队列入口
         */
        public final void await() throws InterruptedException {
            //如果当前线程被中断则直接抛出异常
            if (Thread.interrupted())
                throw new InterruptedException();
            //把当前节点加入条件队列
            Node node = addConditionWaiter();
            /**
             * 1、释放掉已经获取的独占锁资源 ，
             * 2、同时如果head不为空，并且waitState的!=0,（以独占来说，此时waitState==-1 则唤醒下个节点
             */
            int savedState = fullyRelease(node);
            //记录唤醒模式
            int interruptMode = 0;
            //如果不在同步队列中则不断挂起
            while (!isOnSyncQueue(node)) {
                //如果Node节点部位同步队列中，阻塞此线程，
                LockSupport.park(this);
                //为什么要判断是中断唤醒的还是signal正常唤醒的？ 因为 LockSupport.park 方法是响应中断的
                //这里被唤醒可能是正常的signal操作也可能是中断
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            /**
             * 走到这里说明节点已经条件满足被加入到了同步队列中或者中断了
             * 这个方法很熟悉吧？就跟独占锁调用同样的获取锁方法，从这里可以看出条件队列只能用于独占锁
             * 在处理中断之前首先要做的是从同步队列中成功获取锁资源
             */
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            //走到这里说明已经成功获取到了独占锁，接下来就做些收尾工作
            //删除条件队列中被取消的节点
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            //根据不同模式处理中断
            //interruptMode==0 说明是被正常signal()方法唤醒的
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }

        /**
         * Implements timed condition wait.
         * <ol>
         * <li> If current thread is interrupted, throw InterruptedException.
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled, interrupted, or timed out.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * <li> If interrupted while blocked in step 4, throw InterruptedException.
         * </ol>
         */
        public final long awaitNanos(long nanosTimeout)
                throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return deadline - System.nanoTime();
        }

        /**
         * Implements absolute timed condition wait.
         * <ol>
         * <li> If current thread is interrupted, throw InterruptedException.
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled, interrupted, or timed out.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * <li> If interrupted while blocked in step 4, throw InterruptedException.
         * <li> If timed out while blocked in step 4, return false, else true.
         * </ol>
         */
        public final boolean awaitUntil(Date deadline)
                throws InterruptedException {
            long abstime = deadline.getTime();
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (System.currentTimeMillis() > abstime) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                LockSupport.parkUntil(this, abstime);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        /**
         * Implements timed condition wait.
         * <ol>
         * <li> If current thread is interrupted, throw InterruptedException.
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled, interrupted, or timed out.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * <li> If interrupted while blocked in step 4, throw InterruptedException.
         * <li> If timed out while blocked in step 4, return false, else true.
         * </ol>
         */
        public final boolean await(long time, TimeUnit unit)
                throws InterruptedException {
            long nanosTimeout = unit.toNanos(time);
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        //  support for instrumentation

        /**
         * Returns true if this condition was created by the given
         * synchronization object.
         *
         * @return {@code true} if owned
         */
        final boolean isOwnedBy(AbstractQueuedSynchronizer sync) {
            return sync == AbstractQueuedSynchronizer.this;
        }

        /**
         * Queries whether any threads are waiting on this condition.
         * Implements {@link AbstractQueuedSynchronizer#hasWaiters(ConditionObject)}.
         *
         * @return {@code true} if there are any waiting threads
         * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        protected final boolean hasWaiters() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    return true;
            }
            return false;
        }

        /**
         * Returns an estimate of the number of threads waiting on
         * this condition.
         * Implements {@link AbstractQueuedSynchronizer#getWaitQueueLength(ConditionObject)}.
         *
         * @return the estimated number of waiting threads
         * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        protected final int getWaitQueueLength() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int n = 0;
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    ++n;
            }
            return n;
        }

        /**
         * 得到同步队列当中所有在等待的Thread集合
         */
        protected final Collection<Thread> getWaitingThreads() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            ArrayList<Thread> list = new ArrayList<Thread>();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION) {
                    Thread t = w.thread;
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        }
    }

    /**
     * Setup to support compareAndSet. We need to natively implement
     * this here: For the sake of permitting future enhancements, we
     * cannot explicitly subclass AtomicInteger, which would be
     * efficient and useful otherwise. So, as the lesser of evils, we
     * natively implement using hotspot intrinsics API. And while we
     * are at it, we do the same for other CASable fields (which could
     * otherwise be done with atomic field updaters).
     * unsafe魔法类，直接绕过虚拟机内存管理机制，修改内存
     */
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (Node.class.getDeclaredField("next"));

        } catch (Exception ex) { throw new Error(ex); }
    }

    /**
     * CAS 修改头部节点指向. 并发入队时使用.
     */
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    /**
     * CAS 修改尾部节点指向. 并发入队时使用.
     */
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    /**
     * CAS 修改信号量状态.
     */
    private static final boolean compareAndSetWaitStatus(Node node,
                                                         int expect,
                                                         int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,
                expect, update);
    }

    /**
     * 修改节点的后继指针.
     */
    private static final boolean compareAndSetNext(Node node,
                                                   Node expect,
                                                   Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }
}
