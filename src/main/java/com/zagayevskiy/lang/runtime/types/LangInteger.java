package com.zagayevskiy.lang.runtime.types;

import javax.annotation.Nonnull;

public class LangInteger extends LangObject {

    public static final LangInteger NAN = new LangInteger(0, true);

    @Nonnull
    public static LangInteger from(int value) {
        //TODO: cache here
        return new LangInteger(value);
    }

    @Nonnull
    public static LangInteger from(int value, boolean isNan) {
        return isNan ? NAN : from(value);
    }

    public final int intValue;
    public final boolean isNan;

    private LangInteger(int intValue) {
        this(intValue, false);
    }

    private LangInteger(int intValue, boolean isNan) {
        this.intValue = intValue;
        this.isNan = isNan;
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return this;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from(String.valueOf(intValue));
    }

    @Override
    public String toString() {
        return "i:" + intValue;
    }
}
