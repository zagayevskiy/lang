package com.zagayevskiy.lang.runtime.userclass;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;

import javax.annotation.Nonnull;
import java.util.List;

public class UserClassConstructor implements IUserClass.Constructor {

    @Nonnull
    private final IFunctionPrototype actualConstructor;

    public UserClassConstructor(@Nonnull IFunctionPrototype actualConstructor) {
        this.actualConstructor = actualConstructor;
    }

    @Override
    public int getArgumentsCount() {
        return actualConstructor.getArgumentsCount();
    }

    @Nonnull
    @Override
    public LangObject newInstance(@Nonnull IContext context, @Nonnull List<LangObject> arguments) {
        return actualConstructor
                    .newInstance(arguments)
                    .call(context);
    }
}
