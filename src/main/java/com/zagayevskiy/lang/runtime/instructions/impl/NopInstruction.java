package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.instructions.Instruction;

import javax.annotation.Nonnull;

public class NopInstruction implements Instruction {

    @Override
    public void execute(@Nonnull IFunction function) {
    }

    @Override
    public String toString() {
        return "NOP";
    }
}
