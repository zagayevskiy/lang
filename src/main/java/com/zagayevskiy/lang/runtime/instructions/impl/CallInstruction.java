package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class CallInstruction implements Instruction {
    @Override
    public void execute(@Nonnull IFunction context) {

        final LangObject actualArgsCountObj = context.popOperand().getValue(context);
        if (actualArgsCountObj.getClass() != LangInteger.class || actualArgsCountObj.toLangInteger().isNan) {
            throw new IllegalStateException("second argument of call must be valid int");
        }

        final int actualArgsCount = actualArgsCountObj.toLangInteger().intValue;

        final LangObject[] argumentsArray = new LangObject[actualArgsCount];
        for (int i = actualArgsCount - 1; i >= 0; --i) {
            argumentsArray[i] = context.popOperand().getValue(context);
        }

        final LangObject functionClassObj = context.popOperand().getValue(context);
        if (!(functionClassObj instanceof IFunctionClass)) {
            context.pushOperand(LangUndefined.INSTANCE);
            return;
        }

        final IFunctionClass functionClass = (IFunctionClass) functionClassObj;
        final int argsCount = functionClass.getArgumentsCount();

        List<LangObject> arguments = Arrays.asList(argumentsArray);

        if (actualArgsCount < argsCount) {
            context.pushOperand(functionClass.applyPartially(arguments));
            return;
        }

        if (actualArgsCount > argsCount) {
            arguments = arguments.subList(0, argsCount);
        }

        context.pushOperand(functionClass.newInstance(arguments).execute());
    }
}
