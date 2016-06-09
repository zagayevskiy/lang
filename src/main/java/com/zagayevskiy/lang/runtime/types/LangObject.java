package com.zagayevskiy.lang.runtime.types;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;

import javax.annotation.Nonnull;

public interface LangObject extends Instruction, Operand {

    @Nonnull
    LangBoolean toLangBoolean();

    @Nonnull
    LangInteger toLangInteger();

    @Nonnull
    LangString toLangString();

    @Nonnull
    LangObject plus(@Nonnull LangObject other);

    @Nonnull
    @Override
    LangObject getValue(@Nonnull IFunction function);

    @Override
    void execute(@Nonnull IFunction function);
}
