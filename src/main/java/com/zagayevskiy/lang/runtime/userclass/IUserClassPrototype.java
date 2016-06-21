package com.zagayevskiy.lang.runtime.userclass;

import com.zagayevskiy.lang.runtime.types.base.LangClass;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IUserClassPrototype extends LangClass, LangObject {

    interface Builder {
        @Nonnull
        IUserClassPrototype getBuildingUserClassPrototype();

        void addMethodPrototype(@Nonnull IMethodPrototype methodPrototype);

        void addConstructor(@Nonnull IMethodPrototype constructorProto);

        @Nullable
        IMethodPrototype getMethodPrototype(@Nonnull String name);
    }

    @Nullable
    IUserClass.Constructor getConstructor(int argumentsCount);
}
