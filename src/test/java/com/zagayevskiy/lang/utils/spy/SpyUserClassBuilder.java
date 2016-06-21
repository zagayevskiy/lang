package com.zagayevskiy.lang.utils.spy;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.runtime.userclass.IUserClassPrototype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//TODO spy!
public class SpyUserClassBuilder implements IUserClassPrototype.Builder {
    public final IUserClassPrototype.Builder inner;

    public SpyUserClassBuilder(IUserClassPrototype.Builder inner) {
        this.inner = inner;
    }

    @Override
    @Nonnull
    public IUserClassPrototype getBuildingUserClassPrototype() {
        return inner.getBuildingUserClassPrototype();
    }

    @Override
    public void addMethodPrototype(@Nonnull IMethodPrototype methodPrototype) {
        inner.addMethodPrototype(methodPrototype);
    }

    @Override
    public void addConstructor(@Nonnull IMethodPrototype constructorProto) {
        inner.addConstructor(constructorProto);
    }

    @Override
    @Nullable
    public IMethodPrototype getMethodPrototype(@Nonnull String name) {
        return inner.getMethodPrototype(name);
    }

}
