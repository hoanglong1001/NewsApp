package com.example.news.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.Random;

public class Utils {
    public static ColorDrawable[] colorDrawables = {
        new ColorDrawable(Color.parseColor("#ffeead")),
        new ColorDrawable(Color.parseColor("#93cfb3")),
        new ColorDrawable(Color.parseColor("#fd7a7a")),
        new ColorDrawable(Color.parseColor("#1ba798")),
        new ColorDrawable(Color.parseColor("#6aa9ae")),
        new ColorDrawable(Color.parseColor("#ffbf27")),
        new ColorDrawable(Color.parseColor("#d93947")),
    };

    public static ColorDrawable getColor() {
        int random = new Random().nextInt(colorDrawables.length);
        return colorDrawables[random];
    }
}
