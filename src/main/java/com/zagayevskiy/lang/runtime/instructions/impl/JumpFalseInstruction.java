package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public class JumpFalseInstruction extends JumpInstruction {
    @Override
    public void execute(@Nonnull IFunction function) {
        final LangObject address = function.popOperand().getValue(function);
        final LangObject condition = function.popOperand().getValue(function);

        if (!condition.toLangBoolean().boolValue) {
            jump(function, address);
        }
    }

    @Override
    public String toString() {
        return "jump_false";
    }
}
