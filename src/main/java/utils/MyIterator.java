package utils;

import java.util.Iterator;

public class MyIterator<T> implements Iterator<T> {
    private Node<T> current;

    public MyIterator(Node<T> head) {
        current = head;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        Node<T> temp = current;

        current = current.next;
        return temp.data;
    }
}
