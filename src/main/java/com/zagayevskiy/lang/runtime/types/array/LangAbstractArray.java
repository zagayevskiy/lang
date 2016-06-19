package com.zagayevskiy.lang.runtime.types.array;

import com.zagayevskiy.lang.runtime.types.base.AbsLangObject;
import com.zagayevskiy.lang.runtime.types.base.LangClass;

import javax.annotation.Nonnull;
import java.util.*;

abstract class LangAbstractArray<E> extends AbsLangObject implements List<E>, RandomAccess {
    @Nonnull
    final ArrayList<E> arrayList;

    LangAbstractArray(@Nonnull ArrayList<E> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return arrayList.contains(o);
    }

    @Override
    public int indexOf(Object o) {
        return arrayList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return arrayList.lastIndexOf(o);
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return arrayList.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        return arrayList.toArray(a);
    }

    @Override
    public E get(int index) {
        return arrayList.get(index);
    }

    @Override
    public E set(int index, E element) {
        return arrayList.set(index, element);
    }

    @Override
    public boolean add(E E) {
        return arrayList.add(E);
    }

    @Override
    public void add(int index, E element) {
        arrayList.add(index, element);
    }

    @Override
    public E remove(int index) {
        return arrayList.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return arrayList.remove(o);
    }

    @Override
    public void clear() {
        arrayList.clear();
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends E> c) {
        return arrayList.addAll(c);
    }

    @Override
    public boolean addAll(int index, @Nonnull Collection<? extends E> c) {
        return arrayList.addAll(index, c);
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        return arrayList.removeAll(c);
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return arrayList.retainAll(c);
    }

    @Nonnull
    @Override
    public ListIterator<E> listIterator(int index) {
        return arrayList.listIterator(index);
    }

    @Nonnull
    @Override
    public ListIterator<E> listIterator() {
        return arrayList.listIterator();
    }

    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return arrayList.iterator();
    }

    @Nonnull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return arrayList.subList(fromIndex, toIndex);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return arrayList.containsAll(c);
    }

    @Nonnull
    @Override
    public LangClass getLangClass() {
        return LangArrayClass.INSTANCE;
    }
}
