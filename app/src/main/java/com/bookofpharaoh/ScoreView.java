package com.bookofpharaoh;



import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;


public class ScoreView extends FrameLayout {


    private Animation animBubbleUp;
    private TextView txtAnim, txtLbl, txtScore;
    private int scr = 0;
    public ScoreView(Context context) {
        this(context, null, 0);
    }

    public ScoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public int getScr() {
        return scr;
    }

    public void resetScore() {
        scr = 0;
        txtScore.setText(Integer.toString(scr));
    }

    public void setScr(int scr) {
        this.scr = scr;
        txtScore.setText(Integer.toString(scr));
    }

    public ScoreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        animBubbleUp = AnimationUtils.loadAnimation(context, R.anim.bubble_up);
        animBubbleUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtAnim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.scr_view, this);
        txtAnim = v.findViewById(R.id.scr_view_txtview_animation);
        txtLbl = v.findViewById(R.id.scr_view_txtview_label);
        txtScore = v.findViewById(R.id.scr_view_txtview_score);
        txtScore.setText(Integer.toString(scr));
        attrParse(context, attrs);
    }
    public void scrAdd(int score) {
        scr += score;
        txtAnim.setVisibility(View.VISIBLE);
        txtAnim.setText("+" + score);
        txtAnim.startAnimation(animBubbleUp);
        txtScore.setText(Integer.toString(scr));
    }
    private void attrParse(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ScoreView, 0, 0);
        String label;
        try {
            label = a.getString(R.styleable.ScoreView_label_text);
            txtLbl.setText(label);
        } finally {
            a.recycle();
        }
    }




}
