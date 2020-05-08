package com.bookofpharaoh;




import android.content.Context;
import android.content.SharedPreferences;

public class BookOfPharaohPreferences {

    enum Key {
        BEST_SCORE
    }

    private static SharedPreferences prefs;

    public static void initPrefs(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences("com.bookofpharaoh", Context.MODE_PRIVATE);
        }
    }

    public static void saveBestScore(int score) {
        prefs.edit().putInt(Key.BEST_SCORE.name(), score).commit();
    }

    public static int getBestScore() {
        return prefs.getInt(Key.BEST_SCORE.name(), 0);
    }
}
