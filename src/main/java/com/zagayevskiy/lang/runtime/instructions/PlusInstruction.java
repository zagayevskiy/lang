package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.operand.ConstOperand;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangUndefined;

import javax.annotation.Nonnull;

public class PlusInstruction extends AbsBinaryInstruction {

    public static final PlusInstruction INSTANCE = new PlusInstruction();

    private  PlusInstruction(){

    }

    @Nonnull
    @Override
    protected Operand execute(@Nonnull Operand op1, @Nonnull Operand op2) {

        //TODO: refactor this shit
        final ConstOperand c1 = (ConstOperand) op1;
        final ConstOperand c2 = (ConstOperand) op2;

        if (!(c1.getValue() instanceof LangInteger) || !(c2.getValue() instanceof LangInteger)) {
            return new ConstOperand(LangUndefined.INSTANCE);
        }

        final LangInteger langInteger1 = ((LangInteger) c1.getValue());
        final LangInteger langInteger2 = ((LangInteger) c2.getValue());

        final LangInteger sum = LangInteger.from(langInteger1.intValue + langInteger2.intValue, langInteger1.isNan || langInteger2.isNan);

        return new ConstOperand(sum);
    }

    @Override
    public String toString() {
        return "+";
    }
}
