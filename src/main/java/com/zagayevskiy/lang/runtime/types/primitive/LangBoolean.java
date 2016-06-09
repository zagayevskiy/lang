package com.zagayevskiy.lang.runtime.types.primitive;

import com.zagayevskiy.lang.runtime.types.AbsLangObject;

import javax.annotation.Nonnull;

public class LangBoolean extends AbsLangObject {

    public static final LangBoolean TRUE = new LangBoolean(true);
    public static final LangBoolean FALSE = new LangBoolean(false);

    public static LangBoolean from(boolean value) {
        return value ? TRUE : FALSE;
    }

    public final boolean boolValue;

    private LangBoolean(boolean boolValue) {
        this.boolValue = boolValue;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from(String.valueOf(boolValue).toLowerCase());
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.from(boolValue ? 1 : 0);
    }

    @Nonnull
    @Override
    public LangBoolean toLangBoolean() {
        return this;
    }

    @Nonnull
    public LangBoolean or(@Nonnull LangBoolean other) {
        return from(boolValue || other.boolValue);
    }

    @Nonnull
    public LangBoolean and(@Nonnull LangBoolean other) {
        return from(boolValue && other.boolValue);
    }

    @Nonnull
    public LangBoolean not() {
        return from(!boolValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LangBoolean that = (LangBoolean) o;

        return boolValue == that.boolValue;

    }

    @Override
    public int hashCode() {
        return (boolValue ? 1 : 0);
    }

    @Override
    public String toString() {
        return "b:" + String.valueOf(boolValue);
    }
}

