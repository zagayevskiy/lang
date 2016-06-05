package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.operand.ConstOperand;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangInteger;

import javax.annotation.Nonnull;

public class MultiplyInstruction extends AbsBinaryInstruction {

    public static final MultiplyInstruction INSTTANCE = new MultiplyInstruction();

    private MultiplyInstruction() {

    }

    @Nonnull
    @Override
    protected Operand execute(@Nonnull Operand op1, @Nonnull Operand op2) {


        //TODO: refactor this shit
        final ConstOperand c1 = (ConstOperand) op1;
        final ConstOperand c2 = (ConstOperand) op2;

        final LangInteger i1 = c1.getValue().toLangInteger();
        final LangInteger i2 = c2.getValue().toLangInteger();

        return new ConstOperand(LangInteger.from(i1.intValue * i2.intValue, i1.isNan || i2.isNan));
    }

    @Override
    public String toString() {
        return "*";
    }
}