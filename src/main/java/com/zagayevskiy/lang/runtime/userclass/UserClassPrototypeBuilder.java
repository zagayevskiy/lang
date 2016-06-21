package com.zagayevskiy.lang.runtime.userclass;

import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;
import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserClassPrototypeBuilder implements IUserClassPrototype.Builder {

    @Nonnull
    private final UserClassPrototype userClassPrototype;
    @Nonnull
    private final Map<String, IMethodPrototype> methodPrototypes = new HashMap<>();
    @Nonnull
    private final Map<Integer, IUserClass.Constructor> constructors = new HashMap<>();

    public UserClassPrototypeBuilder(@Nonnull String name) {
        userClassPrototype = new UserClassPrototype(name,
                Collections.unmodifiableMap(methodPrototypes),
                Collections.unmodifiableMap(constructors));
    }

    @Nonnull
    @Override
    public IUserClassPrototype getBuildingUserClassPrototype() {
        return userClassPrototype;
    }

    @Override
    public void addConstructor(@Nonnull IMethodPrototype constructorProto) {
        final Integer actualArgsCount = constructorProto.getArgumentsCount() - 1;
        if (constructors.get(actualArgsCount) != null) {
            throw new IllegalStateException("constructor with " + actualArgsCount + " arguments already exists");
        }
        
        //TODO: fix wildcards
        final IFunctionPrototype actualConstructor = constructorProto.applyPartially(Collections.<LangObject>singletonList(userClassPrototype));

        constructors.put(actualArgsCount, new UserClassConstructor(actualConstructor));
    }

    @Override
    public void addMethodPrototype(@Nonnull IMethodPrototype methodPrototype) {
        methodPrototypes.put(methodPrototype.getName(), methodPrototype);
    }

    @Nullable
    @Override
    public IMethodPrototype getMethodPrototype(@Nonnull String name) {
        return methodPrototypes.get(name);
    }
}
