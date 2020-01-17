package com.sjl.rfm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename TimeUtils.java
 * @time 2019/12/19 17:02
 * @copyright(C) 2019 song
 */
public class TimeUtils {
    /**
     * 将时间转换成日期
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String formatDateToStr(long timeInMillis, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        return formatDateToStr(date, dateFormat);
    }

    /**
     * Date转换成字符串日期
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatDateToStr(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
}
