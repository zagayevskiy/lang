package com.zagayevskiy.lang.runtime.types.base.methods.impl;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.base.methods.AbsBuiltInMethod;
import com.zagayevskiy.lang.runtime.types.base.methods.AbsBuiltInMethodPrototype;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;

import javax.annotation.Nonnull;
import java.util.List;

class HashCode extends AbsBuiltInMethodPrototype {

    HashCode() {
        super("hashCode", 0);
    }

    @Nonnull
    @Override
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        return new AbsBuiltInMethod(getName(), arguments) {
            @Nonnull
            @Override
            protected LangObject call(@Nonnull IContext context, @Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
                return LangInteger.from(self.hashCode());
            }
        };
    }
}
