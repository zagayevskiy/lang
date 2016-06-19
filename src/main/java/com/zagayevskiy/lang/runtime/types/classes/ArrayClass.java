package com.zagayevskiy.lang.runtime.types.classes;

import com.zagayevskiy.lang.runtime.types.methods.Methods;

import javax.annotation.Nonnull;

public class ArrayClass extends BaseLangClass {

    public static final ArrayClass INSTANCE = new ArrayClass();

    private ArrayClass() {
        registerAllMethods(Methods.ARRAY_METHODS);
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return ArrayClass.class.getSimpleName();
    }
}
