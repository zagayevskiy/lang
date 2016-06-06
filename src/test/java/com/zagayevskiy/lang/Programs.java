package com.zagayevskiy.lang;

import com.zagayevskiy.lang.runtime.types.LangBoolean;
import com.zagayevskiy.lang.runtime.types.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangUndefined;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Programs {
    public static final List<Pair<String, LangObject>> PROGRAMS = new ArrayList<>();
    static {
//        p("main{;}", LangUndefined.INSTANCE);
        p("main { 2 + 3 * (1 - 17 + (1 << 1) * 2) - 22; }", LangInteger.from(-56));
        p("main { var x, y, z=123, t, qwerty = 971,\n asdf=345; var g, h, k; \n var temp = qwerty; }", LangInteger.from(971));
        p("main { 1; var x = 2; var y = (x + 1), z = y + 2;  x * 10; var result = x<<y<<z<<4; }", LangInteger.from(8192));
        p("main { var x = 10; var y = x + 5; }", LangInteger.from(15));
        p("main { var x = 7; var y = x + 9; x = y + 123; }", LangInteger.from(139));
        p("main { true; false; }", LangBoolean.FALSE);
        p("main { true || false; }", LangBoolean.TRUE);
        p("main { var a = true, b = true, c = false; var AorB = a || b, BandA = b && a, notC = !c; AorB && BandA && notC; }", LangBoolean.TRUE);
        p("main { var a = true, b = true, c = false; var AorB = a || b, BandA = b && a; c = c; AorB && BandA && c; }", LangBoolean.FALSE);
    }

    private static void p(String s, LangObject o) {
        PROGRAMS.add(new Pair<>(s, o));
    }
}
