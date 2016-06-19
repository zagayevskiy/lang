package com.zagayevskiy.lang.runtime.types.base.methods.impl;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.base.methods.AbsBuiltInMethod;
import com.zagayevskiy.lang.runtime.types.base.methods.AbsBuiltInMethodPrototype;
import com.zagayevskiy.lang.runtime.types.function.IFunction;

import javax.annotation.Nonnull;
import java.util.List;

class ToBoolean extends AbsBuiltInMethodPrototype {

    ToBoolean() {
        super("toBoolean", 0);
    }

    @Nonnull
    @Override
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        return new ToBooleanMethod(getName(), arguments);
    }

    private static final class ToBooleanMethod extends AbsBuiltInMethod {

        private ToBooleanMethod(@Nonnull String name, @Nonnull List<LangObject> arguments) {
            super(name, arguments);
        }

        @Nonnull
        @Override
        protected LangObject call(@Nonnull IContext context, @Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
            return self.toLangBoolean();
        }
    }
}
