package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;

import javax.annotation.Nonnull;

public abstract class AbsMultipleArgsInstruction implements Instruction {
    @Override
    public final void execute(@Nonnull IContext context) {
        final LangObject count = context.popOperand().getValue(context);
        if (count.getClass() != LangInteger.class) {
            throw new IllegalStateException("top argument of " + AbsMultipleArgsInstruction.class.getSimpleName() + " must be int.");
        }
        final int argsCount = count.toLangInteger().intValue;

        context.pushOperand(execute(context, argsCount));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull IContext context, int argsCount);
}
