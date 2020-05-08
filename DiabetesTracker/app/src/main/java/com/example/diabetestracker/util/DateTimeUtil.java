package com.example.diabetestracker.util;

import com.example.diabetestracker.entities.RecordTag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    static final String DATE_TIME_PATTERN2 = "dd/MM/yyyy HH:mm";
    static final String TIME_PATTERN = "HH:mm";
    static final String DATE_PATERN = "dd/MM/yyyy";

    public static int compareDatesWithoutTime(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, 1);
        date1 = new Date(calendar.getTimeInMillis());

        calendar.setTime(date2);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, 1);
        date2 = new Date(calendar.getTimeInMillis());

        return date1.compareTo(date2);
    }

    /**
     * Format date theo dạng yyyy-MM-dd HH:mm:ss
     * Chú ý: dùng hàm này để format date thời gian khi đọc dữ liệu từ database lên
     * @param date ngày cần format
     * @return chuỗi dạng yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
        return formatter.format(date);
    }

    /**
     * Format time theo dạng HH:mm
     * @param date ngày cần format
     * @return chuỗi dạng HH:mm vd 1:30, 14:30
     */
    public static String formatTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_PATTERN, Locale.US);
        return formatter.format(date);
    }

    /**
     * Format date theo dạng dd/MM/yyyy
     * @param date ngày cần format
     * @return chuỗi dạng dd/MM/yyyy vd 23/04/2020
     */
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATERN, Locale.US);
        return formatter.format(date);
    }

    /**
     * Chuyển chuỗi có dạng yyyy-MM-dd HH:mm:ss thành dạng date
     * @param date chuỗi datetime cần format
     * @return Date
     * @throws ParseException chuỗi có dạng khác yyyy-MM-dd HH:mm:ss
     */
    public static Date parse(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
        return formatter.parse(date);
    }

    /**
     * Chuyển chuỗi có dạng dd/MM/yyyy HH:mm  thành dạng yyyy-MM-dd HH:mm:ss
     * NOTE: Dùng hàm này để convert kiểu ngày trên UI do người dùng nhập để đồng bộ với lại format
     * datetime trong database
     * @param dateString
     * @return chuỗi có dạng yyyy-MM-dd HH:mm:ss
     * @throws ParseException
     */
    public static String convertDateString(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN2, Locale.US);
            Date date = formatter.parse(dateString);

            formatter = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
            return formatter.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static class DateComparator implements Comparator<RecordTag> {
        /**
         * using for sort descending
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(RecordTag o1, RecordTag o2) {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
            try {
                Date date1 = formatter.parse(o1.getRecord().getRecordDate());
                Date date2 = formatter.parse(o2.getRecord().getRecordDate());

                return date2.compareTo(date1);
            }
            catch (ParseException e){
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        public Comparator<RecordTag> reversed() {
            return new Comparator<RecordTag>() {
                @Override
                public int compare(RecordTag o1, RecordTag o2) {
                    SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
                    try {
                        Date date1 = formatter.parse(o1.getRecord().getRecordDate());
                        Date date2 = formatter.parse(o2.getRecord().getRecordDate());

                        return date1.compareTo(date2);
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }

                    return 0;
                }
            };
        }
    }
}
