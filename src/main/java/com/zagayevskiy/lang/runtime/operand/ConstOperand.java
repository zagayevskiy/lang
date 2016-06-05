package com.zagayevskiy.lang.runtime.operand;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public class ConstOperand implements Operand {

    @Nonnull
    private final LangObject value;

    public ConstOperand(@Nonnull LangObject value) {
        this.value = value;
    }

    @Nonnull
    public LangObject getValue() {
        return value;
    }
}
