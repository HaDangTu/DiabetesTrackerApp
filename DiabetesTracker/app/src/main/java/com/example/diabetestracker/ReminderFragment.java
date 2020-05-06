package com.example.diabetestracker;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diabetestracker.entities.ReminderTag;
import com.example.diabetestracker.listeners.FabAddReminderClickListener;
import com.example.diabetestracker.viewmodels.ReminderViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment {

    private TextView warningTextView;
    private RecyclerView recyclerView;
    private ReminderRecyclerAdapter adapter;
    private FloatingActionButton fabAddReminder;

    public ReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        warningTextView = view.findViewById(R.id.warning_remind_text);
        recyclerView = view.findViewById(R.id.reminder_recycler_view);

        ReminderViewModel viewModel = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ReminderViewModel.class);
        adapter = new ReminderRecyclerAdapter(getContext());

        viewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<ReminderTag>>() {
            @Override
            public void onChanged(List<ReminderTag> reminderTags) {
                if (reminderTags.size() > 0) {
                    adapter.setReminders(reminderTags);
                    warningTextView.setVisibility(View.GONE);
                }
                else {
                    warningTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        fabAddReminder = view.findViewById(R.id.new_reminder_fab);
        fabAddReminder.setOnClickListener(new FabAddReminderClickListener());
        return view;
    }

}
