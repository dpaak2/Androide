package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        final Context context = Index.this;
        //context
    /*    findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "로그인 클릭됨 !!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,Login.class));
            }
        });*/

        Handler handler = new Handler();
        SqLiteHelper helper = new SqLiteHelper(context);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, Main.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //디비 연결
    public static class SqLiteHelper extends SQLiteOpenHelper {
        public SqLiteHelper(Context context) {
            super(context, "hanbitdb", null, 1);
            //버전은 1부터 시작
            //디비 바꿔서 만들어서 쓰는거라고 하는것
            this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //내장 디비 이기때문에 sql query만 던지면 끝!
            String sql = String.format(" CREATE TABLE IF NOT EXISTS " +
                    " %s( " +
                    "         %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "         %s TEXT, %s TEXT, %s TEXT,%s TEXT, " +
                    "         %s TEXT, %s TEXT " +
                    " );", Cons.MEMBER_TBL, Cons.SEQ, Cons.PASS, Cons.NAME, Cons.EMAIL, Cons.PHONE, Cons.PROFILE, Cons.ADDR);
            db.execSQL(sql);

            for (int i = 1; i < 6; i++) {
                db.execSQL(String.format(" INSERT INTO %s(%s,%s,%s,%s,%s,%s)" +
                                "VALUES ('%s','%s','%s','%s','%s','%s');"
                        , Cons.MEMBER_TBL, Cons.PASS, Cons.NAME, Cons.EMAIL, Cons.PHONE, Cons.ADDR, Cons.PROFILE,
                        "1", "홍길동" + i, "hong" + i + "@test.com", "010-1234-567" + i, "서울" + 1, "default_img"));
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public static abstract class QueryFactory {
        Context context;

        public QueryFactory(Context context) {
            this.context = context;
        }
        public abstract SQLiteDatabase getDatabase();
    }
}
