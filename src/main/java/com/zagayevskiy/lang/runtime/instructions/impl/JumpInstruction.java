package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.IContext;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;

import javax.annotation.Nonnull;

public class JumpInstruction implements Instruction {
    @Override
    public void execute(@Nonnull IContext context) {
        jump(context, context.popOperand().getValue(context));
    }

    final void jump(@Nonnull IContext context, @Nonnull LangObject address) {
        if (address.getClass() != LangInteger.class) {
            throw new RuntimeException("argument of jump must be int");
        }

        context.jump(address.toLangInteger().intValue);
    }

    @Override
    public String toString() {
        return "jump";
    }
}
