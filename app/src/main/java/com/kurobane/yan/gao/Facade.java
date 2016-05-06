package com.kurobane.yan.gao;

import android.content.Context;

public class Facade {
    private static Facade instance = null;
    private static DbHelper dbHelper;

    public Facade getInstance(Context context) {
        if (instance == null) {
            instance = new Facade(context);
        }
        return instance;
    }

    private Facade(Context context) {
        dbHelper = new DbHelper(context);
    }
}
