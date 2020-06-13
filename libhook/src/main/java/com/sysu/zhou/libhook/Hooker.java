package com.sysu.zhou.libhook;

import android.content.Context;

public class Hooker {
    public static void init(Context context) {
        if (context == null) {
            return;
        }

    }

    private native String init();
}
