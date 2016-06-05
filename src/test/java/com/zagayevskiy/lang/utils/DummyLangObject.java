package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.types.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangString;

import javax.annotation.Nonnull;

public class DummyLangObject extends LangObject{

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from(toString());
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.from(hashCode());
    }
}
