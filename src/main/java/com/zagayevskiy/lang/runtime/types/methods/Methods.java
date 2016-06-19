package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

public interface Methods {
    IMethodPrototype[] DEFAULT_METHODS = new IMethodPrototype[]{
            new HashCodeProto(),
            new ToBooleanProto(),
            new ToIntegerProto(),
            new ToStringProto()
    };

    IMethodPrototype[] ARRAY_METHODS = new IMethodPrototype[]{
            new ArraySizeProto()
    };

    IMethodPrototype[] STRING_METHODS = new IMethodPrototype[] {
            new SubStringProto()
    };
}
