package com.hanbit.kakaotalk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        final Context context=MemberList.this;
        ListView listView= (ListView) findViewById(R.id.listView);
        final FriendList firendList = new FriendList(context);
        ArrayList<Member> friends= (ArrayList<Member>) new Service.IList() {
            @Override
            public ArrayList<?> execute(Object o) {
                return firendList.execute();
            }
        }.execute(null);
        listView.setAdapter(new MemberAdapter(context,friends));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        findViewById(R.id.addMem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "친구 추가 ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,MemberAdd.class));
            }
        });
        findViewById(R.id.detailBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "친구 상세", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,MemberDetail.class));
            }
        });
        findViewById(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "로그 아웃", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context,Login.class));
            }
        });
    }
    private abstract class ListQuery extends Index.QueryFactory{
        SQLiteOpenHelper helper;
        public ListQuery(Context context) {
            super(context);
            //SqLiteHelper내가 만든것,inner class
            helper= new Index.SqLiteHelper(context);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class FriendList extends ListQuery{
        //추상을 또 만들면 안됨 ,FINAL이다
        public FriendList(Context context) {
            super(context);
        }
        public ArrayList<Member> execute(){
            ArrayList<Member> list = new ArrayList<>();
            String sql=String.format(" SELECT * FROM %s ;" ,Cons.MEMBER_TBL);
            Cursor cursor= super.getDatabase().rawQuery(sql,null);
            Member member= null;
            if(cursor!=null){
                if(cursor.moveToNext()){
                    do{
                        member=new Member();
                        member.setSeq(String.valueOf(cursor.getColumnIndex(Cons.SEQ)));
                        member.setName(String.valueOf(cursor.getColumnIndex(Cons.NAME)));
                        member.setPass(String.valueOf(cursor.getColumnIndex(Cons.PASS)));
                        member.setEmail(String.valueOf(cursor.getColumnIndex(Cons.EMAIL)));
                        member.setProfile(String.valueOf(cursor.getColumnIndex(Cons.PROFILE)));
                        member.setAddr(String.valueOf(cursor.getColumnIndex(Cons.ADDR)));
                        member.setPhone(String.valueOf(cursor.getColumnIndex(Cons.PHONE)));
                        list.add(member);
                    }while (cursor.moveToNext());
                }
            }else{}
            return list;
        }
    }
    class MemberAdapter extends BaseAdapter{
        ArrayList<Member> list;
        //
        LayoutInflater inflater;
        //외부에서 , list를 받고
        public MemberAdapter(Context context,ArrayList<Member>list) {
            //바람주입
            this.list=list;
            this.inflater=LayoutInflater.from(context);

        }
        private int[] photos={
                R.drawable.icecream,
                R.drawable.gingerbread,
                R.drawable.kitkat,
                R.drawable.honeycomb,
                R.drawable.jellybean,
                R.drawable.lollipop
        };

        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder viewHolder;
            if(v==null){
                //null == i'm not using default i will use whatever i made!!
                v=inflater.inflate(R.layout.member_adapter,null);
                viewHolder=new ViewHolder();
                viewHolder.imageView= (ImageView) v.findViewById(R.id.imageView);
                viewHolder.name= (TextView) v.findViewById(R.id.name);
                viewHolder.phone= (TextView) v.findViewById(R.id.phone);
            }else{
                viewHolder= (ViewHolder) v.getTag();
            }
            viewHolder.imageView.setImageResource(photos[i]);
            viewHolder.name.setText(list.get(i).getName());
            viewHolder.phone.setText(list.get(i).getPhone());
            return null;
        }
    }
    static class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView phone;


    }
}
