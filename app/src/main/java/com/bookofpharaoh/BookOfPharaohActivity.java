package com.bookofpharaoh;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class BookOfPharaohActivity extends Activity implements View.OnTouchListener {

    private MatrixView matrixView;

    private ScoreView currentScore;

    private ScoreView bestScore;


    private SwipeListener swipeListener;


    private Animation slideUpAnimation;

    private TextView textViewLucky;

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
