package com.bookofpharaoh;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.
        ImageView;
import android.widget.TableLayout;


public class MatrixView extends TableLayout implements SwipeInterface {

    private static final int N = MatrixBookOfPharaoh.N;



    private MatrixBookOfPharaoh matrixBookOfPharaoh;

    private
    ImageView[][] tiles;


    private Animation appearingAnimation;

    private SwipeListener swipeListener;

    private BookOfPharaohMoveInterface bookOfPharaohMoveInterface;

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.matrix, this);

        tiles = new ImageView[N][N];
        tiles[0][0] = v.findViewById(R.id.
                ImageView00);
        tiles[0][1] = v.findViewById(R.id.
                ImageView01);
        tiles[0][2] = v.findViewById(R.id.
                ImageView02);
        tiles[0][3] = v.findViewById(R.id.
                ImageView03);

        tiles[1][0] = v.findViewById(R.id.
                ImageView10);
        tiles[1][1] = v.findViewById(R.id.
                ImageView11);
        tiles[1][2] = v.findViewById(R.id.
                ImageView12);
        tiles[1][3] = v.findViewById(R.id.
                ImageView13);

        tiles[2][0] = v.findViewById(R.id.
                ImageView20);
        tiles[2][1] = v.findViewById(R.id.
                ImageView21);
        tiles[2][2] = v.findViewById(R.id.
                ImageView22);
        tiles[2][3] = v.findViewById(R.id.
                ImageView23);

        tiles[3][0] = v.findViewById(R.id.
                ImageView30);
        tiles[3][1] = v.findViewById(R.id.
                ImageView31);
        tiles[3][2] = v.findViewById(R.id.
                ImageView32);
        tiles[3][3] = v.findViewById(R.id.
                ImageView33);



        appearingAnimation = AnimationUtils.loadAnimation(context, R.anim.appearing);

        matrixBookOfPharaoh = new MatrixBookOfPharaoh();
        displayMatrix(matrixBookOfPharaoh, Direction.DOWN);

        swipeListener = new SwipeListener(context, this);
    }

    public void reset() {
        matrixBookOfPharaoh = new MatrixBookOfPharaoh();
        displayMatrix(matrixBookOfPharaoh, Direction.DOWN);

    }

    public void setBookOfPharaohMoveInterface(BookOfPharaohMoveInterface listener) {
        bookOfPharaohMoveInterface = listener;
    }


    private void displayMatrix(MatrixBookOfPharaoh m, Direction dir) {
        final int n = MatrixBookOfPharaoh.N;
        int number;
        for (int r = 0; r < n; ++r) {
            for (int c = 0; c < n; ++c) {
                number = m.getSpot(r, c);
                if (number == MatrixBookOfPharaoh.FREE) {
                    tiles[r][c].setContentDescription("");
                } else {
                    tiles[r][c].setContentDescription(Integer.toString(number));
                }
                tiles[r][c].setImageDrawable(getResources().getDrawable(getDrawableId(number)));

                if (matrixBookOfPharaoh.isMergeSpot(r, c)) {
                    tiles[r][c].startAnimation(appearingAnimation);
                }

            }
        }
        invalidate();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        swipeListener.getGestureDetector().onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onUp() {
        //Log.e("2048", "swipe up");
        handleSwipe(Direction.UP);
    }

    @Override
    public void onRight() {
        //Log.e("2048", "swipe right");
        handleSwipe(Direction.RIGHT);
    }

    @Override
    public void onLeft() {
        //Log.e("2048", "swipe left");
        handleSwipe(Direction.LEFT);
    }

    @Override
    public void onDown() {
        //Log.e("2048", "swipe down");
        handleSwipe(Direction.DOWN);
    }

    private void handleSwipe(Direction dir) {
        int score = matrixBookOfPharaoh.swipe(dir);
        boolean gameOver = matrixBookOfPharaoh.isStuck();
        boolean newSquare = matrixBookOfPharaoh.newGenInit();
        displayMatrix(matrixBookOfPharaoh, dir);
        bookOfPharaohMoveInterface.onMove(score, gameOver, newSquare);
    }



    private int getDrawableId(int n) {
        switch (n) {
            case 2:
                return R.drawable.points_2;

            case 4:
                return R.drawable.points_4;

            case 8:
                return R.drawable.points_8;

            case 16:
                return R.drawable.points_16;

            case 32:
                return R.drawable.points_32;

            case 64:
                return R.drawable.points_64;

            case 128:
                return R.drawable.points_128;

            case 256:
                return R.drawable.points_256;

            case 512:
                return R.drawable.points_512;

            case 1024:
                return R.drawable.points_1024;

            case 2048:
                return R.drawable.points_2048;
        }
        return R.drawable.n_0;
    }
}
