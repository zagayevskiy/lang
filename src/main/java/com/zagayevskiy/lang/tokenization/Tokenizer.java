package com.zagayevskiy.lang.tokenization;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public interface Tokenizer {

    @Nonnull
    Token nextToken() throws IOException;

    @Nullable
    String getLineDebug(int lineNumber);
}
