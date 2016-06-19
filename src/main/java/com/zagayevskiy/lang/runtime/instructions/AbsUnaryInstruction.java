package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public abstract class AbsUnaryInstruction implements Instruction{

    @Override
    public final void execute(@Nonnull IContext context) {
        context.pushOperand(execute(context, context.popOperand()));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull IContext context, @Nonnull Operand operand);
}
