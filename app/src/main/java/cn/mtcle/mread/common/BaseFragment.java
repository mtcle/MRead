package cn.mtcle.mread.common;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * BaseFragment基类
 * 
 * 程序创建Fragment需要继承实现自此类<p>
 * 必须实现的方法为{@link #inflateContentView() 返回Fragment需要加载的资源Id}}
 * {@link #layoutInit(LayoutInflater, Bundle) 此方法为非必须实现，子类可以实现定义自己的业务逻辑。如保存状态值等}
 * {@link #requestData() 方法为获取请求数据，子类重写此方法获取加载数据}
 * 需要注意：默认每个Fragment布局中要 {@link #include() 三种加载状态布局}
 * 
 * @author Ming
 * @date 2015年7月30日 下午4:25:56
 */
public abstract class BaseFragment extends Fragment {
	
    static final String TAG = BaseFragment.class.getSimpleName();
    
    /**
     * 根视图
     */
    private View rootView;
    /**
     * 加载中视图
     */
    protected View loadingLayout;
    /**
     * 加载失败视图
     */
    protected View loadFailureLayout; 
    /**
     * 内容视图
     */
    protected View contentLayout;
    /**
     * 空视图
     */
    protected  View emptyLayout;
    /**
     * 标志是否ContentView是否为空
     */
    private boolean contentEmpty = true;

    
    /**
     * 是否开始友盟统计时长
     */
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	
	/**
	 * 显示或者隐藏View
	 * @Description: 
	 * @author Ming  
	 * @date 2015年8月1日 下午2:23:51
	 */
	protected void toggleView(View mView,boolean isShow) {
		if(isShow){
			mView.setVisibility(View.VISIBLE);
		}else{
			mView.setVisibility(View.GONE);
		}
	}
	/**
	 * 返回Context
	 * @Description: 
	 * @author Ming  
	 * @date 2015年8月1日 上午10:00:08
	 */
	public Context getContext() {
		return getActivity();
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(rootView!=null){
			ViewGroup mViewGroup=(ViewGroup) rootView.getParent();
			if(mViewGroup!=null){
				mViewGroup.removeView(rootView);
			}
		}else{
	        if (inflateContentView() > 0) {
	            rootView =inflater.inflate(inflateContentView(),container,false);
	            rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	            /**
	             * 子类选择性实现
	             */
	            layoutInit(inflater, savedInstanceState);
	            
	            /**
	             * 子类重写加载子类布局View
	             */
	            initContainerView();
	        }
		}
		doUpdateView();
        return rootView;
    }
	
	/**
	 * 需要每次执行更新代码
	 * @Description: 
	 * @author Ming  
	 * @date 2015年8月21日 下午1:42:53
	 */
	protected void doUpdateView(){};
	
	/**
	 * 根据Id获取View
	 * @Description: 
	 * @author Ming  
	 * @date 2015年7月29日 上午9:11:05
	 */
	@SuppressWarnings("unchecked")
	protected <T extends View> T getViewById(int id) {
		return (T) rootView.findViewById(id);
	}
	/**
	 * 返回根目录视图
	 * @Description: 
	 * @author Ming  
	 * @date 2015年7月30日 下午4:34:03
	 */
    protected View getRootView() {
        return rootView;
    }

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
	
	/**
	 * 处理
	 * @Description: 
	 * @author Ming  
	 * @date 2015年7月30日 下午4:34:29
	 */
    public boolean onHomeClick() {
        return onBackClick();
    }

    /**
     * 子类重写选择是否处理返回按键操作
     * @return
     */
    protected boolean onBackClick() {
        return false;
    }

    /**
     * 初始化页面布局
     * @Description: 
     * @author Ming  
     * @date 2015年7月30日 下午4:23:19
     */
    protected abstract void initContainerView();

    /**
     * 子类重写这个方法，初始化视图 
     * 此方法非必须重写。
     * @param inflater
     * @param savedInstanceSate
     */
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {}
    
    /**
     * 根据Id查找View
     * @Description: 
     * @author Ming  
     * @date 2015年7月28日 下午7:04:03
     */
    protected View findViewById(int viewId) {
        if (rootView == null)
            return null;

        return rootView.findViewById(viewId);
    }
    
    /**
     * 返回构建contentView的资源Id
     * @Description: 
     * @author Ming  
     * @date 2015年7月28日 下午7:03:38
     */
    abstract protected int inflateContentView();

    
    public void setContentEmpty(boolean empty) {
        this.contentEmpty = empty;
    }
    
    /**
     * 返回是否Content empty
     * @Description: 
     * @author Ming  
     * @date 2015年7月28日 下午7:04:34
     */
    public boolean isContentEmpty() {
        return contentEmpty;
    }

    /**
     * 视图点击回调，子类重写
     *
     * @param view
     */
    public void onViewClicked(View view) {
//        if (view.getId() == R.id.layoutReload){
//        	requestData();
//        }else if (view.getId() == R.id.layoutRefresh){
//        	requestData();
//        }
    }
    
    /**
     * toggle View isVisiable
     * @Description: 
     * @author Ming  
     * @date 2015年7月28日 下午7:05:33
     */
    protected void setViewVisiable(View v, int visibility) {
        if (v != null)
            v.setVisibility(visibility);
    }


	@Override
    public void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {
        }
    }

	@Override
    public void onDetach() {
        super.onDetach();
        /**
         * 解决Fragment切换造成Activity异常销毁问题
         */
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 绑定View 点击监听
     * @Description: 
     * @author Ming  
     * @date 2015年7月28日 下午7:08:30
     */
    protected void setViewOnClick(View v) {
        if (v == null)
            return;

        v.setOnClickListener(innerOnClickListener);
    }
    
    /**
     * 设置内部监听
     */
    View.OnClickListener innerOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onViewClicked(v);
        }

    };
    
    /**
     * 保存当前Fragment状态
     */
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
}

