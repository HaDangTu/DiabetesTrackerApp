package com.example.diabetestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.entities.TagScale;
import com.example.diabetestracker.listeners.CancelOnClickListener;
import com.example.diabetestracker.listeners.DateIconOnCLickListener;
import com.example.diabetestracker.listeners.DropdownItemClickListener;
import com.example.diabetestracker.listeners.EditMenuItemClickListener;
import com.example.diabetestracker.listeners.TimeIconOnClickListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.viewmodels.RecordViewModel;
import com.example.diabetestracker.viewmodels.TagViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * TODO create AddNewRecordActivity
 */

public class EditRecordActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private TextInputEditText glycemicEditText;

    private TextInputLayout dateInputLayout;
    private TextInputEditText dateEditText;

    private TextInputLayout timeInputLayout;
    private TextInputEditText timeEditText;

    private AutoCompleteTextView tagAutoCompleteText;

    private TextInputEditText noteEditText;

    private int recordId;
    private int tagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new CancelOnClickListener(this));
        toolbar.setOnMenuItemClickListener(new EditMenuItemClickListener(this));

        glycemicEditText = findViewById(R.id.glycemic_index_text);

        dateInputLayout = findViewById(R.id.date_input_layout);
        dateInputLayout.setEndIconOnClickListener(new DateIconOnCLickListener(this));
        dateInputLayout.setOnClickListener(new DateIconOnCLickListener(this));
        dateEditText = findViewById(R.id.record_date_text);

        timeInputLayout = findViewById(R.id.time_input_layout);
        timeInputLayout.setEndIconOnClickListener(new TimeIconOnClickListener(this));
        timeInputLayout.setOnClickListener(new TimeIconOnClickListener(this));
        timeEditText = findViewById(R.id.time_record_text);

        noteEditText = findViewById(R.id.note);

        tagAutoCompleteText = findViewById(R.id.tag_autocompletetext);
        tagAutoCompleteText.setInputType(InputType.TYPE_NULL);
        tagAutoCompleteText.setOnItemClickListener(new DropdownItemClickListener(this));
        TagViewModel tagViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(TagViewModel.class);

        final ArrayAdapter<Tag> tagAdapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.dropdown_menu_item);

        tagViewModel.getAllTag().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                tagAdapter.addAll(tags);
                tagAutoCompleteText.setAdapter(tagAdapter);
            }
        });

        HomeFragment fragment = HomeFragment.getInstance();
        RecordViewModel viewModel = new ViewModelProvider(fragment.getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(RecordViewModel.class);

        viewModel.getSelectedRecord().observe(this, new Observer<RecordTag>() {
            @Override
            public void onChanged(RecordTag recordTag) {
                BloodSugarRecord record = recordTag.getRecord();
                TagScale tagScale = recordTag.getTagScale();
                Tag tag = tagScale.getTag();
                recordId = record.getId();
                tagId = tag.getId();

                glycemicEditText.setText(String.valueOf(record.getBloodSugarLevel()));

                try {
                    Date date = DateTimeUtil.parse(record.getRecordDate());

                    dateEditText.setText(DateTimeUtil.formatDate(date));
                    timeEditText.setText(DateTimeUtil.formatTime(date));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                tagAutoCompleteText.setText(tag.getName(), false);
                noteEditText.setText(record.getNote());
            }
        });
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getTagId() {
        return tagId;
    }

    public int getRecordId() {
        return  recordId;
    }

    public float getGlycemicIndex() {
        return Float.parseFloat(glycemicEditText.getText().toString());
    }

    public String getDateTimeRecord() {
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();

        return date + " " + time;
    }

    public String getNote() {
        return noteEditText.getText().toString();
    }

    public void setTime(String time) {
        timeEditText.setText(time);
    }

    public void setDate(String date) {
        dateEditText.setText(date);
    }
}
