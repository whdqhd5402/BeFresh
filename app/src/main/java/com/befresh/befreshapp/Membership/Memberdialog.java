package com.befresh.befreshapp.Membership;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.befresh.befreshapp.R;


/**
 * Created by idongsu on 2017. 5. 17..
 */

public class Memberdialog extends Dialog
{


    Context context;
    ImageView dialog;
    private OnDismissListener onDismissListener;

    public Memberdialog(Context context)
    {
        super(context);
    }
    // In the constructor, you set the callback


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.2f;
        getWindow().setAttributes(lpWindow);
        dialog = (ImageView)findViewById(R.id.dialog);
        setContentView(R.layout.completedialog);

    }
}
