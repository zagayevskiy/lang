package com.zagayevskiy.lang;

import com.zagayevskiy.lang.runtime.types.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Programs {
    public static final List<Pair<String, LangObject>> PROGRAMS = new ArrayList<>();

    static {
//        p("main{;}", LangUndefined.INSTANCE);
        p("main {{{{{{{{{{13579;}}}}}}}}}}", 13579);
        p("main { 2 + 3 * (1 - 17 + (1 << 1) * 2) - 22; }", 2 + 3 * (1 - 17 + (1 << 1) * 2) - 22);
        p("main {var a = 1, b = 2, c = 7, x = a, z = b, y = c;} ", 7);
        p("main { var x, y, z=123, t, qwerty = 971,\n asdf=345; var g, h, k; \n var temp = qwerty; }", 971);
        p("main { 1; var x = 2; var y = (x + 1), z = y + 2;  x * 10; var result = x<<y<<z<<4; }", 2 << 3 << 5 << 4);
        p("main { var x = 10; var y = x + 5; }", 15);
        p("main { var x = 7; var y = x + 9; x = y + 123; }", 139);
        p("main { true; false; }", false);
        p("main { true || false; }", true);
        p("main { var a = true, b = true, c = false; var AorB = a || b, BandA = b && a, notC = !c; AorB && BandA && notC; }", true);
        p("main { var a = true, b = true, c = false; var AorB = a || b, BandA = b && a; c = c; AorB && BandA && c; }", false);
        p("main { if (true) 1; else 2; }", 1);
        p("main { if (true) 3; }", 3);
        p("main { if (false) 4; }", LangUndefined.INSTANCE);
        p("main { if (true) 100 + 200; else 300*700; }", 100 + 200);
        p("main { if (false) 10 * 222; else {3012*70013;} }", 3012 * 70013);
        p("main { if (1 + 3 - 2 * 2) { var x = 123 } else { var y = 321 }; true; false; 4223; x + y; }", LangUndefined.STRING_VALUE + 321);
    }

    private static void p(String s, boolean b) {
        p(s, LangBoolean.from(b));
    }

    private static void p(String s, String r) {
        p(s, LangString.from(r));
    }

    private static void p(String s, int i) {
        p(s, LangInteger.from(i));
    }

    private static void p(String s, LangObject o) {
        PROGRAMS.add(new Pair<>(s, o));
    }
}
