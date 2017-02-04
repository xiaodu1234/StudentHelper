package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by duchaoqiang on 2016/12/29.
 */
public class SharedPrefUtils {
    public static final String FILENAME="filename";
    public static final String USERNAME="userName";
    public static final String PASSWORD="password";
    public static void savaUserName(Context context,String username){
        getSharedPrefrence(context).edit().putString(USERNAME,username).commit();
    }
    public static void savePassword(Context context,String password){
        getSharedPrefrence(context).edit().putString(PASSWORD,password).commit();
    }
    public static String getUsername(Context context,String defaultValue){
      return   getSharedPrefrence(context).getString(USERNAME,defaultValue);
    }
    public static String getPassword(Context context,String defaultValue){
        return   getSharedPrefrence(context).getString(PASSWORD,defaultValue);
    }


    private static SharedPreferences getSharedPrefrence(Context context){
        return  context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
    }

}
