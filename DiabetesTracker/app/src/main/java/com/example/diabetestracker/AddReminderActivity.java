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

import com.example.diabetestracker.listeners.CancelOnClickListener;
import com.example.diabetestracker.listeners.TimeIconOnClickListener;
import com.example.diabetestracker.viewmodels.TagViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddReminderActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputEditText timeEditText;
    private TextInputLayout timeInputLayout;
    private CheckBox notificationRepeatCheckBox;
    private LinearLayout buttonGroup;

    private AutoCompleteTextView tagAutoComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        toolbar = findViewById(R.id.toolbar);
//        toolbar.setOnMenuItemClickListener(new MenuItemDoneClickListener());
        toolbar.setNavigationOnClickListener(new CancelOnClickListener(this));

        timeEditText = findViewById(R.id.time_remind_text);
        timeEditText.setInputType(InputType.TYPE_NULL);
        timeEditText.setOnClickListener(new TimeIconOnClickListener(this));
        timeInputLayout = findViewById(R.id.time_input_layout);
        timeInputLayout.setEndIconOnClickListener(new TimeIconOnClickListener(this));

        tagAutoComplete = findViewById(R.id.tag_autocompletetext);
        tagAutoComplete.setInputType(InputType.TYPE_NULL);

        TagViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(TagViewModel.class);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.dropdown_menu_item);




        notificationRepeatCheckBox = findViewById(R.id.repeat_checkbox);
//        notificationRepeatCheckBox.setOnCheckedChangeListener();
        buttonGroup = findViewById(R.id.btn_days_group);
    }

    public void setTime(String time) {
        timeEditText.setText(time);
    }

    public String getDateTime() {
        return timeEditText.getText().toString();
    }

    public void enabledRepeat(boolean enabled) {
        buttonGroup.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }
}
