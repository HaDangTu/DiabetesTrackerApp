package com.example.diabetestracker.listeners;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;

import com.example.diabetestracker.AlarmReceiver;
import com.example.diabetestracker.ReminderRecyclerAdapter;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderAndInfo;
import com.example.diabetestracker.entities.ReminderInfo;
import com.example.diabetestracker.repository.ReminderRepository;
import com.example.diabetestracker.util.DateTimeUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationButtonClickListener implements ReminderRecyclerAdapter.OnButtonNotificationClickListener {

    private final int ON = 1;
    private final int OFF = 0;
    private ReminderRepository repository;

    public NotificationButtonClickListener(Application application) {
        repository = new ReminderRepository(application);
    }

    @Override
    public void onClick(View v, ReminderAndInfo reminderAndInfo) {
        Reminder reminder = reminderAndInfo.getReminder();
        List<ReminderInfo> infos = reminderAndInfo.getInfos();

        Context context = v.getContext();

        ImageButton button = (ImageButton) v;
        boolean enabled = reminder.isEnabled();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.TYPE_KEY, reminder.getTime());

        enabled = !enabled;

        reminder.setEnabled(enabled);
        repository.update(reminder);


        if (!enabled) {
            button.setImageLevel(OFF);
            //cancel all alarm
            for (ReminderInfo info : infos) {
                int requestCode = info.getRequestCode();
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                       requestCode, intent, 0);

                alarmManager.cancel(pendingIntent);
            }
        }
        else {
            //enable all alarm
            button.setImageLevel(ON);
            if (infos.size() > 1) { //Repeat
                for (ReminderInfo info : infos) {
                    int requestCode = info.getRequestCode();
                    try {
                        //parse date string in database to date to get hourOfDay and minute
                        Date time = DateTimeUtil.parseTime24(reminder.getTime());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(time.getTime());

                        int hourOfDay = calendar.get(Calendar.DAY_OF_WEEK);
                        int minute = calendar.get(Calendar.MINUTE);

                        String repeatDay = info.getRepeatDay();
                        //Set repeat day
                        switch (repeatDay) {
                            case "T2":
                                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                                break;
                            case "T3":
                                calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                                break;
                            case "T4":
                                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                                break;
                            case "T5":
                                calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                                break;
                            case "T6":
                                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                                break;
                            case "T7":
                                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                                break;
                            case "CN":
                                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                                break;
                        }

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        Date now = new Date();
                        time = calendar.getTime();
                        if (now.after(time))
                            calendar.setTimeInMillis(calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY * 7);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode,
                                intent, 0);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7,
                                pendingIntent);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
            else { //Not repeat
                ReminderInfo info = infos.get(0);
                int requestCode = info.getRequestCode();

                intent.putExtra(AlarmReceiver.ID_KEY, reminder.getId());
                intent.putExtra(AlarmReceiver.TIME_KEY, reminder.getTime());
                intent.putExtra(AlarmReceiver.REQUEST_CODE_KEY, info.getRequestCode());

                try {
                    Date time = DateTimeUtil.parseTime24(reminder.getTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(time.getTime());

                    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    Date now = new Date();
                    time = calendar.getTime();
                    if (now.after(time))
                        calendar.setTimeInMillis(calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode,
                            intent, 0);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), pendingIntent);
                    }
                    else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), pendingIntent);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
