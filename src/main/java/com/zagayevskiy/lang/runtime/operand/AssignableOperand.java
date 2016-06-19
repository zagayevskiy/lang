package com.zagayevskiy.lang.runtime.operand;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;

public interface AssignableOperand extends Operand {
    void setValue(@Nonnull IContext context, @Nonnull LangObject value);
}
