package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;

import javax.annotation.Nonnull;
import java.util.List;

public class SubStringProto extends AbsBuiltInMethodPrototype {
    public SubStringProto() {
        super("subString", 2);
    }

    @Override
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        return new AbsBuiltInMethod(getName(), arguments) {
            @Nonnull
            @Override
            protected LangObject call(@Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
                //TODO refactor it
                int from = arguments.get(0).toLangInteger().intValue;
                int to = arguments.get(1).toLangInteger().intValue;
                return LangString.from(self.toLangString().stringValue.substring(from, to));
            }
        };
    }
}
