package utils;

import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private int size = 0;

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> temp = head;

        if (head == null) {
            head = newNode;
        }
        else {
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;
    }

    public void remove(T data) {
        if (head != null && head.data == data) {
            head = head.next;
            size--;
            return;
        }

        Node<T> temp = head;
        while (temp != null && temp.next != null) {
            if (temp.next.data == data) {
                temp.next = temp.next.next;
                size--;
            }
            else {
                temp = temp.next;
            }
        }
    }

    public T get(int index) {
        if (index < 0 || index > size) {
            return null;
        }

        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    public int size() {
        return size;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>(head);
    }
}
