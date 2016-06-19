package com.zagayevskiy.lang.utils.spy;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.base.LangClass;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SpyFunctionPrototype implements IFunctionPrototype {
    private final IFunctionPrototype inner;
    public List<IFunction> functions = new ArrayList<>();

    SpyFunctionPrototype(IFunctionPrototype inner) {
        this.inner = inner;
    }

    @Override
    @Nonnull
    public String getName() {
        return inner.getName();
    }

    @Override
    @Nonnull
    public IFunction newInstance(@Nonnull List<LangObject> arguments) {
        IFunction f = inner.newInstance(arguments);
        functions.add(f);
        return f;
    }

    @Override
    @Nonnull
    public IFunctionPrototype applyPartially(@Nonnull List<LangObject> arguments) {
        return inner.applyPartially(arguments);
    }

    @Override
    public int getArgumentsCount() {
        return inner.getArgumentsCount();
    }

    @Nonnull
    @Override
    public LangClass getLangClass() {
        return inner.getLangClass();
    }

    @Override
    @Nonnull
    public LangBoolean toLangBoolean() {
        return inner.toLangBoolean();
    }

    @Override
    @Nonnull
    public LangInteger toLangInteger() {
        return inner.toLangInteger();
    }

    @Override
    @Nonnull
    public LangString toLangString() {
        return inner.toLangString();
    }

    @Override
    @Nonnull
    public LangObject plus(@Nonnull LangObject other) {
        return inner.plus(other);
    }

    @Nonnull
    @Override
    public LangObject getValue(@Nonnull IContext function) {
        return inner.getValue(function);
    }

    @Override
    public void execute(@Nonnull IContext context) {
        inner.execute(context);
    }

    @Override
    public String toString() {
        return "spy$" + inner.toString();
    }
}
