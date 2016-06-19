package com.zagayevskiy.lang.runtime.types.classes;

import javax.annotation.Nonnull;

public class FunctionPrototypeClass extends BaseLangClass {

    public static final FunctionPrototypeClass INSTANCE = new FunctionPrototypeClass();

    private FunctionPrototypeClass(){}

    @Nonnull
    @Override
    public String getLangClassName() {
        return FunctionPrototypeClass.class.getSimpleName();
    }
}
