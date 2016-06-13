package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.array.LangArray;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.List;

public class ArraySizeProto extends AbsBuiltInMethodPrototype {
    public ArraySizeProto() {
        super("size", 0);
    }

    @Override
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        return new AbsBuiltInMethod(getName(), arguments) {
            @Nonnull
            @Override
            protected LangObject call(@Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
                if (self.getClass() != LangArray.class) {
                    return LangUndefined.INSTANCE;
                }
                return LangInteger.from(((LangArray) self).size());
            }
        };
    }
}
