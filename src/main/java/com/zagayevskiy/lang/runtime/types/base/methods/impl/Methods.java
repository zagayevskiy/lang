package com.zagayevskiy.lang.runtime.types.base.methods.impl;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

public interface Methods {
    IMethodPrototype[] DEFAULT_METHODS = new IMethodPrototype[]{
            new HashCode(),
            new ToBoolean(),
            new ToInteger(),
            new ToString()
    };
}
