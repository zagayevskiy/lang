package com.zagayevskiy.lang.runtime.types.function;

import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface IFunction {

    @Nonnull
    String getName();

    @Nonnull
    LangObject call();
}
