package com.zagayevskiy.lang.runtime.types.primitive.string;

import com.zagayevskiy.lang.runtime.types.base.BaseLangClass;
import com.zagayevskiy.lang.runtime.types.primitive.string.methods.StringMethods;

import javax.annotation.Nonnull;

class LangStringClass extends BaseLangClass {

    public static final LangStringClass INSTANCE = new LangStringClass();

    private LangStringClass() {
        registerAllMethods(StringMethods.PROTOTYPES);
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return LangStringClass.class.getSimpleName();
    }
}
