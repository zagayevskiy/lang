package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;

import javax.annotation.Nonnull;
import java.util.List;

public class ToIntegerProto extends AbsBuiltInMethodPrototype {
    public ToIntegerProto() {
        super("toInteger", 0);
    }

    @Override
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        return new AbsBuiltInMethod(getName(), arguments) {
            @Nonnull
            @Override
            protected LangObject call(@Nonnull LangObject self, @Nonnull List<LangObject> arguments) {
                try {
                    //TODO refactor it
                    return LangInteger.from(Integer.parseInt(self.toLangString().stringValue));
                } catch (NumberFormatException ignored){
                }
                return self.toLangInteger();
            }
        };
    }
}
