package com.company;

import java.util.*;

public class MyCollection<E> implements Collection<E> {

    private int size;
    private Object[] elementData = new Object[10];

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean add(E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.size() != 0) {
            Object[] result = c.toArray();
            elementData = Arrays.copyOf(elementData, size + result.length);
            System.arraycopy(result, 0, elementData, size, result.length);
            size = elementData.length;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Object c : elementData) {
            if (o == c) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        MyCollection<Boolean> my = new MyCollection<>();
        Object[] result = c.toArray();
        for (Object o : result) {
            if (contains(o)) {
                my.add(true);
            } else {
                my.add(false);
            }
        }

        for (Boolean a : my) {
            if (!a) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        if (size >= 0) System.arraycopy(elementData, 0, result, 0, size);
        return result;
    }


    @Override
    public <T> T[] toArray(T[] a) {

        T[] trouble = null;

        if (a.length == 0) {
            trouble = Arrays.copyOf((T[]) elementData, size);
        }

        if (a.length <= size) {
            for (T c : a) {
                if (c != null) {
                    trouble = Arrays.copyOf(a, size);
                } else {
                    trouble = Arrays.copyOf((T[]) elementData, size);
                }
            }
        } else {
            for (T c : a) {
                if (c != null) {
                    System.arraycopy((T[])elementData, 0, a, 0, size);
                    trouble = a;
                } else {
                    trouble = Arrays.copyOf((T[])elementData, a.length);
                }
            }
        }

        return trouble;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i <= size; i++) {
            if (o == elementData[i]) {
                Object[] result = new Object[size - 1];
                System.arraycopy(elementData, 0, result, 0, i);
                System.arraycopy(elementData, i + 1, result, i, size - i - 1);
                size = result.length;
                elementData = Arrays.copyOf(result, size);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] save = Arrays.copyOf(elementData, size);
        Object[] result = c.toArray();
        for (Object o : result) {
            for (int j = 0; j < size; j++) {
                if (o.equals(elementData[j])) {
                    remove(elementData[j]);
                }
            }
        }
        return elementData.length < save.length;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChange = false;
        for (int i = 0; i < size; i++) {
            while (!c.contains(elementData[i]) && contains(elementData[i])) {
                remove(elementData[i]);
                isChange = true;
            }
        }
        return isChange;
    }

    @Override
    public void clear() {
        size = 0;
        elementData = Arrays.copyOf(elementData, size);
    }

    private class MyIterator<T> implements Iterator<T> {

        int cursor = 0;
        Boolean error;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            error = true;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (cursor == 0 || !error) {
                throw new IllegalStateException();
            } else {
                Object[] result = new Object[size - 1];
                int index = cursor - 1;
                System.arraycopy(elementData, 0, result, 0, index);
                System.arraycopy(elementData, index + 1, result, index, size - index - 1);
                size = result.length;
                elementData = Arrays.copyOf(result, size);
                error = false;
            }
        }
    }
}