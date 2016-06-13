package com.zagayevskiy.lang.runtime.operand;

import com.zagayevskiy.lang.runtime.types.IContext;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface AssignableOperand extends Operand {
    void setValue(@Nonnull IContext context, @Nonnull LangObject value);
}
