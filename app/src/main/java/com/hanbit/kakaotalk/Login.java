package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Context context= Login.this;
        final EditText inputId = (EditText) findViewById(R.id.inputId);
        final EditText inputPass= (EditText) findViewById(R.id.inputPass);
        final MemberLogin login=new MemberLogin(context);
        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String id= String.valueOf(inputId.getText().toString());
                final String pass=String.valueOf(inputPass.getText().toString());
                Toast.makeText(context, "아이디: "+id+" 비밀번호: "+pass, Toast.LENGTH_SHORT).show();
                Log.d("입력된 ID: ",id);
                Log.d("입력된 PW: ",pass);

                new Service.IPredicate() {
                    @Override
                    public void execute() {
                       if(login.execute(id,pass)){
                           Toast.makeText(context, "로그인으로 가기, 아이디:"+id, Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(context,MemberList.class));
                       }else{
                           Toast.makeText(context, "로그인 실패 아이디: "+id+" 비밀번호: "+pass, Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(context,Main.class));
                       }
                    }
                }.execute();
                //startActivity(new Intent(context,MemberList.class));
            }
        });
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show();
                inputId.setText("");
                inputPass.setText("");
            }
        });
    }
    private abstract class LoginQuery extends Index.QueryFactory{
        SQLiteOpenHelper helper;
        public LoginQuery(Context context) {
            super(context);
            //SqLiteHelper내가 만든것,inner class
            helper= new Index.SqLiteHelper(context);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class MemberLogin extends LoginQuery{
        //추상을 또 만들면 안됨 ,FINAL이다
        public MemberLogin(Context context) {
            super(context);
        }
        public boolean execute(String id,String pass){
            return super
                    .getDatabase()
                    .rawQuery(String.format(" SELECT * FROM %s WHERE %s='%s' AND %s ='%s' ;" ,Cons.MEMBER_TBL,Cons.SEQ,id,Cons.PASS,pass),null)
                    .moveToNext();
        }
    }
}
