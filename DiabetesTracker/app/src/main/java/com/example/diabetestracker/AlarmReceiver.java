package com.example.diabetestracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderAndInfo;
import com.example.diabetestracker.entities.ReminderInfo;
import com.example.diabetestracker.repository.ReminderRepository;
import com.example.diabetestracker.viewmodels.ReminderViewModel;

import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    final int ALARM_REQUEST_CODE = 1840538;
    final int NOTIFICATION_ID = 1;

    public static final String TYPE_KEY = "Type key";
    public static final String ID_KEY = "Id reminder key";
    public static final String TIME_KEY = "time reminder key";
    public static final String REQUEST_CODE_KEY = "request code of reminder";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String type = bundle.getString(TYPE_KEY);

        Notification notification = new NotificationCompat.Builder(context, MainActivity.NOTIFICATION_CHANEL_ID)
                .setSmallIcon(R.drawable.ic_diabetes)
                .setContentTitle("Diabetes Tracker")
                .setContentText(type)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notification);
        //Update enabled in database and cancel alarm
        if (bundle.getString(TIME_KEY) != null) {
            int id = bundle.getInt(ID_KEY);
            String time = bundle.getString(TIME_KEY);
            int requestCode = bundle.getInt(REQUEST_CODE_KEY);

            Reminder reminder = new Reminder();
            reminder.setId(id);
            reminder.setTime(time);
            reminder.setType(type);
            reminder.setEnabled(false);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent1 = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent1, 0);
            alarmManager.cancel(pendingIntent);

            ReminderRepository repository = new ReminderRepository(context);
            repository.update(reminder);
        }
    }
}
