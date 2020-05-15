package com.example.diabetestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.diabetestracker.listeners.CancelOnClickListener;
import com.example.diabetestracker.listeners.CheckableButtonClickListener;
import com.example.diabetestracker.listeners.DropdownItemClickListener;
import com.example.diabetestracker.listeners.MenuItemAddReminderListener;
import com.example.diabetestracker.listeners.TimeIconOnClickListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.util.Day;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class AddReminderActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputEditText timeEditText;
    private TextInputLayout timeInputLayout;
    private CheckBox notificationRepeatCheckBox;
    private LinearLayout buttonGroup;
    private AutoCompleteTextView typeAutoComplete;

    private String type;
    private Hashtable<String, Boolean> repeatDays;

    private int hourOfDay;
    private int minute;

    private MaterialButton btnMonday;
    private MaterialButton btnTuesday;
    private MaterialButton btnWednesday;
    private MaterialButton btnThursday;
    private MaterialButton btnFriday;
    private MaterialButton btnSaturday;
    private MaterialButton btnSunday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new MenuItemAddReminderListener(this));
        toolbar.setNavigationOnClickListener(new CancelOnClickListener(this));

        timeEditText = findViewById(R.id.time_remind_text);
        timeEditText.setInputType(InputType.TYPE_NULL);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        timeEditText.setText(DateTimeUtil.formatTime24(date));
        timeEditText.setOnClickListener(new TimeIconOnClickListener(this));
        timeInputLayout = findViewById(R.id.time_input_layout);
        timeInputLayout.setEndIconOnClickListener(new TimeIconOnClickListener(this));

        typeAutoComplete = findViewById(R.id.type_autocompletetext);
        typeAutoComplete.setInputType(InputType.TYPE_NULL);
        typeAutoComplete.setOnItemClickListener(new DropdownItemClickListener(this));

        String[] types = getResources().getStringArray(R.array.reminder_types);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.dropdown_menu_item, types);
        typeAutoComplete.setAdapter(typeAdapter);

        notificationRepeatCheckBox = findViewById(R.id.repeat_checkbox);
        notificationRepeatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enabledRepeat(isChecked);
                reset();
            }
        });

        initRepeatsDay();

        buttonGroup = findViewById(R.id.btn_days_group);


        btnMonday = findViewById(R.id.btn_monday);
        btnTuesday = findViewById(R.id.btn_tuesday);
        btnWednesday = findViewById(R.id.btn_wednesday);
        btnThursday = findViewById(R.id.btn_thursday);
        btnFriday = findViewById(R.id.btn_friday);
        btnSaturday = findViewById(R.id.btn_saturday);
        btnSunday = findViewById(R.id.btn_sunday);
        reset();
    }

    private void reset() {
        btnMonday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnMonday,
                false, repeatDays));
        btnMonday.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnMonday.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));

        btnTuesday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnTuesday,
                false, repeatDays));
        btnTuesday.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnTuesday.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));

        btnWednesday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnWednesday,
                false, repeatDays));
        btnWednesday.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnWednesday.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));

        btnThursday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnThursday,
                false, repeatDays));
        btnThursday.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnThursday.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));

        btnFriday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnFriday,
                false, repeatDays));
        btnFriday.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnFriday.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));

        btnSaturday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnSaturday,
                false, repeatDays));
        btnSaturday.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnSaturday.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));

        btnSunday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnSunday,
                false, repeatDays));
        btnSunday.setTextColor(getResources().getColor(R.color.colorPrimary));
        btnSunday.setBackgroundColor(getResources().getColor(R.color.colorTextPrimary));
    }

    private void initRepeatsDay() {
        repeatDays = new Hashtable<>();
        repeatDays.put(Day.MONDAY.value, false);
        repeatDays.put(Day.TUESDAY.value, false);
        repeatDays.put(Day.WEDNESDAY.value, false);
        repeatDays.put(Day.THURSDAY.value, false);
        repeatDays.put(Day.FRIDAY.value, false);
        repeatDays.put(Day.SATURDAY.value, false);
        repeatDays.put(Day.SUNDAY.value, false);
    }

    public void setTime(String time) {
        timeEditText.setText(time);
    }

    public String getTime() {
        return timeEditText.getText().toString();
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void enabledRepeat(boolean enabled) {
        initRepeatsDay();
        buttonGroup.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
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
}
