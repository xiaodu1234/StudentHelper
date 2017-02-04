package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import bean.SalfeBean;
import bean.StockBean;
import bean.addBookBean;

/**
 * Created by duchaoqiang on 2016/12/29.
 * 操作
 */
public class DButils {

    private SQLiteDatabase db;
    private Context context;
    private DButils(Context context){
        DBHelper dbHelper = new DBHelper(context, "bookManger.db", null, 2);
        db = dbHelper.getWritableDatabase();
        this.context=context;
    }
    public static  DButils dButils;
    public static DButils getInstance(Context context){
        if (dButils==null){
            dButils=new DButils(context);
        }
        return dButils;
    }
    public void addUser(String username,String password){
        if (queryUser(username)){
            //Toast.makeText(this.context,"用戶名已存在",Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        db.insert("user",null,values);
        Toast.makeText(this.context,"用戶創建成功",Toast.LENGTH_SHORT).show();
        values.clear();
    }

    /**
     * 判断账号密码是否存在
     * @param username
     * @param password
     * @return
     */
    public boolean queryUser(String username,String password){

        Cursor cursor=db.rawQuery("select * from user where username=? and password=?",new String[]{username,password});
        if (cursor.getCount()!=0){
            return  true;
        }
        return false;
    }
    private boolean queryUser(String username){
        Cursor cursor=db.rawQuery("select * from user where username=? ",new String[]{username});
        if (cursor.getCount()!=0){
            return true;

        }
        return false;
    }

    /**
     * 增加售书表
     */
    public void addSalfeTable(ArrayList<SalfeBean> info){
        ContentValues values=new ContentValues();
        for (int i=0;i<info.size();i++){
            SalfeBean bean=info.get(i);
            values.put("barcode",bean.getBarcode());
            values.put("num",bean.getNum());
            values.put("time",bean.getDate());
            db.insert("salfebook",null,values);
            values.clear();
        }

    }

    /**
     * 增加进书表
     */
    public void addBookTable(addBookBean bean){
        ContentValues values=new ContentValues();
        //增加进书表
        values.put("barcode", bean.getBarcode());
        values.put("purchase", bean.getPrice());
        values.put("num", bean.getNum());
        values.put("time", bean.getDate());
        db.insert("addbook", null, values);
        values.clear();

    }

    /**
     * 增加库存表
     * @param bean
     */
    public void addStockTable(StockBean bean){
        ContentValues values=new ContentValues();
        //增加库存
        values.clear();
        values.put("barcode", bean.getBarcode());
        values.put("book_name", bean.getBookName());
        values.put("stock_num", bean.getStock_num());
        values.put("author", bean.getAuthor());
        values.put("Press", bean.getPress());
        values.put("price", bean.getPrice());
        db.insert("stock", null, values);
    }
    public void add() {
        ContentValues values = new ContentValues();
        //增加进书表
        values.put("barcode", 3333);
        values.put("purchase", 22.1);
        values.put("num", 100);
        values.put("time", "2016年");
        db.insert("addbook", null, values);
        //增加库存
        values.clear();
        values.put("barcode", 3333);
        values.put("book_name", "Android 第一行代码");
        values.put("stock_num", 100);
        values.put("author", "郭林");
        values.put("Press", "新华日报");
        values.put("price", 20);
        db.insert("stock", null, values);
        //增加售书表
        values.clear();
        values.put("barcode", 1111);
        values.put("num", 2);
        values.put("time", "2016年");
        db.insert("salfebook",null,values);
        values.clear();

        Toast.makeText(context, "增加数据成功", Toast.LENGTH_LONG).show();
    }

    /**
     * 查询库存中所有书
     */
    private void queryStock() {
        Cursor cursor = db.query("addbook", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                int barcode = cursor.getInt(cursor.getColumnIndex("barcode"));
                String book_name = cursor.getString(cursor.getColumnIndex("book_name"));
                int stock_num = cursor.getInt(cursor.getColumnIndex("stock_num"));
                String author=cursor.getString(cursor.getColumnIndex("author"));
                String Press=cursor.getString(cursor.getColumnIndex("Press"));
                int price=cursor.getInt(cursor.getColumnIndex("price"));
            }while (cursor.moveToNext());
        }
    }

    /**
     * 综合查询库存
     * @param barcode
     * @param booName
     * @return
     */
    public ArrayList<StockBean> queryStock(String barcode,String booName){
        ArrayList<StockBean> allinfo=new ArrayList<>();
        String sql="";
        int witch=-1;
        if (TextUtils.isEmpty(barcode) && !TextUtils.isEmpty(booName)){
           sql="select * from stock where book_name=?";
            witch=0;
        }else if (!TextUtils.isEmpty(barcode) && TextUtils.isEmpty(booName)){
            sql="select * from stock where barcode=?";
            witch=1;
        }else if (!TextUtils.isEmpty(barcode) && !TextUtils.isEmpty(booName)){
            sql="select * from stock where barcode=? and book_name=?";
            witch=2;
        }else{ //查询所有库存
            sql="select * from stock";
            witch=3;
        }
        String data[] = new String[0];
        switch (witch){
            case 0:
                data=new String[]{booName};
                break;
            case 1:
                data=new String[]{barcode};
                break;
            case 2:
                data=new String[]{barcode,booName};
                break;
            case 3:
                data=null;

        }
        Cursor cursor=db.rawQuery(sql,data);
        if (cursor.moveToFirst()){
            do {
                 StockBean  bean=new StockBean();
                String book_name = cursor.getString(cursor.getColumnIndex("book_name"));
                int stock_num = cursor.getInt(cursor.getColumnIndex("stock_num"));
                String author=cursor.getString(cursor.getColumnIndex("author"));
                String Press=cursor.getString(cursor.getColumnIndex("Press"));
                int price=cursor.getInt(cursor.getColumnIndex("price"));
                if (TextUtils.isEmpty(barcode)){
                    bean.setBarcode(cursor.getInt(cursor.getColumnIndex("barcode")));
                }
                bean.setAuthor(author);
                bean.setBookName(book_name);
                bean.setStock_num(stock_num);
                bean.setPress(Press);
                bean.setPrice(price);
                allinfo.add(bean);
            }while (cursor.moveToNext());
        }
        return allinfo;
    }

    /**
     * 综合查询进书表
     * @param barcode
     * @param booName
     * @param date
     * @return
     */
    public ArrayList<addBookBean> queryAddBook(String barcode,String booName,String date) {
        ArrayList<addBookBean> allinfo = new ArrayList<>();
        boolean isBarcode = false;
        boolean isBookName = false;
        boolean isDate = false;
        String sql = "select * from addbook,stock where addbook.barcode=stock.barcode ";
        int witch = -1;
        if (!TextUtils.isEmpty(barcode)) {
            sql += "and addbook.barcode=? ";
            isBarcode = true;
        }
        if (!TextUtils.isEmpty(booName)) {
            sql += "and book_name=? ";
            isBookName = true;
        }
        if (!TextUtils.isEmpty(date)) {
            sql += "and time=? ";
            isDate = true;
        }
        ArrayList<String> data = new ArrayList<>();
        if (isBarcode) data.add(barcode);
        if (isBookName) data.add(booName);
        if (isDate) data.add(date);
        String s[] = (String[]) data.toArray(new String[0]);
        Cursor cursor = db.rawQuery(sql, s);
        if (cursor.moveToFirst()) {
            do {
                String book_name = cursor.getString(cursor.getColumnIndex("book_name"));
                int num = cursor.getInt(cursor.getColumnIndex("num"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                int price = cursor.getInt(cursor.getColumnIndex("purchase"));
                String author=cursor.getString(cursor.getColumnIndex("author"));
                addBookBean bean = new addBookBean();
                bean.setPrice(price);
                bean.setBookName(book_name);
                bean.setNum(num);
                bean.setDate(time);
                bean.setAuthor(author);
                allinfo.add(bean);
            } while (cursor.moveToNext());

        }
        return allinfo;
    }

    /**
     * 售书查询
     * @return
     */
    public ArrayList<SalfeBean>querySalfeBook(String barcode,String bookName,String date){

        ArrayList<SalfeBean> allinfo = new ArrayList<>();
        boolean isBarcode = false;
        boolean isBookName = false;
        boolean isDate = false;
        String sql = "select * from salfebook,stock where salfebook.barcode=stock.barcode ";
        int witch = -1;
        if (!TextUtils.isEmpty(barcode)) {
            sql += "and stock.barcode=? ";
            isBarcode = true;
        }
        if (!TextUtils.isEmpty(bookName)) {
            sql += "and book_name=? ";
            isBookName = true;
        }
        if (!TextUtils.isEmpty(date)) {
            sql += "and time=? ";
            isDate = true;
        }
        ArrayList<String> data = new ArrayList<>();
        if (isBarcode) data.add(barcode);
        if (isBookName) data.add(bookName);
        if (isDate) data.add(date);
        String s[] = (String[]) data.toArray(new String[0]);
        Cursor cursor = db.rawQuery(sql, s);
        if (cursor.moveToFirst()) {
            do {
                String book_name = cursor.getString(cursor.getColumnIndex("book_name"));
                int num = cursor.getInt(cursor.getColumnIndex("num"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                String author=cursor.getString(cursor.getColumnIndex("author"));
                SalfeBean bean = new SalfeBean();
                bean.setPrice(price);
                bean.setBooName(book_name);
                bean.setNum(num);
                bean.setDate(time);
                bean.setAuthor(author);
                allinfo.add(bean);
            } while (cursor.moveToNext());

        }
        return allinfo;
    }

    /**
     * 查询库存书
     * @param barcode
     * @return
     */
    public StockBean qreryStock(String barcode ){
        StockBean bean=null;
        Cursor cursor=db.rawQuery("select * from stock where barcode=?",new String[]{barcode});
        if (cursor.getCount()!=0 && cursor.moveToFirst()){
            bean=new StockBean();
            String book_name = cursor.getString(cursor.getColumnIndex("book_name"));
            int stock_num = cursor.getInt(cursor.getColumnIndex("stock_num"));
            String author=cursor.getString(cursor.getColumnIndex("author"));
            String Press=cursor.getString(cursor.getColumnIndex("Press"));
            int price=cursor.getInt(cursor.getColumnIndex("price"));
            bean.setAuthor(author);
            bean.setBookName(book_name);
            bean.setBarcode(Integer.parseInt(barcode));
            bean.setStock_num(stock_num);
            bean.setPress(Press);
            bean.setPrice(price);
        }
        return bean;
    }

    /**
     * 修改库存量
     * @param num
     */
    public void updateStock(int num){
        ContentValues values = new ContentValues();
        values.put("stock_num",num);
        db.update("stock",values,null,null);
    }



    /**
     * 查询进书表
     */
    private void queryAddBook(){
        Cursor cursor = db.query("stock", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                int barcode = cursor.getInt(cursor.getColumnIndex("barcode"));
                float purchase = cursor.getInt(cursor.getColumnIndex("purchase"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int num=cursor.getInt(cursor.getColumnIndex("num"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                Log.i("Main", "di :" + id + barcode + " " + purchase + "");
            }while (cursor.moveToNext());
        }
    }

    /**
     * 清理库存
     */
    public void clearStock(){
      db.execSQL("delete from stock where 1=1");
    }
    public void clearAddTable(){
        db.execSQL("delete from addbook where 1=1");
    }
    public void clearSalfeTable(){
        db.execSQL("delete from salfebook where 1=1");
    }
    
    
}
