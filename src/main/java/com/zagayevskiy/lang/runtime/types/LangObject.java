package com.zagayevskiy.lang.runtime.types;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.classes.LangClass;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;

import javax.annotation.Nonnull;
import java.util.Comparator;

public interface LangObject extends Instruction, Operand {

    Comparator<LangObject> COMPARATOR = new Comparator<LangObject>() {
        @Override
        public int compare(@Nonnull LangObject o1, @Nonnull LangObject o2) {

            if (o1.getClass() == LangInteger.class && o2.getClass() == LangInteger.class) {
                return o1.toLangInteger().compareTo(o2.toLangInteger());
            }

            return o1.toLangString().compareTo(o2);
        }
    };

    @Nonnull
    LangBoolean toLangBoolean();

    @Nonnull
    LangInteger toLangInteger();

    @Nonnull
    LangString toLangString();

    @Nonnull
    LangObject plus(@Nonnull LangObject other);

    @Nonnull
    LangClass getLangClass();
}
