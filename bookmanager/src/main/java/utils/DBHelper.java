package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by duchaoqiang on 2016/12/28.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final String addBookTable="addbook";
    private final String salfeBookTable="salfebook";
    private final String stockTable="stock";
    private final String userTable="user";
    private Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
      String sql="create table "+addBookTable+"(id Integer  primary key autoincrement  ,barcode int,purchase int,num int,time char(12))";
      String sql1="create table "+salfeBookTable+"(id Integer  primary key autoincrement ,barcode int,num int,time char(12))";
      String sql2="create table "+stockTable+"(barcode int primary key,book_name char(20),stock_num int , author char(10),Press char(20),price int, foreign key(barcode) references "+addBookTable+"(barcode))";
      String sql3="create table "+userTable+"(id Integer  primary key autoincrement,username char(10),password char(10))";
      db.execSQL(sql);
      db.execSQL(sql1);
      db.execSQL(sql2);
        db.execSQL(sql3);
        Toast.makeText(context,"创建成果",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("drop table if exists "+addBookTable);
     db.execSQL("drop table if exists "+salfeBookTable);
     db.execSQL("drop table if exists "+stockTable);
        db.execSQL("drop table if exists "+userTable);
    }


}
