package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public abstract class AbsMultipleArgsInstruction implements Instruction {
    @Override
    public final void execute(@Nonnull IFunction function) {
        final LangObject count = function.popOperand().getValue(function);
        if (count.getClass() != LangInteger.class) {
            throw new IllegalStateException("top argument of " + AbsMultipleArgsInstruction.class.getSimpleName() + " must be int.");
        }
        final int argsCount = count.toLangInteger().intValue;

        function.pushOperand(execute(function, argsCount));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull IFunction function, int argsCount);
}
