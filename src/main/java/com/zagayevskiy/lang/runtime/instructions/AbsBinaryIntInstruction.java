package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public abstract class AbsBinaryIntInstruction extends AbsBinaryObjInstruction {

    @Nonnull
    @Override
    protected final Operand execute(@Nonnull LangObject left, @Nonnull LangObject right) {
        return execute(left.toLangInteger(), right.toLangInteger());
    }

    @Nonnull
    protected abstract LangInteger execute(@Nonnull LangInteger left, @Nonnull LangInteger right);
}
