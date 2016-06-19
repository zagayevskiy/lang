package com.zagayevskiy.lang.logging;

import javax.annotation.Nonnull;

public interface RuntimeLogger {
    void println(@Nonnull String message);
    void warning(@Nonnull String message);
}
