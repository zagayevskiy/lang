package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

public interface Methods {
    IMethodPrototype[] DEFAULT_METHODS = new IMethodPrototype[]{
            new HashCodeProto(),
            new ToIntegerProto(),
            new ToStringProto(),
            new SubStringProto(),
            new ArraySizeProto()
    };
}
