package cn.mtcle.mread.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import cn.mtcle.mread.R;
import cn.mtcle.mread.util.okhttp.OkHttpUtil;


/**
 * CommonActivity 
 * 描述：通用类，抽取出各个界面里都要用到的标题栏没有底部工具栏。
 * 创建日期：2013-6-18 上午7:38:28
 * 修改人：
 * 修改日期：
 * 修改备注：
 */
public class BaseFirstLevActivity extends BaseActivity implements OnTouchListener,OnGestureListener{
	//顶部返回按钮
	protected ImageButton btnBarBack;
	
	// 标题栏上的标题字符串对象
	protected TextView tvBarTitle;
	
	protected Button btnRight;
	
	// 标题栏以下部分内容布局类对象
	protected LinearLayout viewContent = null;

	protected static int page = 1;
	protected int pageCount = 0;// 页面总数
	protected GestureDetector mGestureDetector;
	private DisplayMetrics dism;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置界面布局文件
		setContentView(R.layout.common_firstlev);
		dism = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dism);
		
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		// 注册一个用于手势识别的类
		mGestureDetector = new GestureDetector(this);
		// 初始化 标题栏上的组件
		tvBarTitle = (TextView) findViewById(R.id.n_login_titleTextView);
		btnBarBack = (ImageButton) findViewById(R.id.info_back_title);
		btnRight=(Button)findViewById(R.id.ibtnBarRight);
		
		viewContent = (LinearLayout) findViewById(R.id.viewContent);
		
		btnBarBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackClick();
			}
		});
		btnRight.setVisibility(View.GONE);
		btnRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO
			}
		});
	}
	
	/**
	 * 初始化中间部分内容布局方法
	 * */
	public void setChildContentView(int layoutResId) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(layoutResId, null);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT, 1.0f);
		viewContent.addView(v,params);
		//设置中间布局可以长按才能触发手势
		viewContent.setLongClickable(true);
		viewContent.setOnTouchListener(this);
	}
	
	/**
	 * 方法名：onBackClick
	 * 方法描述：返回
	 * 创建日期：2012-6-29
	 * 修改人：
	 * 修改日期：
	 * 备注：单独抽出来是方便子类对其时间添加其他操作
	 */
	protected void onBackClick(){
		finish();
	}
	
	/**
	 * 方法名：onHomeClick
	 * 方法描述：返回首页
	 * 创建人：lmy
	 * 创建日期：2012-6-29
	 * 修改人：
	 * 修改日期：
	 * 备注：单独抽出来是方便子类对其时间添加其他操作
	 */
	protected void onHomeClick(){
		Intent intent = new Intent();
		//intent.setClass(BaseFirstLevActivity.this, MainTabActivity.class);//TODO
		//注意下行的FLAG设置不然在主界面可能无法一次性退出系统。（清除栈中所有Activity）
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		startActivity(intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	
	/**
	 * 方法名：addMoreData
	 * 方法描述：得到服务器端返回的Json信息。
	 * 创建人：lmy
	 * 创建日期：2012-6-14
	 * 修改人：
	 * 修改日期：
	 * 备注：
	 */
	protected void addMoreData(){
		
	}
	
	static class ViewHolder {
		TextView id;
		TextView name;
		TextView des;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		//对手指滑动的距离进行了计算，如果滑动距离大于180像素，就做切换动作，否则不做任何切换动作。
        // 从右向左滑动
		//Log.v("手势", "触发");
        if (e1.getX() - e2.getX() > (getDisplayWidth()/4) && Math.abs(e1.getY() - e2.getY()) < (getDisplayHeight()/12) && Math.abs(velocityX) > 200)
        {
        	rightToLeft();
            return true;
        }// 从左向右滑动
        else if (e1.getX() - e2.getX() < -(getDisplayWidth()/4) &&  Math.abs(e1.getY() - e2.getY()) < (getDisplayHeight()/12) && Math.abs(velocityX) > 200)
        {
        	leftToRight();
            return true;
        }
        return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// 一定要将触屏事件交给手势识别类去处理
		return mGestureDetector.onTouchEvent(event);
	}
	protected Integer getDisplayWidth(){
		return dism.widthPixels;
		
	}
	protected Integer getDisplayHeight(){
		return dism.heightPixels;
		
	}
	/**
	 * 描述：手势滑动触发数据更新子类只需要重写leftToRight和rightToLeft方法加上业务即可
	 * 创建人：clwang
	 * 创建日期：2013-8-13 
	 * 修改人：
	 * 修改日期：
	 * 修改备注：
	 */
	protected void leftToRight(){
		
	}
	protected void rightToLeft(){
		
	}
}