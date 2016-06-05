package com.zagayevskiy.lang.runtime.types;

import javax.annotation.Nonnull;

public class LangString extends LangObject {

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
    public LangInteger toLangInteger() {
        return LangInteger.NAN;
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
}
