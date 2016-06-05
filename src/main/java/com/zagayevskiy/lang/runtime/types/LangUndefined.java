package com.zagayevskiy.lang.runtime.types;

import javax.annotation.Nonnull;

public class LangUndefined extends LangObject {

    public static final LangUndefined INSTANCE = new LangUndefined();

    private LangUndefined(){

    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.NAN;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from("undefined");
    }

    @Override
    public String toString() {
        return "undefined";
    }
}
