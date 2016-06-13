package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsBinaryObjInstruction;
import com.zagayevskiy.lang.runtime.operand.AssignableOperand;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.IContext;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangStruct;
import com.zagayevskiy.lang.runtime.types.classes.LangClass;
import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.Collections;

public class BindProperty extends AbsBinaryObjInstruction {

    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangObject left, @Nonnull LangObject right) {
        if (right.getClass() != LangString.class) {
            //TODO
            throw new IllegalStateException();
        }
        if (left.getClass() == LangStruct.class) {
            return new PropertyReference((LangStruct) left, right.toLangString().stringValue);
        }

        return new MethodReference(left, right.toLangString().stringValue);
    }

    private static final class MethodReference implements AssignableOperand {
        @Nonnull
        private final String name;
        @Nonnull
        private final LangObject object;

        public MethodReference(@Nonnull LangObject object, @Nonnull String name) {
            this.name = name;
            this.object = object;
        }

        @Override
        public void setValue(@Nonnull IContext context, @Nonnull LangObject value) {
            //TODO
        }

        @Nonnull
        @Override
        public LangObject getValue(@Nonnull IContext context) {
            final LangClass clazz = object.getLangClass();
            final IMethodPrototype methodPrototype = clazz.getMethodPrototype(name);
            if (methodPrototype == null) {
                return LangUndefined.INSTANCE;
            }
            if (methodPrototype.isSelfBound()) {
                return methodPrototype;
            }
            return methodPrototype.applyPartially(Collections.singletonList(object));
        }
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
        public void setValue(@Nonnull IContext context, @Nonnull LangObject value) {
            struct.setPropertyValue(name, value);
        }

        @Nonnull
        @Override
        public LangObject getValue(@Nonnull IContext context) {
            final LangObject value = struct.getPropertyValue(name);

            //TODO may be throw?
            return value == null ? LangUndefined.INSTANCE : value;
        }
    }
}
