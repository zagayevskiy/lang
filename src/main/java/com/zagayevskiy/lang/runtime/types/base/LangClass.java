package com.zagayevskiy.lang.runtime.types.base;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface LangClass {

    @Nullable
    IMethodPrototype getMethodPrototype(@Nonnull String name);

    @Nonnull
    String getLangClassName();
}
