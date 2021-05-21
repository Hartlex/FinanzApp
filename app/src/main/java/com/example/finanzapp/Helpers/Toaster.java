package com.example.finanzapp.Helpers;

import android.content.Context;
import android.widget.Toast;

public final class Toaster {
    private Toaster(){}

    public static void toast(String msg, Context ctx){
        Toast.makeText(ctx, msg , Toast.LENGTH_SHORT).show();
    }
}
