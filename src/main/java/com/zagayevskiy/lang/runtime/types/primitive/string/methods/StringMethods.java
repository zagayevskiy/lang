package com.zagayevskiy.lang.runtime.types.primitive.string.methods;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

public interface StringMethods {
    IMethodPrototype[] PROTOTYPES = new IMethodPrototype[]{
        new SubString()
    };
}
