package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public abstract class AbsBinaryInstruction implements Instruction {
    @Override
    public void execute(@Nonnull IProgram program, @Nonnull IFunction function) {
        final Operand op2 = program.popOperand();
        final Operand op1 = program.popOperand();
        program.pushOperand(execute(op1, op2));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull Operand op1, @Nonnull Operand op2);
}
