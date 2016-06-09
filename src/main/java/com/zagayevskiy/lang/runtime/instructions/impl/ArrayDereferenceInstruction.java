package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.instructions.AbsBinaryObjInstruction;
import com.zagayevskiy.lang.runtime.operand.AssignableOperand;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.array.LangArray;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public class ArrayDereferenceInstruction extends AbsBinaryObjInstruction {

    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangObject left, @Nonnull LangObject right) {

        if (left.getClass() != LangArray.class) {
            throw new IllegalStateException("first argument of " + ArrayDereferenceInstruction.class.getSimpleName() + " must be " + LangArray.class.getName());
        }

        if (right.getClass() != LangInteger.class || right.equals(LangInteger.NaN)) {
            throw new IllegalStateException("second argument of " + ArrayDereferenceInstruction.class.getSimpleName() + " must be valid int");
        }

        return new ArrayReference((LangArray) left, right.toLangInteger().intValue);
    }

    private static final class ArrayReference implements AssignableOperand {
        @Nonnull
        final LangArray array;
        final int index;

        ArrayReference(@Nonnull LangArray array, int index) {
            this.array = array;
            this.index = index;
        }

        @Override
        public void setValue(@Nonnull IFunction context, @Nonnull LangObject value) {
            this.array.set(index, value);
        }

        @Nonnull
        @Override
        public LangObject getValue(@Nonnull IFunction context) {
            return array.get(index);
        }

        @Override
        public String toString() {
            return "ref:" + ":" + array.toLangString().stringValue + ":" + index;
        }
    }

    @Override
    public String toString() {
        return "[deref]";
    }
}
