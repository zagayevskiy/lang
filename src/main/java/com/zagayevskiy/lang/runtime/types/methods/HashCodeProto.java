package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;

import javax.annotation.Nonnull;
import java.util.List;

public class HashCodeProto extends AbsBuiltInMethodPrototype {

    public HashCodeProto() {
        super("hashCode", 0);
    }

    @Override
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        return new AbsBuiltInMethod(getName(), arguments) {
            @Nonnull
            @Override
            protected LangObject call(@Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
                return LangInteger.from(self.hashCode());
            }
        };
    }
}
