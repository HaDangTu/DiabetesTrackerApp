package com.example.diabetestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.animation.FloatArrayEvaluator;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.entities.TagScale;
import com.example.diabetestracker.listeners.CancelOnClickListener;
import com.example.diabetestracker.listeners.DateIconOnCLickListener;
import com.example.diabetestracker.listeners.DropdownItemClickListener;
import com.example.diabetestracker.listeners.MenuItemAddRecordClickListener;
import com.example.diabetestracker.listeners.MenuItemAddReminderClickListener;
import com.example.diabetestracker.listeners.TimeIconOnClickListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.viewmodels.RecordViewModel;
import com.example.diabetestracker.viewmodels.TagViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class AddRecordActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private AutoCompleteTextView tagAutoComplete;
    private TextInputEditText record_date_text;
    private TextInputEditText record_time_text;
    private TextInputEditText note;
    private TextInputEditText glycemic_index;

    private int tagId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        glycemic_index = findViewById(R.id.glycemic_index_text);
        record_date_text = findViewById(R.id.record_date_text);
        record_date_text.setOnClickListener(new DateIconOnCLickListener(this));
        record_time_text = findViewById(R.id.time_record_text);
        record_time_text.setOnClickListener(new TimeIconOnClickListener(this));

        note = findViewById(R.id.note);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new CancelOnClickListener(this));
        toolbar.setOnMenuItemClickListener(new MenuItemAddRecordClickListener(this));

        tagAutoComplete = findViewById(R.id.tag_autocompletetext);
        tagAutoComplete.setInputType(InputType.TYPE_NULL);
        tagAutoComplete.setOnItemClickListener(new DropdownItemClickListener(this));

        TagViewModel tagviewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(TagViewModel.class);

        final ArrayAdapter<Tag> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.dropdown_menu_item);

        tagviewModel.getAllTag().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                adapter.addAll(tags);
                tagAutoComplete.setAdapter(adapter);
            }
        });
    }
    public Float GetGlycemicIndex() {
        String index = glycemic_index.getText().toString();
        if(!index.equals(""))
            return Float.parseFloat(index);
        return null;
    }
    public String getDateTimeRecord() {
        String date = record_date_text.getText().toString();
        String time = record_time_text.getText().toString();

        return date + " " + time;
    }

    public String getDateRecord() {
        String date = record_date_text.getText().toString();
        return date;
    }
    public String getTimeRecord() {
        String time = record_time_text.getText().toString();
        return time;
    }

    public void setTime(String time) {
        record_time_text.setText(time);
    }
    public void setDate(String date) {
        record_date_text.setText(date);
    }

    public String GetNote() {
        return note.getText().toString();
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getTagId() {
        return tagId;
    }
}
