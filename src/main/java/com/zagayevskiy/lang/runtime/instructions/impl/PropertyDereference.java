package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.instructions.AbsBinaryObjInstruction;
import com.zagayevskiy.lang.runtime.operand.AssignableOperand;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.LangStruct;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;

public class PropertyDereference extends AbsBinaryObjInstruction {

    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangObject left, @Nonnull LangObject right) {
        if (left.getClass() != LangStruct.class) {
            //TODO
            throw new IllegalStateException();
        }
        if (right.getClass() != LangString.class) {
            //TODO
            throw new IllegalStateException();
        }
        return new PropertyReference((LangStruct)left, right.toLangString().stringValue);
    }

    private static final class PropertyReference implements AssignableOperand {
        @Nonnull
        private final LangStruct struct;
        @Nonnull
        private final String name;

        private PropertyReference(@Nonnull LangStruct struct, @Nonnull String name) {
            this.struct = struct;
            this.name = name;

        }

        @Override
        public void setValue(@Nonnull IFunction context, @Nonnull LangObject value) {
            struct.setPropertyValue(name, value);
        }

        @Nonnull
        @Override
        public LangObject getValue(@Nonnull IFunction context) {
            final LangObject value = struct.getPropertyValue(name);

            //TODO may be throw?
            return value == null ? LangUndefined.INSTANCE : value;
        }
    }
}
