package cn.mtcle.mread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.mtcle.mread.bean.ArticleType;
import cn.mtcle.mread.common.BaseFirstLevActivity;
import cn.mtcle.mread.util.Cmd;
import cn.mtcle.mread.util.ViewUtil;

/**
 * Created by mtcle on 2016/3/18.
 */
public class MainActivity extends BaseFirstLevActivity implements View.OnClickListener {

    private LinearLayout imageViewOne = null;// 第一行的图片区域
    private LinearLayout imageViewTwo = null;// 第二行的图片区域
    private LinearLayout nameViewTwo = null;// 第二行的文字区域
    private LinearLayout nameViewOne = null;// 第一行的文字区域

    private List<ArticleType> typeList;
    private List<ArticleType> moreTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.initView();
        setChildContentView(R.layout.activity_main);
        initControl();
        getType();
    }

    private void initControl() {
        nameViewOne = (LinearLayout) this.findViewById(R.id.main_name_01);
        nameViewTwo = (LinearLayout) this.findViewById(R.id.main_name_02);
        imageViewOne = (LinearLayout) this.findViewById(R.id.main_imageview_01);
        imageViewTwo = (LinearLayout) this.findViewById(R.id.main_imageview_02);
    }

    /**
     * 开始装在底部按钮
     */
    private void initButtomBtn() {
        setview();
        for (int i = 0; i < typeList.size(); i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            TextView textView = new TextView(MainActivity.this);

            LinearLayout layout = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            layout.setLayoutParams(params);
            layout.setGravity(Gravity.CENTER);

            imageView.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            textView.setLayoutParams(params);
            textView.setText(typeList.get(i).getType_name());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(12);
            textView.setTextColor(getResources().getColor(
                    R.color.buttom_text_color));
            imageView.setImageResource(R.mipmap.canyin);
            // imageView.setBackgroundDrawable(getResources().getDrawable(
            // R.drawable.main_button_selector));
            imageView.setPadding(5, 5, 5, 5);
            imageView.setOnClickListener(this);
            imageView.setTag(i);
            ImageLoader.getInstance().displayImage(typeList.get(i).getType_icon_img().getFileUrl(mContext), imageView);
            layout.addView(imageView);

            if (typeList.size() <= 3) { // 如果一行显示满
                imageViewOne.addView(layout);
                nameViewOne.addView(textView);
            } else if (typeList.size() > 3 && typeList.size() <= 7) {// 如果两行显示满
                if (i <= 2) {
                    imageViewOne.addView(layout);
                    nameViewOne.addView(textView);
                } else {
                    imageViewTwo.addView(layout);
                    nameViewTwo.addView(textView);
                }

            } else { // 如果大于八个显示更多
                if (i <= 2) { // 填充第一行
                    imageViewOne.addView(layout);
                    nameViewOne.addView(textView);
                } else if (i > 2 && i <= 5) { // 填充第二行前三个
                    imageViewTwo.addView(layout);
                    nameViewTwo.addView(textView);
                } else // 点击更多按钮
                {
                    imageView.setImageResource(R.mipmap.bg_gengduo);
                    imageViewTwo.addView(layout);

                    nameViewTwo.addView(textView);
                    textView.setText("更多");
                    imageView.setTag(7);// 7用来标识是更多按钮按下的效果
                    setdata();// 将剩余的几项传入下个界面
                    return;

                }

            }
        }
    }

    /**
     * 截取剩余选项 传入下个更多界面
     */
    private void setdata() {
        typeList = new ArrayList<ArticleType>();
        for (int i = 6; i < typeList.size(); i++) {
            moreTypeList.add(typeList.get(i));
        }

    }

    private void setview() {

        LinearLayout layout = new LinearLayout(MainActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layout.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(MainActivity.this);
        TextView textView = new TextView(MainActivity.this);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(12);
        textView.setTextColor(getResources()
                .getColor(R.color.buttom_text_color));

        textView.setText("固定分类");
        imageView.setImageResource(R.mipmap.bg_saomiao);
        imageView.setTag(-2);

        layout.addView(imageView);
        imageView.setOnClickListener(this);
        imageViewOne.addView(layout);
        imageView.setPadding(5, 5, 5, 5);
        // imageView.setBackgroundDrawable(getResources().getDrawable(
        // R.drawable.main_button_selector));
        nameViewOne.addView(textView);
    }

    @Override
    public void onClick(View view) {

    }

   private void getType(){
       BmobQuery query = new BmobQuery("article_type");
//查询playerName叫“比目”的数据
      // query.addWhereEqualTo("playerName", "比目");
//返回50条数据，如果不加上这条语句，默认返回10条数据
       query.setLimit(50);
//执行查询方法
       query.findObjects(this, new FindCallback() {
           @Override
           public void onSuccess(org.json.JSONArray jsonArray) {
               ViewUtil.showToast(mContext, "查询成功：共" + jsonArray.length() + "条数据。");
               typeList= JSONObject.parseArray(jsonArray.toString(),ArticleType.class);
               if(typeList!=null && typeList.size()>0){
                   initButtomBtn();
               }else{
                   ViewUtil.showToast(mContext,"暂时没有分类");
               }
           }

           @Override
           public void onFailure(int i, String s) {
               ViewUtil.showToast(mContext, "查询失败：" + s);
           }
       });

   }
}
