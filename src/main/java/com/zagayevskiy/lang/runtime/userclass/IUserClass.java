package com.zagayevskiy.lang.runtime.userclass;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;
import java.util.List;

public interface IUserClass extends LangObject {
    @Nonnull
    @Override
    IUserClassPrototype getLangClass();

    interface Constructor {
        int getArgumentsCount();

        @Nonnull
        LangObject newInstance(@Nonnull IContext context, @Nonnull List<LangObject> arguments);
    }
}
