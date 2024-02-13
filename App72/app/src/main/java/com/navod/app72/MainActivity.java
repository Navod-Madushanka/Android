package com.navod.app72;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickDemo();
//        onLongClickDemo();
//        onFocusChangeListenerDemo();
        onTouchDemo();
    }
    private void onTouchDemo(){
        Button button = findViewById(R.id.button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                changeTextView("Touch");
                motionEvent.getX();
                motionEvent.getPointerCount();
                motionEvent.getPressure();
                return false;
            }
        });
    }
    private void onFocusChangeListenerDemo(){
        EditText editText = findViewById(R.id.editTextText);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changeTextView(String.valueOf(hasFocus));//If focused, the boolean is true.
            }
        });
    }
    private void onLongClickDemo(){
        Button button = findViewById(R.id.button);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeTextView("Android");
                return true;
                // other events will call if returned boolean is false.
                // other events will not call if return boolean is true.
            }
        });
    }
    private void onClickDemo(){
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextView("click");
            }
        });
    }
    private void changeTextView(String text){
        TextView textView = findViewById(R.id.textView);
        textView.setText(text);
    }
}