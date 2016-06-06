package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsBinaryBoolInstruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangBoolean;

import javax.annotation.Nonnull;

public class LogicAndInstruction extends AbsBinaryBoolInstruction {
    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangBoolean left, @Nonnull LangBoolean right) {
        return left.and(right);
    }

    @Override
    public String toString() {
        return "&&";
    }
}
