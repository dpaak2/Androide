package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context context= Main.this;
        findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "계정 만들기", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,CreateAccount.class));
            }
        });
        findViewById(R.id.goLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "로그인 ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,Login.class));
            }
        });
    }
}
