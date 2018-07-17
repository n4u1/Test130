package com.example.n4u1.test130.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.n4u1.test130.R;

public class AddCategoryDialog extends Dialog {

    private String mTitle;
    private String mContent;
    private Button mLeftButton;
    private Button mRightButton;
    private TextView mTitleView;
    private TextView mContentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.1f;
        getWindow().setAttributes(lpWindow);



        setContentView(R.layout.activity_add_category_dialog);


        mTitleView = (TextView) findViewById(R.id.txt_title);
        mContentView = (TextView) findViewById(R.id.txt_content);
        mLeftButton = (Button) findViewById(R.id.btn_left);
        mRightButton = (Button) findViewById(R.id.btn_right);

        // 제목과 내용을 생성자에서 셋팅한다.
        mTitleView.setText(mTitle);
        mContentView.setText(mContent);








    }


    public AddCategoryDialog(@NonNull Context context) {
        super(context);
    }

    public AddCategoryDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AddCategoryDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public AddCategoryDialog(@NonNull Context context, String title,
                        String content, Button mLeftButton,
                        Button mRightButton) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.mLeftButton = mLeftButton;
        this.mRightButton = mRightButton;
    }

}
