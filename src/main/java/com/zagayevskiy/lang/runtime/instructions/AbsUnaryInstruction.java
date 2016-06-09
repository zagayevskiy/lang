package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public abstract class AbsUnaryInstruction implements Instruction{

    @Override
    public final void execute(@Nonnull IFunction function) {
        function.pushOperand(execute(function, function.popOperand()));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull IFunction function, @Nonnull Operand operand);
}
