package com.zagayevskiy.lang.runtime.types.array.methods;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

public interface ArrayMethods {
    IMethodPrototype[] PROTOTYPES = new IMethodPrototype[]{
            new Size()
    };
}
