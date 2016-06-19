package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;

import javax.annotation.Nonnull;
import java.util.List;

class ToBooleanProto extends AbsBuiltInMethodPrototype {

    ToBooleanProto() {
        super("toBoolean", 0);
    }

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
        protected LangObject call(@Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
            return self.toLangBoolean();
        }
    }
}
