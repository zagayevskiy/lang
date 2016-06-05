package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public abstract class AbsUnaryInstruction implements Instruction{

    @Override
    public final void execute(@Nonnull IFunction function) {
        execute(function, function.popOperand());
    }

    protected abstract void execute(@Nonnull IFunction function, @Nonnull Operand operand);
}
