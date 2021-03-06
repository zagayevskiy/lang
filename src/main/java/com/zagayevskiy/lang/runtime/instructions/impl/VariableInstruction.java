package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.AssignableOperand;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class VariableInstruction implements AssignableOperand, Instruction {

    private static final Map<Integer, VariableInstruction> CACHE = new HashMap<>();

    private final int id;
    private final String name;

    @Nonnull
    public static VariableInstruction from(int variableId, @Nonnull String name) {
        VariableInstruction cache = CACHE.get(variableId);
        if (cache == null) {
            cache = new VariableInstruction(variableId, name);
            CACHE.put(variableId, cache);
        }
        return cache;
    }

    private VariableInstruction(int id, @Nonnull String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void execute(@Nonnull IContext context) {
        context.pushOperand(this);
    }

    @Override
    public void setValue(@Nonnull IContext context, @Nonnull LangObject value) {
        context.getVariable(id).setValue(value);
    }

    @Nonnull
    @Override
    public LangObject getValue(@Nonnull IContext context) {
        return context.getVariable(id).getValue();
    }

    @Override
    public String toString() {
        return name;
    }
}
