package com.zagayevskiy.lang.runtime.types;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.classes.LangClass;

import javax.annotation.Nonnull;

public abstract class LangObject implements Instruction, Operand {

    @Nonnull
    public abstract LangBoolean toLangBoolean();

    @Nonnull
    public abstract LangInteger toLangInteger();

    @Nonnull
    public abstract LangString toLangString();

    @Nonnull
    public LangObject plus(@Nonnull LangObject other) {
        return LangString.from(toLangString().stringValue + other.toLangString().stringValue);
    }

    @Nonnull
    @Override
    public LangObject getValue(@Nonnull IFunction function) {
        return this;
    }

    @Override
    public void execute(@Nonnull IFunction function) {
        function.pushOperand(this);
    }
}
