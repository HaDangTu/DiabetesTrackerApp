package com.example.diabetestracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderAndInfo;
import com.example.diabetestracker.entities.ReminderInfo;
import com.example.diabetestracker.util.DateTimeUtil;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.ReminderViewHolder> {

    private Context context;
    private List<ReminderAndInfo> reminders;

    private OnReminderClickListener itemClickListener;
    private OnButtonNotificationClickListener btnClickListener;

    public interface OnReminderClickListener {
        void onClick(View v, ReminderAndInfo reminderAndInfo);
    }

    public interface OnButtonNotificationClickListener {
        void onClick(View v, ReminderAndInfo reminderAndInfo);
    }

    public ReminderRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setReminders(List<ReminderAndInfo> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnReminderClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setBtnClickListener(OnButtonNotificationClickListener listener) {
        this.btnClickListener = listener;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reminder_item, parent, false);
        return new ReminderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminders.get(position).getReminder();
        List<ReminderInfo> infos = reminders.get(position).getInfos();

        String repeatDay = "";
        for (ReminderInfo info : infos) {
            repeatDay = repeatDay.concat(info.getRepeatDay() + " ");
        }

        repeatDay = repeatDay.trim();

        try {
            Date date = DateTimeUtil.parseTime(reminder.getTime());
            holder.setTimeText(DateTimeUtil.formatTime24(date));
            holder.setTypeText(reminder.getType());
            holder.setRepeatDaysText(repeatDay);
            holder.setOnOffButtonImage(reminder.isEnabled());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (reminders != null)
          return reminders.size();
        return 0;
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MaterialCardView cardView;
        private MaterialTextView timeText;
        private MaterialTextView repeatDaysText;
        private MaterialTextView typeText;
        private ImageButton onOffButton;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.reminder_card_view);
            cardView.setOnClickListener(this);
            timeText = itemView.findViewById(R.id.time_text);
            typeText = itemView.findViewById(R.id.type_name_text);
            repeatDaysText = itemView.findViewById(R.id.repeat_text);
            onOffButton = itemView.findViewById(R.id.on_off_reminder);
            onOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnClickListener.onClick(v, reminders.get(getAdapterPosition()));
                }
            });
        }

        public void setTimeText(String time) {
            timeText.setText(time);
        }

        public void setTypeText(String type) {
            typeText.setText(type);
        }

        public void setRepeatDaysText(String text) {
            repeatDaysText.setVisibility(text.equals("") ? View.GONE : View.VISIBLE);
            repeatDaysText.setText(text);
        }

        public void setOnOffButtonImage(boolean enabled) {
            onOffButton.setImageLevel(enabled ? 1 : 0);
        }
        @Override
        public void onClick(View v) {
            //TODO xem lại logic code chỗ này vì item click sẽ gây ra exception
            itemClickListener.onClick(v, reminders.get(getAdapterPosition()));
        }
    }
}
