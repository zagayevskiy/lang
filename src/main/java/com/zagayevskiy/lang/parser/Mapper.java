package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.tokenization.Token;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class Mapper {

    @Nullable
    static Instruction multiplication(@Nonnull Token token) {
        switch (token.type) {
            case Token.ASTERISK: return Instruction.MULTIPLY;
            case Token.PERCENT: return Instruction.MOD;
            case Token.SLASH: return Instruction.DIVISION;
            default: return null;
        }
    }
}
