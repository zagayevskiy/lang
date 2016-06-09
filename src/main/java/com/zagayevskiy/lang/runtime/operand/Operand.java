package com.zagayevskiy.lang.runtime.operand;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface Operand {
    @Nonnull
    LangObject getValue(@Nonnull IFunction context);
}
