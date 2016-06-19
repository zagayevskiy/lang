package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;

public abstract class AbsBinaryObjInstruction extends AbsBinaryInstruction {

    @Nonnull
    @Override
    protected final Operand execute(@Nonnull IContext context, @Nonnull Operand op1, @Nonnull Operand op2) {
        return execute(op1.getValue(context), op2.getValue(context));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull LangObject left, @Nonnull LangObject right);
}
