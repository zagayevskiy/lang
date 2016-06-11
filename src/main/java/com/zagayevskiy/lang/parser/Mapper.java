package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.instructions.impl.CompareInstruction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.tokenization.Token;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class Mapper {

    @Nullable
    static Instruction addition(@Nonnull Token token) {
        switch (token.type) {
            case Token.PLUS:
                return Instruction.PLUS;
            case Token.MINUS:
                return Instruction.MINUS;
        }
        return null;
    }

    @Nullable
    static Instruction bitShift(@Nonnull Token token) {
        switch (token.type) {
            case Token.BIT_SHIFT_LEFT:
                return Instruction.BIT_SHIFT_LEFT;
            case Token.BIT_SHIFT_RIGHT:
                return Instruction.BIT_SHIFT_RIGHT;
        }
        return null;
    }

    @Nullable
    static Instruction comparison(@Nonnull Token token) {
        switch (token.type) {
            case Token.GREATER:
                return CompareInstruction.GREATER;
            case Token.GREATER_OR_EQUALS:
                return CompareInstruction.GREATER_OR_EQUALS;
            case Token.LESS:
                return CompareInstruction.LESS;
            case Token.LESS_OR_EQUALS:
                return CompareInstruction.LESS_OR_EQUALS;
        }
        return null;
    }

    @Nullable
    static Instruction constant(@Nonnull Token token) {
        switch (token.type) {
            case Token.FALSE:
                return LangBoolean.FALSE;
            case Token.TRUE:
                return LangBoolean.TRUE;
            case Token.INTEGER:
                final int intValue = Integer.parseInt(token.value);
                return LangInteger.from(intValue);
            case Token.STRING:
                return LangString.from(token.value);
        }
        return null;
    }

    @Nullable
    static Instruction equality(@Nonnull Token token) {
        switch (token.type) {
            case Token.EQUALS:
                return Instruction.EQUALS;
            case Token.NOT_EQUALS:
                return Instruction.NOT_EQUALS;
        }
        return null;
    }

    @Nullable
    static Instruction multiplication(@Nonnull Token token) {
        switch (token.type) {
            case Token.ASTERISK:
                return Instruction.MULTIPLY;
            case Token.PERCENT:
                return Instruction.MOD;
            case Token.SLASH:
                return Instruction.DIVISION;
        }
        return null;
    }

    @Nullable
    static Instruction unary(@Nonnull Token token) {
        switch (token.type) {
            case Token.LOGIC_NOT:
                return Instruction.LOGIC_NOT;
            case Token.BIT_NOT:
                return Instruction.BIT_NOT;
            case Token.MINUS:
                return Instruction.UNARY_MINUS;
        }
        return null;
    }
}
