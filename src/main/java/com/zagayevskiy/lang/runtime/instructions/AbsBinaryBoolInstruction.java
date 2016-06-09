package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public abstract class AbsBinaryBoolInstruction extends AbsBinaryObjInstruction {

    @Nonnull
    @Override
    protected final Operand execute(@Nonnull LangObject left, @Nonnull LangObject right) {
        return execute(left.toLangBoolean(), right.toLangBoolean());
    }

    @Nonnull
    protected  abstract  Operand execute(@Nonnull LangBoolean left, @Nonnull LangBoolean right);
}
