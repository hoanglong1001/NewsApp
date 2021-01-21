package com.example.news.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static String getPublishTime(String oldTime) {
        String newTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = sdf.parse(oldTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyy");
            newTime = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTime;
    }

    public static String getTimeAgo(String oldTime) {
        String newTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = sdf.parse(oldTime);
            PrettyTime prettyTime = new PrettyTime();
            newTime = prettyTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTime;
    }
}
