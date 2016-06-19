package com.zagayevskiy.lang.runtime.types.primitive.string.methods;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.base.methods.AbsBuiltInMethod;
import com.zagayevskiy.lang.runtime.types.base.methods.AbsBuiltInMethodPrototype;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;

import javax.annotation.Nonnull;
import java.util.List;

class SubString extends AbsBuiltInMethodPrototype {
    SubString() {
        super("subString", 2);
    }

    @Nonnull
    @Override
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        return new AbsBuiltInMethod(getName(), arguments) {
            @Nonnull
            @Override
            protected LangObject call(@Nonnull IContext context, @Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
                //TODO refactor it
                int from = arguments.get(0).toLangInteger().intValue;
                int to = arguments.get(1).toLangInteger().intValue;
                return LangString.from(self.toLangString().stringValue.substring(from, to));
            }
        };
    }
}
