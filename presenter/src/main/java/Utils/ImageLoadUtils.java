package Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by duchaoqiang on 2017/1/6.
 */
public class ImageLoadUtils {

    /**
     *
     * @param context
     * @param imageView
     * @param imageUrl
     * @param PlaceHolderResId 加载过程中的现实图片
     * @param failsResId  加载失败显示图片
     */
    public static void setNormalImage(Context context, ImageView imageView,String imageUrl,int PlaceHolderResId,int failsResId){
        Glide.with(context).load(imageUrl).centerCrop().placeholder(PlaceHolderResId).error(failsResId).into(imageView);
    }
}
