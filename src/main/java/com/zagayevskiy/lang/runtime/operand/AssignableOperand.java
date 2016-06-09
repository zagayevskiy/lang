package com.zagayevskiy.lang.runtime.operand;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface AssignableOperand extends Operand {
    void setValue(@Nonnull IFunction context, @Nonnull LangObject value);
}
