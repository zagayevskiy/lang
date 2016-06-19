package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.instructions.Instruction;

import javax.annotation.Nonnull;

public class NopInstruction implements Instruction {

    @Override
    public void execute(@Nonnull IContext context) {
    }

    @Override
    public String toString() {
        return "NOP";
    }
}
