package com.example.diabetestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.listeners.CancelOnClickListener;
import com.example.diabetestracker.listeners.CheckableButtonClickListener;
import com.example.diabetestracker.listeners.DropdownItemClickListener;
import com.example.diabetestracker.listeners.TimeIconOnClickListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.viewmodels.TagViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class AddReminderActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputEditText timeEditText;
    private TextInputLayout timeInputLayout;
    private CheckBox notificationRepeatCheckBox;
    private LinearLayout buttonGroup;
    private AutoCompleteTextView tagAutoComplete;

    private int tagId;
    private Hashtable<String, Boolean> repeatDays;

    final String MONDAY = "T2";
    final String TUESDAY = "T3";
    final String WEDNESDAY = "T4";
    final String THURSDAY = "T5";
    final String FRIDAY = "T6";
    final String SATURDAY = "T7";
    final String SUNDAY = "CN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        toolbar = findViewById(R.id.toolbar);
//        toolbar.setOnMenuItemClickListener(new MenuItemDoneClickListener());
        toolbar.setNavigationOnClickListener(new CancelOnClickListener(this));

        timeEditText = findViewById(R.id.time_remind_text);
        timeEditText.setInputType(InputType.TYPE_NULL);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        timeEditText.setText(DateTimeUtil.formatTime(date));
        timeEditText.setOnClickListener(new TimeIconOnClickListener(this));
        timeInputLayout = findViewById(R.id.time_input_layout);
        timeInputLayout.setEndIconOnClickListener(new TimeIconOnClickListener(this));

        tagAutoComplete = findViewById(R.id.tag_autocompletetext);
        tagAutoComplete.setInputType(InputType.TYPE_NULL);
        tagAutoComplete.setOnItemClickListener(new DropdownItemClickListener(this));
        TagViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(TagViewModel.class);

        final ArrayAdapter<Tag> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.dropdown_menu_item);

        viewModel.getAllTag().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                adapter.addAll(tags);
                tagAutoComplete.setAdapter(adapter);
            }
        });

        notificationRepeatCheckBox = findViewById(R.id.repeat_checkbox);
//        notificationRepeatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                enabledRepeat(isChecked);
//            }
//        });

        buttonGroup = findViewById(R.id.btn_days_group);
        MaterialButton btnMonday = findViewById(R.id.btn_monday);
        btnMonday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnMonday,
                false, repeatDays));

        MaterialButton btnTuesday = findViewById(R.id.btn_tuesday);
        btnTuesday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnTuesday,
                false, repeatDays));

        MaterialButton btnWednesday = findViewById(R.id.btn_wednesday);
        btnWednesday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnWednesday,
                false, repeatDays));

        MaterialButton btnThursday = findViewById(R.id.btn_thursday);
        btnThursday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnThursday,
                false, repeatDays));

        MaterialButton btnFriday = findViewById(R.id.btn_friday);
        btnFriday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnFriday,
                false, repeatDays));

        MaterialButton btnSaturday = findViewById(R.id.btn_saturday);
        btnSaturday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnSaturday,
                false, repeatDays));

        MaterialButton btnSunday = findViewById(R.id.btn_sunday);
        btnSunday.setOnClickListener(new CheckableButtonClickListener(getApplication(), btnSunday,
                false, repeatDays));
    }

    private void initRepeatsDay() {
        repeatDays = new Hashtable<>();
        repeatDays.put(MONDAY, false);
        repeatDays.put(TUESDAY, false);
        repeatDays.put(WEDNESDAY, false);
        repeatDays.put(THURSDAY, false);
        repeatDays.put(FRIDAY, false);
        repeatDays.put(SATURDAY, false);
        repeatDays.put(SUNDAY, false);
    }

    public void setTime(String time) {
        timeEditText.setText(time);
    }

    public String getDateTime() {
        return timeEditText.getText().toString();
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getTagId() {
        return tagId;
    }

    public void enabledRepeat(boolean enabled) {
        buttonGroup.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }
}
