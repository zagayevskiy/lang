package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;

import javax.annotation.Nonnull;
import java.util.List;

class ToStringProto extends AbsBuiltInMethodPrototype {

    ToStringProto() {
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
