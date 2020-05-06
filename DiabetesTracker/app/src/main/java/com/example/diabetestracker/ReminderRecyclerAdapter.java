package com.example.diabetestracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderTag;
import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.util.DateTimeUtil;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.ReminderViewHolder> {

    private Context context;
    private List<ReminderTag> reminders;

    private OnReminderClickListener listener;

    public interface OnReminderClickListener {
        void onClick(ReminderTag reminder);
    }

    public ReminderRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setReminders(List<ReminderTag> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    public void setListener(OnReminderClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reminder_item, parent, false);
        return new ReminderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        ReminderTag reminderTag = reminders.get(position);
        Reminder reminder = reminderTag.getReminder();
        Tag tag = reminderTag.getTag();
        try {
            Date date = DateTimeUtil.parse(reminder.getDateTime());
            holder.setTimeText(DateTimeUtil.format(date));
            holder.setTagText(tag.getName());
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
        private MaterialTextView tagText;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.reminder_card_view);
            cardView.setOnClickListener(this);
            timeText = itemView.findViewById(R.id.time_text);
            tagText = itemView.findViewById(R.id.tag_name_text);
        }

        public void setTimeText(String time) {
            timeText.setText(time);
        }

        public void setTagText(String tag) {
            tagText.setText(tag);
        }
        @Override
        public void onClick(View v) {
            listener.onClick(reminders.get(getAdapterPosition()));
        }
    }
}
