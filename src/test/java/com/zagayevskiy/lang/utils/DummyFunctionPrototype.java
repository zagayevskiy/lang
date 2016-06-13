package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.types.IContext;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.List;

class DummyFunctionPrototype implements IFunctionPrototype {

    private final String name;

    DummyFunctionPrototype(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public IFunction newInstance(@Nonnull List<LangObject> arguments) {
        return new DummyFunction(name);
    }

    @Nonnull
    @Override
    public IFunctionPrototype applyPartially(@Nonnull List<LangObject> arguments) {
        return new DummyFunctionPrototype(name);
    }

    @Override
    public int getArgumentsCount() {
        return 0;
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
        return LangString.from("dummy_function_class$" + name);
    }

    @Nonnull
    @Override
    public LangObject plus(@Nonnull LangObject other) {
        return LangUndefined.INSTANCE;
    }

    @Nonnull
    @Override
    public LangObject getValue(@Nonnull IContext function) {
        return this;
    }

    @Override
    public void execute(@Nonnull IContext context) {
        context.pushOperand(this);
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return "dummy_func$" + name;
    }
}
