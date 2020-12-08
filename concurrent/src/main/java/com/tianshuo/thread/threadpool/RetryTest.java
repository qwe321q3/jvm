package com.tianshuo.thread.threadpool;

/**
 * @author tianshuo
 * @Classname RetryTest
 * @Description 新的循环跳出方法类似goto
 * @Date 2020/8/1 0001 22:57
 * @Created by Administrator
 */
public class RetryTest {

    public static void main(String[] args) {
        //0	1	2	3	4	0	1	2	3	4
       // continueTest();
        //0	1	2	3	0	1	2	3
//        breakTest();

        //0	1	2	3	0	1	2	3
       // retryContinueTest();
        // 0	1	2	3
        retryBreakTest();

    }

    /**
     * 0	1	2	3	4	0	1	2	3	4
     */
    private static void continueTest() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(j+"\t");

                if (j == 3) {
                    continue;
                }
            }

        }
    }

    /**
     * 0	1	2	3	0	1	2	3
     */
    private static void breakTest() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(j+"\t");

                if (j == 3) {
                    break;
                }
            }

        }
    }

    /**
     * 0	1	2	3 0	1	2	3
     */
    private static void retryContinueTest() {
        c:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(j+"\t");

                if (j == 3) {
                    continue c;
                }
            }

        }

    }

    /**
     * 0	1	2	3
     */
    private static void retryBreakTest() {
        d:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(j+"\t");
                if (j == 3) {
                    break d;
                }
            }

        }

    }
}
