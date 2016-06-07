package com.zagayevskiy.lang.runtime.types;

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

    @Nonnull
    private static ArrayList<LangObject> createArrayList(int size) {
        final ArrayList<LangObject> result = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            result.add(LangUndefined.INSTANCE);
        }
        return result;
    }
}
