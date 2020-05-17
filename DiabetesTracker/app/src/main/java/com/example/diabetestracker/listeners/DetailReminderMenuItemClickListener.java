package com.example.diabetestracker.listeners;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;
import androidx.fragment.app.FragmentManager;

import com.example.diabetestracker.AlarmReceiver;
import com.example.diabetestracker.DetailRecordFragment;
import com.example.diabetestracker.DetailReminderFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderInfo;
import com.example.diabetestracker.repository.ReminderRepository;
import com.example.diabetestracker.util.Day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class DetailReminderMenuItemClickListener extends BaseMenuItemClickListener {
    private ReminderRepository repository;

    public DetailReminderMenuItemClickListener(DetailReminderFragment fragment) {
        super(fragment);
        repository = new ReminderRepository(fragment.getContext());

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        DetailReminderFragment detailReminderFragment = (DetailReminderFragment) fragment;
        int id = item.getItemId();
        Context context = fragment.getContext();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.TYPE_KEY, detailReminderFragment.getType());

        switch (id) {
            case R.id.item_edit:
                //IMPORTANT Clear all old reminder info (avoid duplicate request code)
                repository.delete(detailReminderFragment.getReminderInfos());

                Calendar calendar = Calendar.getInstance();

                Hashtable<String, Boolean> repeatDays = detailReminderFragment.getRepeatDays();

                int hourOfDay = detailReminderFragment.getHourOfDay();
                int minute = detailReminderFragment.getMinute();

                Reminder reminder = detailReminderFragment.getReminder();
                reminder.setType(detailReminderFragment.getType());
                reminder.setTime(detailReminderFragment.getTime());
                reminder.setEnabled(true);

                repository.update(reminder);

                if (!repeatDays.contains(true)) { //Not repeat
                    intent.putExtra(AlarmReceiver.ID_KEY, reminder.getId());
                    intent.putExtra(AlarmReceiver.TIME_KEY, reminder.getTime());

                    ReminderInfo reminderInfo = new ReminderInfo();
                    reminderInfo.setRequestCode(reminder.getId() * 100);
                    reminderInfo.setRepeatDay("");
                    reminderInfo.setReminderId(reminder.getId());

                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    Date time = calendar.getTime();
                    Date now = new Date();
                    if (now.after(time))
                        calendar.setTimeInMillis(calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY);

                    repository.insert(reminderInfo);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                            reminderInfo.getRequestCode(), intent, 0);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), pendingIntent);
                    } else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), pendingIntent);
                    }
                } else { //Repeat
                    List<ReminderInfo> infos = new ArrayList<>();

                    Day[] days = Day.values();
                    for (int i = 0; i < days.length; i++) {
                        if (repeatDays.get(days[i].value)) {
                            ReminderInfo info = new ReminderInfo();
                            info.setReminderId(reminder.getId());
                            info.setRequestCode(reminder.getId() * 100 + i);
                            info.setRepeatDay(days[i].value);

                            infos.add(info);

                            //Set day repeat
                            switch (days[i].value) {
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
                            Date time = calendar.getTime();
                            if (now.after(time))
                                calendar.setTimeInMillis(calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY * 7);

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                                    info.getRequestCode(), intent, 0);

                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7,
                                    pendingIntent);
                        }
                    }

                    repository.insert(infos);
                }
                FragmentManager fragmentManager = fragment.getFragmentManager();
                fragmentManager.popBackStack();
                return true;
            case R.id.item_delete:
                Reminder reminder1 = detailReminderFragment.getReminder();
                List<ReminderInfo> infos1 = detailReminderFragment.getReminderInfos();

                for (ReminderInfo info : infos1) {
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                            info.getRequestCode(), intent, 0);

                    alarmManager.cancel(pendingIntent);
                }

                repository.delete(infos1);
                repository.delete(reminder1);

                FragmentManager fragmentManager1 = fragment.getFragmentManager();
                fragmentManager1.popBackStack();
                return true;
        }
        return false;
    }
}