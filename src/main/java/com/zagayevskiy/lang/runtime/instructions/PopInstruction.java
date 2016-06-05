package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public class PopInstruction implements Instruction {

    public static final PopInstruction INSTANCE = new PopInstruction();

    private PopInstruction() {}

    @Override
    public void execute(@Nonnull IProgram program, @Nonnull IFunction function) {
        program.popOperand();
    }

    @Override
    public String toString() {
        return "POP";
    }
}
