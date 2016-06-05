package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public abstract class AbsBinaryInstruction implements Instruction {

    @Override
    public final void execute(@Nonnull IFunction function) {
        final Operand op2 = function.popOperand();
        final Operand op1 = function.popOperand();
        function.pushOperand(execute(function, op1, op2));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull IFunction function, @Nonnull Operand op1, @Nonnull Operand op2);

}
