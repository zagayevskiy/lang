package com.zagayevskiy.lang.runtime.types;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public abstract class LangObject implements Instruction, Operand {

    @Nonnull
    public abstract LangString toLangString();

    @Nonnull
    public abstract LangInteger toLangInteger();

    @Nonnull
    public LangObject plus(@Nonnull LangObject other) {
        return LangString.from(toLangString().stringValue + other.toLangString().stringValue);
    }

    @Nonnull
    @Override
    public LangObject getValue() {
        return this;
    }

    @Override
    public void execute(@Nonnull IProgram program, @Nonnull IFunction function) {
        program.pushOperand(this);
    }
}
