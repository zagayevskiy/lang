package com.zagayevskiy.lang.runtime.types.base.methods;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangClass;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;
import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbsBuiltInMethodPrototype implements IMethodPrototype {

    @Nonnull
    private final String name;
    private final int argumentsCount;

    @Nullable
    private final AbsBuiltInMethodPrototype wrapped;

    @Nonnull
    private final List<LangObject> arguments;

    protected AbsBuiltInMethodPrototype(@Nonnull String name, int actualArgumentsCount) {
        this.name = name;
        this.argumentsCount = actualArgumentsCount + 1; //With first argument as self
        arguments = Collections.emptyList();
        this.wrapped = null;
    }

    private AbsBuiltInMethodPrototype(@Nonnull AbsBuiltInMethodPrototype wrapped, @Nonnull List<LangObject> arguments) {
        this.arguments = arguments;
        this.wrapped = wrapped;
        this.argumentsCount = wrapped.argumentsCount - arguments.size();
        this.name = wrapped.name;
    }

    @Override
    public final boolean isSelfBound() {
        return wrapped != null;
    }

    @Nonnull
    @Override
    public final IFunction newInstance(@Nonnull List<LangObject> arguments) {
        if (wrapped == null) {
            throw new IllegalStateException("applyPartially() not called before newInstance()" );
        }
        if (arguments.size() != getArgumentsCount()) {
            throw new IllegalStateException("Invalid arguments count! "
                    + "Has " + (arguments.size())
                    + ", but want " + getArgumentsCount());
        }

        List<LangObject> finalArguments;
        if (arguments.isEmpty()) {
            finalArguments = this.arguments;
        } else {
            finalArguments = new ArrayList<>(this.arguments);
            finalArguments.addAll(arguments);
            finalArguments = Collections.unmodifiableList(finalArguments);
        }

        return wrapped.newInstanceImpl(finalArguments);
    }

    @Nonnull
    protected IFunction newInstanceImpl(@Nonnull List<LangObject> arguments) {
        throw new RuntimeException("child must override it");
    }

    @Nonnull
    @Override
    public final IFunctionPrototype applyPartially(@Nonnull List<LangObject> arguments) {
        if (arguments.isEmpty()) {
            throw new IllegalStateException("Want at least 1 argumetn to apply it partially.");
        }

        if (arguments.size() + this.arguments.size() > getArgumentsCount()) {
            throw new IllegalStateException("Too much arguments! "
                     + "Has " + (arguments.size() + this.arguments.size())
                     + ", but want " + getArgumentsCount());
        }
        List<LangObject> boundArguments;
        if (this.arguments.isEmpty()) {
            boundArguments = arguments;
        } else {
            boundArguments = new ArrayList<>(this.arguments);
            boundArguments.addAll(arguments);
            boundArguments = Collections.unmodifiableList(boundArguments);
        }

        return new AbsBuiltInMethodPrototype(wrapped == null ? this : wrapped, boundArguments);
    }

    @Override
    public final int getArgumentsCount() {
        return argumentsCount;
    }

    @Nonnull
    @Override
    public final LangBoolean toLangBoolean() {
        return LangBoolean.FALSE;
    }

    @Nonnull
    @Override
    public final LangInteger toLangInteger() {
        return LangInteger.NaN;
    }

    @Nonnull
    @Override
    public final LangString toLangString() {
        return LangString.from(toString());
    }

    @Override
    public final String toString() {
        return "builtin" + "$" + getName();
    }

    @Nonnull
    @Override
    public final LangObject plus(@Nonnull LangObject other) {
        return LangUndefined.INSTANCE;
    }

    @Override
    public final void execute(@Nonnull IContext context) {
        context.pushOperand(this);
    }

    @Nonnull
    @Override
    public final LangObject getValue(@Nonnull IContext context) {
        return this;
    }

    @Nonnull
    @Override
    public final String getName() {
        return name;
    }

    @Nonnull
    @Override
    public LangClass getLangClass() {
        return MethodPrototypeClass.INSTANCE;
    }
}
