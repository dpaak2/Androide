package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        final Context context=MemberDetail.this;
        findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,MemberUpdate.class));
            }
        });
        findViewById(R.id.cancelUpdat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "수정 취소", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,MemberList.class));
            }
        });
    }
}
