package cn.mtcle.mread.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;

public class DateUtil {
	public static SimpleDateFormat sdf_D = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf_DT = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
     * 某一个月第一天和最后一天
     * @param date
     * @return
     */
    public static Map<String, String> getFirstday_Lastday_Month(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        
        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = sdf_D.format(gcLast.getTime());

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = sdf_D.format(calendar.getTime());

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }
    
    /**
	 * 方法名：compareDaysDiff
	 * 方法描述：计算天数差
	 * 创建人：lmy
	 * 创建日期：2012-3-27
	 * 修改人：
	 * 修改日期：
	 * 备注：
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long compareDaysDiff(String beginDate,String endDate){
		try{
		    Date d_beginDate = sdf_D.parse(beginDate);
		    Date d_endDate = sdf_D.parse(endDate);
		    long diff = d_endDate.getTime() - d_beginDate.getTime();
		    long months = diff / (1000 * 60 * 60 * 24);
		    return months;
		}catch (Exception e){
		}
		return 0;
	}
	
	/**
	 * getCurrDate
	 * 描述：获取当前时间
	 * 创建人：李满义
	 * 创建时间：2014-4-3 上午10:24:40
	 * @return
	 * 修改人：
	 * 修改时间：
	 */
	public static String getCurrDate(){
		return sdf_D.format(new Date(System.currentTimeMillis()));
	}
	public static String getCurrDateTime(){
		return sdf_DT.format(new Date(System.currentTimeMillis()));
	}
	public static long compareDaysDiff(String beginDate){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try{
		    Date d_beginDate = df.parse(beginDate);
		    Date d_endDate = new Date(System.currentTimeMillis());
		    long diff = d_endDate.getTime() - d_beginDate.getTime();
		    long months = diff / (1000 * 60 * 60 * 24);
		    return months;
		}catch (Exception e){
		}
		return 0;
	}
	@SuppressLint("SimpleDateFormat")
	public static int getCurrentHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(new Date(System.currentTimeMillis()));
		return Integer.parseInt(hour);
	}
}
