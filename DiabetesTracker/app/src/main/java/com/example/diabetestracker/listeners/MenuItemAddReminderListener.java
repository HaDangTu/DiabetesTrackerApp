package com.example.diabetestracker.listeners;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.AlarmReceiver;
import com.example.diabetestracker.R;
import com.example.diabetestracker.SettingsFragment;
import com.example.diabetestracker.TimePickerDialogFragment;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderAndInfo;
import com.example.diabetestracker.entities.ReminderInfo;
import com.example.diabetestracker.repository.ReminderRepository;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.util.Day;
import com.example.diabetestracker.viewmodels.ReminderViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class MenuItemAddReminderListener extends BaseMenuItemClickListener {
    private final ReminderRepository repository;
    private int nextId;

    public MenuItemAddReminderListener(Fragment fragment) {
        super(fragment);
        repository = new ReminderRepository(fragment.getActivity());

        ReminderViewModel viewModel = new ViewModelProvider(fragment.requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getActivity().getApplication()))
                .get(ReminderViewModel.class);
        viewModel.getAll().observe(fragment.requireActivity(), new Observer<List<ReminderAndInfo>>() {
            @Override
            public void onChanged(List<ReminderAndInfo> reminderAndInfos) {
                nextId = 1;
                if (reminderAndInfos.size() > 0) {
                    ReminderAndInfo last = reminderAndInfos.get(reminderAndInfos.size() - 1);
                    Reminder lastReminder = last.getReminder();
                    nextId = lastReminder.getId() + 1;
                }
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_save) {
            final AddReminderFragment addReminderFragment = (AddReminderFragment) fragment;
            //Mapping data
            String type = addReminderFragment.getType();

            Hashtable<String, Boolean> repeatDays = addReminderFragment.getRepeatDays();
            Context context = fragment.getContext();

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra(AlarmReceiver.TYPE_KEY, type);

            int hourOfDay = addReminderFragment.getHourOfDay();
            int minute = addReminderFragment.getMinute();

            Calendar calendar = Calendar.getInstance();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(fragment.getContext());
            String timeSetting = sharedPreferences.getString(SettingsFragment.TIME_KEY, TimePickerDialogFragment.TIME_24);
            String timeText = addReminderFragment.getTime();

            if (!timeSetting.equals(TimePickerDialogFragment.TIME_24)) {
                timeText = DateTimeUtil.convertTime24(timeText);
            }
            Reminder reminder = new Reminder();
            reminder.setId(nextId);
            reminder.setTime(timeText);
            reminder.setType(type);
            reminder.setEnabled(true);

            repository.insert(reminder);

            if (!isRepeat(repeatDays)) {//Không lặp lại báo thức
                ReminderInfo reminderInfo = new ReminderInfo();
                reminderInfo.setReminderId(nextId);
                reminderInfo.setRequestCode(nextId * 100);
                reminderInfo.setRepeatDay("");

                repository.insert(reminderInfo);

                intent.putExtra(AlarmReceiver.ID_KEY, nextId);
                intent.putExtra(AlarmReceiver.TIME_KEY, reminder.getTime());

                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                Date time = calendar.getTime();;
                Date now = new Date();
                if (now.after(time))
                    calendar.setTimeInMillis(calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        reminderInfo.getRequestCode(), intent, 0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(), pendingIntent);
                }
                else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(), pendingIntent);
                }
            }
            else { //Lặp lại báo thức
                List<ReminderInfo> infos = new ArrayList<>();

                Day[] days = Day.values();
                for (int i = 0; i < days.length; i++) {
                    if (repeatDays.get(days[i].value)) {
                        ReminderInfo info = new ReminderInfo();
                        info.setReminderId(nextId);
                        info.setRequestCode(nextId * 100 + i);
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
        }

        return false;
    }

    public boolean isRepeat(Hashtable<String, Boolean> repeatDays) {
        return repeatDays.containsValue(true);
    }
}
