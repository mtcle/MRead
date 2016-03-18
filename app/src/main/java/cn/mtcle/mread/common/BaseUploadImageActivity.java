package cn.mtcle.mread.common;

import java.io.File;
import java.io.IOException;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import cn.mtcle.mread.util.ViewUtil;

public class BaseUploadImageActivity extends BaseFirstLevActivity{

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.initView();
	}
	
	protected void showPickAlert() {
		ViewUtil.showAalertWithItems(mContext,
				new String[]{"相册", "照相机"},
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (which == 0) {
							openAlbum();
						} else if (which == 1) {
							takePicture();
						}

					}
				}, "提示");
	}
	
	private static String picFileFullName;
	//拍照
    private  void takePicture(){
    	String state = Environment.getExternalStorageState();  
        if (state.equals(Environment.MEDIA_MOUNTED)) {  
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);  
            if (!outDir.exists()) {  
            	outDir.mkdirs();  
            }  
            File outFile =  new File(outDir, System.currentTimeMillis() + ".jpg");  
            picFileFullName = outFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));  
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);  
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);  
        } else{
        	Log.e(TAG, "请确认已经插入SD卡");
        }
    }
    
    //打开本地相册
    private void openAlbum(){
    	Intent intent = new Intent();
    	intent.setType("image/*");   
        intent.setAction(Intent.ACTION_GET_CONTENT);   
    	this.startActivityForResult(intent, PICK_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    
 	@Override
 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 		super.onActivityResult(requestCode, resultCode, data);
 		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
 			if (resultCode == RESULT_OK) {
 				Log.e(TAG, "获取图片成功，path="+picFileFullName);
 				ViewUtil.showToast(mContext,"获取图片成功，path="+picFileFullName);
 				
 				//setImageView(picFileFullName);
 			} else if (resultCode == RESULT_CANCELED) {
 				// 用户取消了图像捕获
 			} else {
 				// 图像捕获失败，提示用户
 				Log.e(TAG, "拍照失败");
 			}
 		} else if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {
 			if (resultCode == RESULT_OK) {
 				Uri uri = data.getData();
 				if(uri != null){
 					String realPath = getRealPathFromURI(uri);
 					Log.e(TAG, "获取图片成功，path="+realPath);
 					ViewUtil.showToast(mContext,"获取图片成功，path="+realPath);
 					setImageView(realPath);
 				}else{
 					Log.e(TAG, "从相册获取图片失败");
 				}
 			}
 		}
 	}
 	
 	private void setImageView(String realPath){
 		Bitmap bmp = BitmapFactory.decodeFile(realPath);
 		int degree = readPictureDegree(realPath);
 		if(degree <= 0){
 			//imageView.setImageBitmap(bmp);
 		}else{
 			//Log.e(tag, "rotate:"+degree);
 			//创建操作图片是用的matrix对象
 	 		//Matrix matrix=new Matrix();
 	 		//旋转图片动作
 	 		//matrix.postRotate(degree);
 	 		//创建新图片
 	 		//Bitmap resizedBitmap=Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
 	 		//imageView.setImageBitmap(resizedBitmap);
 		}
 	}
 	
 	/**
     * This method is used to get real path of file from from uri<br/>
     * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just-captured-from-camera
     * 
     * @param contentUri
     * @return String
     */
	public String getRealPathFromURI(Uri contentUri){
        try{
            String[] proj = {MediaStore.Images.Media.DATA};
            // Do not call Cursor.close() on a cursor obtained using this method, 
            // because the activity will do that for you at the appropriate time
            Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }catch (Exception e){
            return contentUri.getPath();
        }
	}
	
	/** 
     * 读取照片exif信息中的旋转角度<br/>
     * http://www.eoeandroid.com/thread-196978-1-1.html
     * 
     * @param path 照片路径 
     * @return角度 
     */  
    public static int readPictureDegree(String path) {  
            int degree  = 0;  
            try {  
                    ExifInterface exifInterface = new ExifInterface(path);  
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
                    switch (orientation) {  
                    case ExifInterface.ORIENTATION_ROTATE_90:  
                            degree = 90;  
                            break;  
                    case ExifInterface.ORIENTATION_ROTATE_180:  
                            degree = 180;  
                            break;  
                    case ExifInterface.ORIENTATION_ROTATE_270:  
                            degree = 270;  
                            break;  
                    }  
            } catch (IOException e) {  
                    e.printStackTrace();  
            }  
            return degree;  
    }  
	
    public interface callbackImage{
    	public void doCommit();
    }
}
