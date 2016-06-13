package com.zagayevskiy.lang.runtime.types.classes;

import javax.annotation.Nonnull;

public class MethodPrototypeClass extends DefaultClass {

    public static final MethodPrototypeClass INSTANCE = new MethodPrototypeClass();

    private MethodPrototypeClass(){}

    @Nonnull
    @Override
    public String getLangClassName() {
        return MethodPrototypeClass.class.getSimpleName();
    }
}
