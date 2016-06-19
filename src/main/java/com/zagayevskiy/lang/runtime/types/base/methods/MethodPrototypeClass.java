package com.zagayevskiy.lang.runtime.types.base.methods;

import com.zagayevskiy.lang.runtime.types.base.BaseLangClass;

import javax.annotation.Nonnull;

public class MethodPrototypeClass extends BaseLangClass {

    public static final MethodPrototypeClass INSTANCE = new MethodPrototypeClass();

    private MethodPrototypeClass(){}

    @Nonnull
    @Override
    public String getLangClassName() {
        return MethodPrototypeClass.class.getSimpleName();
    }
}
