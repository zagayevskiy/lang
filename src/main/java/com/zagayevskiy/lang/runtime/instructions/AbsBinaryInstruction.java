package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public abstract class AbsBinaryInstruction implements Instruction {

    @Override
    public final void execute(@Nonnull IContext context) {
        final Operand op2 = context.popOperand();
        final Operand op1 = context.popOperand();
        context.pushOperand(execute(context, op1, op2));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull IContext context, @Nonnull Operand op1, @Nonnull Operand op2);

}
