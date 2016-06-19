package com.zagayevskiy.lang.runtime.types.function.prototype;

import com.zagayevskiy.lang.runtime.types.base.BaseLangClass;

import javax.annotation.Nonnull;

class FunctionPrototypeClass extends BaseLangClass {

    public static final FunctionPrototypeClass INSTANCE = new FunctionPrototypeClass();

    private FunctionPrototypeClass(){}

    @Nonnull
    @Override
    public String getLangClassName() {
        return FunctionPrototypeClass.class.getSimpleName();
    }
}
