package com.example.diabetestracker.util;

import com.example.diabetestracker.entities.RecordTag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
    static final String TIME_PATTERN = "hh:mm a";
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
     * Format date theo dạng yyyy-MM-dd hh:mm:ss
     * Chú ý: dùng hàm này để format date thời gian người dùng nhập trước khi insert data có
     * thuộc tính date time vào database
     * @param date ngày cần format
     * @return chuỗi dạng yyyy-MM-dd hh:mm:ss
     */
    public static String format(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
        return formatter.format(date);
    }

    /**
     * Format time theo dạng hh:mm:ss a
     * @param date ngày cần format
     * @return chuỗi dạng hh:mm:ss a vd 1:30 AM, 2:30 PM
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
     * Chuyển chuỗi có dạng yyyy-MM-dd hh:mm:ss thành dạng date
     * Chú ý:
     *      - Do SqLite không hỗ trợ kiểu dữ liệu datetime nên mọi dữ liệu liên quan đến datetime
     * sẽ chuyển thành kiểu string hoặc tương đương.
     *      - Để tránh gây ra ParseException thì trước khi insert data có dữ liệu datetime vào database
     * thì nên dùng hàm format(Date date) được định nghĩa bên trên để format lại datetime và dùng hàm này
     * để format chuỗi datetime khi đọc từ dữ liệu từ database lên
     *
     * @param date chuỗi datetime cần format
     * @return Date được format theo yyyy-MM-dd hh:mm:ss
     * @throws ParseException chuỗi có dạng khác yyyy-MM-dd hh:mm:ss
     */
    public static Date parse(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
        return formatter.parse(date);
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
