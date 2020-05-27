package com.example.diabetestracker;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderAndInfo;
import com.example.diabetestracker.entities.ReminderInfo;
import com.example.diabetestracker.listeners.CancelOnClickListener;
import com.example.diabetestracker.listeners.CheckableButtonClickListener;
import com.example.diabetestracker.listeners.DetailReminderMenuItemClickListener;
import com.example.diabetestracker.listeners.TimeIconOnClickListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.util.Day;
import com.example.diabetestracker.viewmodels.ReminderViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailReminderFragment extends Fragment {

    private MaterialToolbar toolbar;
    private TextInputEditText timeEditText;
    private TextInputLayout timeInputLayout;
    private CheckBox notificationRepeatCheckBox;
    private LinearLayout buttonGroup;
    private AutoCompleteTextView typeAutoComplete;

    private Hashtable<String, Boolean> repeatDays;

    private int hourOfDay;
    private int minute;

    private Reminder reminder;
    private List<ReminderInfo> reminderInfos;

    private MaterialButton btnMonday;
    private MaterialButton btnTuesday;
    private MaterialButton btnWednesday;
    private MaterialButton btnThursday;
    private MaterialButton btnFriday;
    private MaterialButton btnSaturday;
    private MaterialButton btnSunday;

    public DetailReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_reminder, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String time = sharedPreferences.getString(SettingsFragment.TIME_KEY,
                TimePickerDialogFragment.TIME_24);

        initRepeatDays();

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new DetailReminderMenuItemClickListener(this));
        toolbar.setNavigationOnClickListener(new CancelOnClickListener(this));


        timeEditText = view.findViewById(R.id.time_remind_text);
        timeEditText.setInputType(InputType.TYPE_NULL);
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        timeEditText.setText(DateTimeUtil.formatTime24(date));
        timeEditText.setOnClickListener(new TimeIconOnClickListener(this));
        timeInputLayout = view.findViewById(R.id.time_input_layout);
        timeInputLayout.setEndIconOnClickListener(new TimeIconOnClickListener(this));

        typeAutoComplete = view.findViewById(R.id.type_autocompletetext);
        typeAutoComplete.setInputType(InputType.TYPE_NULL);
//        typeAutoComplete.setOnItemClickListener(new DropdownItemClickListener(this));

        String[] types = getResources().getStringArray(R.array.reminder_types);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity().getApplication(),
                R.layout.dropdown_menu_item, types);
        typeAutoComplete.setAdapter(typeAdapter);

        notificationRepeatCheckBox = view.findViewById(R.id.repeat_checkbox);
        notificationRepeatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enabledRepeat(isChecked);
            }
        });

        buttonGroup = view.findViewById(R.id.btn_days_group);

        btnMonday = view.findViewById(R.id.btn_monday);
        btnMonday.setOnClickListener(new CheckableButtonClickListener(
                repeatDays.get(Day.MONDAY.value), repeatDays, this));

        btnTuesday = view.findViewById(R.id.btn_tuesday);
        btnTuesday.setOnClickListener(new CheckableButtonClickListener(
                repeatDays.get(Day.TUESDAY.value), repeatDays, this));

        btnWednesday = view.findViewById(R.id.btn_wednesday);
        btnWednesday.setOnClickListener(new CheckableButtonClickListener(
                repeatDays.get(Day.WEDNESDAY.value), repeatDays, this));

        btnThursday = view.findViewById(R.id.btn_thursday);
        btnThursday.setOnClickListener(new CheckableButtonClickListener(
                repeatDays.get(Day.THURSDAY.value), repeatDays, this));

        btnFriday = view.findViewById(R.id.btn_friday);
        btnFriday.setOnClickListener(new CheckableButtonClickListener(
                repeatDays.get(Day.FRIDAY.value), repeatDays, this));

        btnSaturday = view.findViewById(R.id.btn_saturday);
        btnSaturday.setOnClickListener(new CheckableButtonClickListener(
                repeatDays.get(Day.SATURDAY.value), repeatDays, this));

        btnSunday = view.findViewById(R.id.btn_sunday);
        btnSunday.setOnClickListener(new CheckableButtonClickListener(
                repeatDays.get(Day.SUNDAY.value), repeatDays, this));

        ReminderViewModel reminderViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ReminderViewModel.class);

        reminderViewModel.getSelectedItem().observe(requireActivity(), new Observer<ReminderAndInfo>() {
            @Override
            public void onChanged(ReminderAndInfo reminderAndInfo) {
                Reminder reminder = reminderAndInfo.getReminder();
                List<ReminderInfo> infos = reminderAndInfo.getInfos();

                setReminder(reminder);
                setReminderInfos(infos);

                try {
                    Date time = DateTimeUtil.parseTime24(reminder.getTime());
                    if (time.equals(TimePickerDialogFragment.TIME_24))
                        setTime(DateTimeUtil.formatTime24(time));
                    else {
                        setTime(DateTimeUtil.formatTime12(time));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                setType(reminder.getType());

                if (infos.size() > 1) {
                    setRepeatCheck(true);
                    for (ReminderInfo info : infos) {
                        String repeatDay = info.getRepeatDay();

                        repeatDays.put(info.getRepeatDay(), true);
                        switch (repeatDay) {
                            case "T2":
                                btnMonday.performClick();
                                break;
                            case "T3":
                                btnTuesday.performClick();
                                break;
                            case "T4":
                                btnWednesday.performClick();
                                break;
                            case "T5":
                                btnThursday.performClick();
                                break;
                            case "T6":
                                btnFriday.performClick();
                                break;
                            case "T7":
                                btnSaturday.performClick();
                                break;
                            case "CN":
                                btnSunday.performClick();
                                break;
                        }
                    }
                }
            }
        });

        return view;
    }

    private void initRepeatDays() {
        repeatDays = new Hashtable<>();
        repeatDays.put(Day.MONDAY.value, false);
        repeatDays.put(Day.TUESDAY.value, false);
        repeatDays.put(Day.WEDNESDAY.value, false);
        repeatDays.put(Day.THURSDAY.value, false);
        repeatDays.put(Day.FRIDAY.value, false);
        repeatDays.put(Day.SATURDAY.value, false);
        repeatDays.put(Day.SUNDAY.value, false);
    }

    public void setColorButton(MaterialButton button, int textColor, int backgroundColor) {
        button.setTextColor(getResources().getColor(textColor));
        button.setBackgroundColor(getResources().getColor(backgroundColor));
    }

    public void setTime(String time) {
        timeEditText.setText(time);
    }

    public String getTime() {
        return timeEditText.getText().toString();
    }

    public void setType(String type) {
        typeAutoComplete.setText(type, false);
    }

    public String getType() {
        return typeAutoComplete.getText().toString();
    }

    public void setRepeatCheck(boolean checked) {
        notificationRepeatCheckBox.setChecked(checked);
    }

    public void enabledRepeat(boolean enabled) {
        if (enabled) {
            buttonGroup.setVisibility(View.VISIBLE);
        } else {
            buttonGroup.setVisibility(View.GONE);
            initRepeatDays();

            //reset all button day
            setColorButton(btnMonday, R.color.colorPrimary, R.color.colorTextPrimary);
            setColorButton(btnTuesday, R.color.colorPrimary, R.color.colorTextPrimary);
            setColorButton(btnWednesday, R.color.colorPrimary, R.color.colorTextPrimary);
            setColorButton(btnThursday, R.color.colorPrimary, R.color.colorTextPrimary);
            setColorButton(btnFriday, R.color.colorPrimary, R.color.colorTextPrimary);
            setColorButton(btnSaturday, R.color.colorPrimary, R.color.colorTextPrimary);
            setColorButton(btnSunday, R.color.colorPrimary, R.color.colorTextPrimary);
        }

    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isRepeat() {
        return notificationRepeatCheckBox.isChecked();
    }

    public Hashtable<String, Boolean> getRepeatDays() {
        return repeatDays;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public List<ReminderInfo> getReminderInfos() {
        return reminderInfos;
    }

    public void setReminderInfos(List<ReminderInfo> reminderInfos) {
        this.reminderInfos = reminderInfos;
    }
}
