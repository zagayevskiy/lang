package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.runtime.userclass.IUserClassPrototype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DummyUserClassBuilder implements IUserClassPrototype.Builder {
    DummyUserClass userClass;
    Map<String, IMethodPrototype> methods = new HashMap<>();

    public DummyUserClassBuilder(String name) {
        this.userClass = new DummyUserClass(name);
    }

    @Nonnull
    @Override
    public IUserClassPrototype getBuildingUserClassPrototype() {
        return userClass;
    }

    @Override
    public void addMethodPrototype(@Nonnull IMethodPrototype methodPrototype) {
        methods.put(methodPrototype.getName(), methodPrototype);
    }

    @Override
    public void addConstructor(@Nonnull IMethodPrototype constructorProto) {

    }

    @Nullable
    @Override
    public IMethodPrototype getMethodPrototype(@Nonnull String name) {
        return methods.get(name);
    }
}
