package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;

import javax.annotation.Nonnull;

public class NopInstruction implements Instruction {

    public static final NopInstruction INSTANCE = new NopInstruction();

    private NopInstruction() {
    }

    @Override
    public void execute(@Nonnull IProgram program, @Nonnull IFunction function) {
    }

    @Override
    public String toString() {
        return "NOP";
    }
}
