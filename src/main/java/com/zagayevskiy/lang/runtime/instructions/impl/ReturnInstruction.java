package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.IContext;

import javax.annotation.Nonnull;

public final class ReturnInstruction implements Instruction {

    @Override
    public void execute(@Nonnull IContext context) {
        context.doReturn();
    }

    @Override
    public String toString() {
        return "RET";
    }
}
