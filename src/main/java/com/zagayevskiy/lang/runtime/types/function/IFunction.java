package com.zagayevskiy.lang.runtime.types.function;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;

public interface IFunction {

    @Nonnull
    String getName();

    @Nonnull
    LangObject call(@Nonnull IContext context);
}
