package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsBinaryBoolInstruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;

import javax.annotation.Nonnull;

public class LogicOrInstruction extends AbsBinaryBoolInstruction {
    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangBoolean left, @Nonnull LangBoolean right) {
        return left.or(right);
    }

    @Override
    public String toString() {
        return "||";
    }
}
