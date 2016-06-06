package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsUnaryObjInstruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public class LogicNotInstruction extends AbsUnaryObjInstruction {

    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangObject operand) {
        return operand.toLangBoolean().not();
    }

    @Override
    public String toString() {
        return "!";
    }
}
