package com.zagayevskiy.lang.runtime.userclass;

import com.zagayevskiy.lang.runtime.types.base.AbsLangObject;
import com.zagayevskiy.lang.runtime.types.base.LangClass;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

class UserClassPrototype extends AbsLangObject implements IUserClassPrototype {

    @Nonnull
    private final String name;

    @Nonnull
    private final Map<String, IMethodPrototype> methodPrototypes;

    @Nonnull
    private final Map<Integer, IUserClass.Constructor> constructors;

    UserClassPrototype(@Nonnull String name,
                              @Nonnull Map<String, IMethodPrototype> methodPrototypes,
                              @Nonnull Map<Integer, IUserClass.Constructor> constructors) {
        this.name = name;
        this.methodPrototypes = methodPrototypes;
        this.constructors = constructors;
    }

    @Nullable
    @Override
    public IMethodPrototype getMethodPrototype(@Nonnull String name) {
        return methodPrototypes.get(name);
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return name;
    }

    @Nonnull
    @Override
    public LangBoolean toLangBoolean() {
        return LangBoolean.FALSE;
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.NaN;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from(toString());
    }

    @Override
    public String toString() {
        return "class$" + getLangClassName();
    }

    @Nonnull
    @Override
    public LangClass getLangClass() {
        return this;
    }

    @Nullable
    @Override
    public IUserClass.Constructor getConstructor(int argumentsCount) {
        return constructors.get(argumentsCount);
    }

    @Nonnull
    public LangObject newInstance() {
        return new UserClass(this);
    }
}
