package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;

import javax.annotation.Nonnull;

public class NewStructInstanceInstruction implements Instruction {
    @Override
    public void execute(@Nonnull IFunction function) {
        final LangObject structClassObj = function.popOperand().getValue(function);
        if (structClassObj.getClass() != LangStructClass.class) {
            throw new IllegalStateException("top operand of " + NewStructInstanceInstruction.class.getSimpleName()
                    + " must be instance of " + LangStructClass.class.getSimpleName()
                    + ". Has " + structClassObj.toString());
        }

        final LangObject argsCountObj = function.popOperand().getValue(function);
        if (argsCountObj.getClass() != LangInteger.class || argsCountObj.toLangInteger().isNan) {
            throw new IllegalStateException("top operand of " + NewStructInstanceInstruction.class.getSimpleName()
                    + " must be valid int. Has " + argsCountObj.toString());
        }

        final LangStructClass structClass = (LangStructClass) structClassObj;
        final int argsCount = argsCountObj.toLangInteger().intValue;
        final LangStructClass.InstanceBuilder instanceBuilder = structClass.newInstanceBuilder(argsCount);
        for (int i = 0; i < argsCount; ++i) {
            instanceBuilder.withArgument(function.popOperand().getValue(function));
        }

        function.pushOperand(instanceBuilder.build());
    }
}
