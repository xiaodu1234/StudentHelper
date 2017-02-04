package Utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by duchaoqiang on 2017/1/3.
 * 将输入流转为字符串
 */
public class StreamPool {
    public static final String UTF_8="UTF-8";
    public static final String GB="gb2312";
    public static String inToStringByByte(InputStream in,String encode) throws Exception {
        ByteArrayOutputStream outStr = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        StringBuilder content = new StringBuilder();
        while ((len = in.read(buffer)) != -1) {
            content.append(new String(buffer, 0, len, encode==null ? UTF_8:encode));
        }
        outStr.close();
        return content.toString();
    }
}
