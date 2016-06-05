package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public abstract class AbsBinaryInstruction implements Instruction {
    @Override
    public void execute(@Nonnull IFunction function) {
        final Operand op2 = function.popOperand();
        final Operand op1 = function.popOperand();
        function.pushOperand(execute(op1.getValue(), op2.getValue()));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull LangObject left, @Nonnull LangObject right);
}
