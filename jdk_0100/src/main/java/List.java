public interface List<T> {

    void add(T t);

    T get(int index);

    int size();

    boolean isEmpty();

    boolean contain(T t);

    void add(int index,T t);

}
