package com.zagayevskiy.lang.tokenization;

import javax.annotation.Nonnull;
import java.io.IOException;

public interface Tokenizer {

    @Nonnull
    Token nextToken() throws IOException;
}
