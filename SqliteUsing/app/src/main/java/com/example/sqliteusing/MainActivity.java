package com.example.sqliteusing;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity implements View.OnClickListener {

    private SimpleCursorAdapter adapter;
    private EditText etName,etSex;
    private Button btnAdd;
    private Db db;
    private SQLiteDatabase dbRead,dbWrite;
    //长按操作弹出来删除会话框
    private AdapterView.OnItemLongClickListener listViewItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            new AlertDialog.Builder(MainActivity.this).setPositiveButton("确定",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Cursor c = adapter.getCursor();
                    c.moveToPosition(position);

                    int itemId = c.getInt(c.getColumnIndex("_id"));
                    dbWrite.delete("user","_id=?",new String[]{itemId+""});
                    refreshListView();
                }
            }).setNegativeButton("取消",null).setTitle("提醒").setMessage("你确定要删除该项吗？").show();

            return true;//true和false的区别是是否支持长按操作
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etSex = findViewById(R.id.etSex);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        db = new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();

        //Cursor c = dbRead.query("user",null,null,null,null,null,null);

        adapter = new SimpleCursorAdapter(this,R.layout.user_list_cell,null,new String[]{"name","sex"},new int[]{R.id.tvName,R.id.tvSex});
        setListAdapter(adapter);

        refreshListView();

        
        getListView().setOnItemLongClickListener(listViewItemLongClickListener);

        /*这些数据都是显示在控制台中的数据，下面要写的是展示在手机列表中
        Db db = new Db(this);//这些写的是往数据库添加数据
        SQLiteDatabase dbWrite = db.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name","小张");
        cv.put("sex","男");
        dbWrite.insert("user",null,cv);

        cv = new ContentValues();
        cv.put("name","小李");
        cv.put("sex","女");
        dbWrite.insert("user",null,cv);

        dbWrite.close();
        //这里是在控制台输出上面写入的数据
        SQLiteDatabase dbRead = db.getReadableDatabase();
        Cursor c = dbRead.query("user",null,null,null,null,null,null);
        while (c.moveToNext()){
            String name = c.getString(c.getColumnIndex("name"));
            String sex = c.getString(c.getColumnIndex("sex"));
            System.out.println(String.format("name=%s,sex=%s",name,sex));
        }*/
    }
    private void refreshListView(){
        Cursor c = dbRead.query("user",null,null,null,null,null,null);
        adapter.changeCursor(c);
    }

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();
        cv.put("name",etName.getText().toString());
        cv.put("sex",etSex.getText().toString());
        dbWrite.insert("user",null,cv);
        refreshListView();
    }
}
