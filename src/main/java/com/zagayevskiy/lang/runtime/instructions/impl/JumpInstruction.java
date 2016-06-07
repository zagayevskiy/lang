package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public class JumpInstruction implements Instruction {
    @Override
    public void execute(@Nonnull IFunction function) {
        jump(function, function.popOperand().getValue(function));
    }

    final void jump(@Nonnull IFunction function, @Nonnull LangObject address) {
        if (address.getClass() != LangInteger.class) {
            throw new RuntimeException("argument of jump must be int");
        }

        function.jump(address.toLangInteger().intValue);
    }

    @Override
    public String toString() {
        return "jump";
    }
}
