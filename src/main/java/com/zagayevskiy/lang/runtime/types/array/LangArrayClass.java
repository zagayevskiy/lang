package com.zagayevskiy.lang.runtime.types.array;

import com.zagayevskiy.lang.runtime.types.array.methods.ArrayMethods;
import com.zagayevskiy.lang.runtime.types.base.BaseLangClass;

import javax.annotation.Nonnull;

class LangArrayClass extends BaseLangClass {

    public static final LangArrayClass INSTANCE = new LangArrayClass();

    private LangArrayClass() {
        registerAllMethods(ArrayMethods.PROTOTYPES);
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return LangArrayClass.class.getSimpleName();
    }
}
