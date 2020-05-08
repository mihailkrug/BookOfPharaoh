package com.bookofpharaoh;



import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class BookOfPharaohDialog extends Dialog {

    private BookOfPharaohDialogClickInterface listener;

    public BookOfPharaohDialog(Context context, String msg, String leftMsg, String rightMsg) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = getLayoutInflater().inflate(R.layout.bookofpharaoh_dialog, null);
        TextView tv = v.findViewById(R.id.bokkofpharaoh_dialog_txtvw_msg);
        tv.setText(msg);
        Button left = v.findViewById(R.id.bokkofpharaoh_dialog_btn_left);
        left.setText(leftMsg);
        left.setOnClickListener(v12 -> {
            if (listener != null) {
                listener.exitBtn();
            }
        });
        Button right = v.findViewById(R.id.bokkofpharaoh_dialog_btn_right);
        right.setText(rightMsg);
        right.setOnClickListener(v1 -> {
            if (listener != null) {
                listener.newGameBtn();
            }
        });
        setCancelable(false);
        setContentView(v);
    }

    public void setOnGameDialogClickListener(BookOfPharaohDialogClickInterface listener) {
        this.listener = listener;
    }
}
