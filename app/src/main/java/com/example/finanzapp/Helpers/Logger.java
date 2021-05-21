package com.example.finanzapp.Helpers;

import android.util.Log;

public final class Logger {

    private Logger(){}

    public static void log(String msg, Class c){
        Log.d(c.getSimpleName(),msg);
    }
}
