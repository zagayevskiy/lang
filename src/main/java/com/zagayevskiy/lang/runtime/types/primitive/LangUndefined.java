package com.zagayevskiy.lang.runtime.types.primitive;

import com.zagayevskiy.lang.runtime.types.base.AbsLangObject;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;

import javax.annotation.Nonnull;

public class LangUndefined extends AbsLangObject {

    public static final LangUndefined INSTANCE = new LangUndefined();
    public static final String STRING_VALUE = "undefined";

    private LangUndefined(){

    }

    @Nonnull
    @Override
    public LangBoolean toLangBoolean() {
        return LangBoolean.FALSE;
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.NaN;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from(STRING_VALUE);
    }

    @Override
    public String toString() {
        return STRING_VALUE;
    }
}
