package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.instructions.Instruction;

import javax.annotation.Nonnull;

public class PopInstruction implements Instruction {

    @Override
    public void execute(@Nonnull IProgram program, @Nonnull IFunction function) {
        program.popOperand();
    }

    @Override
    public String toString() {
        return "POP";
    }
}
