package com.zagayevskiy.lang.runtime.types.array;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class LangArray extends LangAbstractArray<LangObject> {

    public LangArray() {
        this(0);
    }

    public LangArray(int size) {
        super(createArrayList(size));
    }

    @Nonnull
    @Override
    public LangBoolean toLangBoolean() {
        return LangBoolean.from(!isEmpty());
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.NaN;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        final StringBuilder builder = new StringBuilder("[");
        final int size = size();
        for (int i = 0; i < size; ++i) {
            builder.append(get(i).toLangString().stringValue);
            if (i < size - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");

        return LangString.from(builder.toString());
    }

    @Override
    public String toString() {
        return toLangString().stringValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final LangArray that = (LangArray) o;

        if (arrayList == that.arrayList) return true;
        if (size() != that.size()) return false;

        for (int i = 0; i < size(); ++i) {
            if (!get(i).equals(that.get(i))) return false;
        }

        return true;

    }

    @Override
    public int hashCode() {
        int result = size();

        for (LangObject item: this) {
            result = 31 * result + item.hashCode();
        }

        return result;
    }

    @Override
    public LangObject get(int index) {
        if (index >= size()) {
            return LangUndefined.INSTANCE;
        }
        return super.get(index);
    }

    @Override
    public LangObject set(int index, LangObject element) {
        expandToSize(index + 1);
        return super.set(index, element);
    }

    @Override
    public void add(int index, LangObject element) {
        expandToSize(index);
        super.add(index, element);
    }

    @Override
    public LangObject remove(int index) {
        if (index >= size()) {
            return LangUndefined.INSTANCE;
        }
        return super.remove(index);
    }

    private void expandToSize(int size) {
        while (size() < size) {
            add(LangUndefined.INSTANCE);
        }
    }

    @Nonnull
    private static ArrayList<LangObject> createArrayList(int size) {
        final ArrayList<LangObject> result = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            result.add(LangUndefined.INSTANCE);
        }
        return result;
    }
}
