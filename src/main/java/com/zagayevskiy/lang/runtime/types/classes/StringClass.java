package com.zagayevskiy.lang.runtime.types.classes;

import com.zagayevskiy.lang.runtime.types.methods.Methods;

import javax.annotation.Nonnull;

public class StringClass extends BaseLangClass {

    public static final StringClass INSTANCE = new StringClass();

    private StringClass() {
        registerAllMethods(Methods.STRING_METHODS);
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return StringClass.class.getSimpleName();
    }
}
