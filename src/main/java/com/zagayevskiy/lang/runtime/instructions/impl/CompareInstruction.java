package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsBinaryObjInstruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;

import javax.annotation.Nonnull;

public abstract class CompareInstruction extends AbsBinaryObjInstruction {

    @Nonnull
    @Override
    protected final Operand execute(@Nonnull LangObject left, @Nonnull LangObject right) {
        return LangBoolean.from(interpretCompare(LangObject.COMPARATOR.compare(left, right)));
    }

    abstract boolean interpretCompare(int compare);

    public static final CompareInstruction GREATER = new CompareInstruction() {
        @Override
        boolean interpretCompare(int compare) {
            return compare > 0;
        }

        @Override
        public String toString() {
            return ">";
        }
    };

    public static final CompareInstruction GREATER_OR_EQUALS = new CompareInstruction() {
        @Override
        boolean interpretCompare(int compare) {
            return compare >= 0;
        }

        @Override
        public String toString() {
            return ">=";
        }
    };

    public static final CompareInstruction LESS = new CompareInstruction() {
        @Override
        boolean interpretCompare(int compare) {
            return compare < 0;
        }

        @Override
        public String toString() {
            return "<";
        }
    };

    public static final CompareInstruction LESS_OR_EQUALS = new CompareInstruction() {
        @Override
        boolean interpretCompare(int compare) {
            return compare <= 0;
        }

        @Override
        public String toString() {
            return "<";
        }
    };
}
