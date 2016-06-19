package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;

public class JumpFalseInstruction extends JumpInstruction {
    @Override
    public void execute(@Nonnull IContext context) {
        final LangObject address = context.popOperand().getValue(context);
        final LangObject condition = context.popOperand().getValue(context);

        if (!condition.toLangBoolean().boolValue) {
            jump(context, address);
        }
    }

    @Override
    public String toString() {
        return "jump_false";
    }
}
