package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.struct.LangStructClass;

import javax.annotation.Nonnull;

public class NewStructInstanceInstruction implements Instruction {
    @Override
    public void execute(@Nonnull IContext context) {
        final LangObject structClassObj = context.popOperand().getValue(context);
        if (structClassObj.getClass() != LangStructClass.class) {
            throw new IllegalStateException("top operand of " + NewStructInstanceInstruction.class.getSimpleName()
                    + " must be instance of " + LangStructClass.class.getSimpleName()
                    + ". Has " + structClassObj.toString());
        }

        final LangObject argsCountObj = context.popOperand().getValue(context);
        if (argsCountObj.getClass() != LangInteger.class || argsCountObj.toLangInteger().isNan) {
            throw new IllegalStateException("top operand of " + NewStructInstanceInstruction.class.getSimpleName()
                    + " must be valid int. Has " + argsCountObj.toString());
        }

        final LangStructClass structClass = (LangStructClass) structClassObj;
        final int argsCount = argsCountObj.toLangInteger().intValue;
        final LangStructClass.InstanceBuilder instanceBuilder = structClass.newInstanceBuilder(argsCount);
        for (int i = 0; i < argsCount; ++i) {
            instanceBuilder.withArgument(context.popOperand().getValue(context));
        }

        context.pushOperand(instanceBuilder.build());
    }
}
