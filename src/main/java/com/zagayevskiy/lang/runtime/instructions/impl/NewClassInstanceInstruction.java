package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.struct.LangStructClass;
import com.zagayevskiy.lang.runtime.userclass.IUserClass;
import com.zagayevskiy.lang.runtime.userclass.IUserClassPrototype;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class NewClassInstanceInstruction implements Instruction {
    @Override
    public void execute(@Nonnull IContext context) {
        final LangObject userClassProtoObj = context.popOperand().getValue(context);
        if (!(userClassProtoObj instanceof IUserClassPrototype)) {
            //TODO
            throw new IllegalStateException();
        }

        final LangObject argsCountObj = context.popOperand().getValue(context);
        if (argsCountObj.getClass() != LangInteger.class || argsCountObj.toLangInteger().isNan) {
            throw new IllegalStateException("top operand of " + getClass().getSimpleName()
                    + " must be valid int. Has " + argsCountObj.toString());
        }

        final int actualArgsCount = argsCountObj.toLangInteger().intValue;
        final IUserClassPrototype prototype = (IUserClassPrototype) userClassProtoObj;
        final IUserClass.Constructor constructor = prototype.getConstructor(actualArgsCount);
        if (constructor == null) {
            throw new IllegalStateException("has not constructor");
        }
        final LangObject[] args = new LangObject[actualArgsCount];
        for (int i = actualArgsCount - 1; i >= 0; --i) {
            args[i] = context.popOperand().getValue(context);
        }

        context.pushOperand(constructor.newInstance(context, Arrays.asList(args)));
    }

    @Override
    public String toString() {
        return "NEW CLASS";
    }
}
