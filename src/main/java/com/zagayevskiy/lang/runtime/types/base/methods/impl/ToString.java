package com.zagayevskiy.lang.runtime.types.base.methods.impl;

import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.base.methods.AbsBuiltInMethod;
import com.zagayevskiy.lang.runtime.types.base.methods.AbsBuiltInMethodPrototype;
import com.zagayevskiy.lang.runtime.types.function.IFunction;

import javax.annotation.Nonnull;
import java.util.List;

class ToString extends AbsBuiltInMethodPrototype {

    ToString() {
        super("toString", 0);
    }

    @Override
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        return new ToStringMethod(getName(), arguments);
    }

    private static final class ToStringMethod extends AbsBuiltInMethod {

        private ToStringMethod(@Nonnull String name, @Nonnull List<LangObject> arguments) {
            super(name, arguments);
        }

        @Nonnull
        @Override
        protected LangObject call(@Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
            return self.toLangString();
        }
    }
}
