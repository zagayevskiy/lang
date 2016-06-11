package com.zagayevskiy.lang.runtime.types.primitive;

import com.zagayevskiy.lang.runtime.types.AbsLangObject;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public class LangString extends AbsLangObject implements Comparable<LangObject> {

    @Nonnull
    public final String stringValue;

    @Nonnull
    public static LangString from(@Nonnull String value) {
        //TODO: may be refactor?
        return new LangString(value);
    }

    private LangString(@Nonnull String stringValue) {
        this.stringValue = stringValue;
    }

    @Nonnull
    @Override
    public LangBoolean toLangBoolean() {
        return LangBoolean.from(!stringValue.isEmpty());
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.NaN;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return this;
    }

    @Override
    public String toString() {
        return "string:" + stringValue;
    }

    @Override
    public int compareTo(@Nonnull LangObject other) {
        return stringValue.compareTo(other.toLangString().stringValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LangString that = (LangString) o;

        return stringValue.equals(that.stringValue);
    }

    @Override
    public int hashCode() {
        return stringValue.hashCode();
    }
}
