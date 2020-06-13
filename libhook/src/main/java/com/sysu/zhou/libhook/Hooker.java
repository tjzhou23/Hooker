package com.sysu.zhou.libhook;

import android.content.Context;

public class Hooker {

    static {
        System.loadLibrary("hooker");
    }

    public static void init(Context context) {
        if (context == null) {
            return;
        }

    }

    public static native boolean init(int sdk, boolean debug);
}
