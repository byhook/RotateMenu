package cn.z.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sample.byhook.rotatemenu.R;

/**
 * 作者 : byhook
 * 时间 : 15-11-18.
 * 邮箱 : byhook@163.com
 * 功能 :
 */
public class RotateTextAdapter {

    private String[] titles;

    private Context ctx;

    private LayoutInflater inflater;

    public RotateTextAdapter(Context ctx, String[] titles){
        this.ctx = ctx;
        this.titles = titles;
        this.inflater = LayoutInflater.from(ctx);
    }

    /**
     * 获取文本数量
     * @return
     */
    public int getCount(){
        return titles.length;
    }

    /**
     * 获取滚动文本
     * @param index
     * @return
     */
    public View getView(int index){
        TextView bt = (TextView) inflater.inflate(R.layout.rotate_text_item,null);
        bt.setText(titles[index]);
        return bt;
    }

    /**
     * 获取旋转游标
     * @return
     */
    public View getCursor(){

        return new Button(ctx);
    }
}
