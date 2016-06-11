package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.tokenization.Token;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class Mapper {

    @Nullable
    static Instruction addition(@Nonnull Token token) {
        switch (token.type) {
            case Token.PLUS: return Instruction.PLUS;
            case Token.MINUS: return  Instruction.MINUS;
        }
        return null;
    }

    @Nullable
    static Instruction bitShift(@Nonnull Token token) {
        switch (token.type) {
            case Token.BIT_SHIFT_LEFT: return Instruction.BIT_SHIFT_LEFT;
            case Token.BIT_SHIFT_RIGHT: return Instruction.BIT_SHIFT_RIGHT;
        }
        return null;
    }

    @Nullable
    static Instruction equality(@Nonnull Token token) {
        switch (token.type) {
            case Token.EQUALS: return Instruction.EQUALS;
            case Token.NOT_EQUALS: return Instruction.NOT_EQUALS;
        }
        return null;
    }

    @Nullable
    static Instruction multiplication(@Nonnull Token token) {
        switch (token.type) {
            case Token.ASTERISK: return Instruction.MULTIPLY;
            case Token.PERCENT: return Instruction.MOD;
            case Token.SLASH: return Instruction.DIVISION;
        }
        return null;
    }
}
