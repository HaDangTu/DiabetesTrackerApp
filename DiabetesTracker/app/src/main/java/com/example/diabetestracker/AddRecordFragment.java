package com.example.diabetestracker;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.listeners.CancelOnClickListener;
import com.example.diabetestracker.listeners.DateIconOnCLickListener;
import com.example.diabetestracker.listeners.DropdownItemClickListener;
import com.example.diabetestracker.listeners.EditRecordMenuItemClickListener;
import com.example.diabetestracker.listeners.MenuItemAddRecordClickListener;
import com.example.diabetestracker.listeners.TimeIconOnClickListener;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.viewmodels.TagViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordFragment extends Fragment {

    private MaterialToolbar toolbar;

    private TextInputLayout glycemicInputLayout;
    private TextInputEditText glycemicEditText;

    private TextInputLayout dateInputLayout;
    private TextInputEditText dateEditText;

    private TextInputLayout timeInputLayout;
    private TextInputEditText timeEditText;

    private TextInputLayout tagAutoCompleteLayout;
    private AutoCompleteTextView tagAutoCompleteText;

    private TextInputEditText noteEditText;

    private BloodSugarRecord record;
    private int tagId;

    public AddRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_record, container, false);
        Application application = getActivity().getApplication();

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new CancelOnClickListener(this));
        toolbar.setOnMenuItemClickListener(new MenuItemAddRecordClickListener(this));

        glycemicInputLayout = view.findViewById(R.id.glycemic_index_text_layout);
        glycemicEditText = view.findViewById(R.id.glycemic_index_text);

        Date date = new Date();
        String time = DateTimeUtil.formatTime24(date);
        String dateStr = DateTimeUtil.formatDate(date);

        dateInputLayout = view.findViewById(R.id.date_input_layout);
        dateInputLayout.setEndIconOnClickListener(new DateIconOnCLickListener(this));
        dateEditText = view.findViewById(R.id.record_date_text);
        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.setOnClickListener(new DateIconOnCLickListener(this));
        dateEditText.setText(dateStr);


        timeInputLayout = view.findViewById(R.id.time_input_layout);
        timeInputLayout.setEndIconOnClickListener(new TimeIconOnClickListener(this));
        timeEditText = view.findViewById(R.id.time_record_text);
        timeEditText.setOnClickListener(new TimeIconOnClickListener(this));
        timeEditText.setInputType(InputType.TYPE_NULL);
        timeEditText.setText(time);

        noteEditText = view.findViewById(R.id.note);

        tagAutoCompleteLayout = view.findViewById(R.id.tag_autocomplete_layout);
        tagAutoCompleteText = view.findViewById(R.id.tag_autocompletetext);
        tagAutoCompleteText.setInputType(InputType.TYPE_NULL);
        tagAutoCompleteText.setOnItemClickListener(new DropdownItemClickListener(this));
        TagViewModel tagViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(TagViewModel.class);

        final ArrayAdapter<Tag> tagAdapter = new ArrayAdapter<>(getContext(),
                R.layout.dropdown_menu_item);

        tagViewModel.getAllTag().observe(requireActivity(), new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                //load data to autocomplete text view
                tagAdapter.addAll(tags);
                tagAutoCompleteText.setAdapter(tagAdapter);
            }
        });
        return view;
    }

    public boolean hasError() {
        boolean hasError = false;

        if (glycemicEditText.getText().toString().equals("")) {
            glycemicInputLayout.setError(getResources().getString(R.string.glycemic_error));
            hasError = true;
        }

        if (dateEditText.getText().toString().equals("")) {
            dateInputLayout.setError(getResources().getString(R.string.date_error));
            hasError = true;
        }

        if (timeEditText.getText().toString().equals("")) {
            System.out.println(timeInputLayout.isErrorEnabled());
            timeInputLayout.setError(getResources().getString(R.string.time_error));
            hasError = true;
        }

        if (tagAutoCompleteText.getText().toString().equals("")) {
            tagAutoCompleteLayout.setError(getResources().getString(R.string.tag_error));
            hasError = true;
        }

        return hasError;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getTagId() {
        return tagId;
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
        if (noteEditText.getText() == null) {
            return "";
        }
        return noteEditText.getText().toString();
    }

    public void setTime(String time) {
        timeEditText.setText(time);
    }

    public void setDate(String date) {
        dateEditText.setText(date);
    }
}
