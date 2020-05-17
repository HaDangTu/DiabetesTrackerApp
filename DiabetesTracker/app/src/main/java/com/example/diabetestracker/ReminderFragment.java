package com.example.diabetestracker;


import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diabetestracker.entities.ReminderAndInfo;
import com.example.diabetestracker.listeners.FabAddReminderClickListener;
import com.example.diabetestracker.listeners.NotificationButtonClickListener;
import com.example.diabetestracker.listeners.ReminderItemClickListener;
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

        Application application = getActivity().getApplication();

        ReminderViewModel viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(ReminderViewModel.class);
        adapter = new ReminderRecyclerAdapter(getContext());

        viewModel.getAll().observe(requireActivity(), new Observer<List<ReminderAndInfo>>() {
            @Override
            public void onChanged(List<ReminderAndInfo> reminderAndInfos) {
                adapter.setReminders(reminderAndInfos);

                if (reminderAndInfos.size() < 1) {
                    warningTextView.setVisibility(View.VISIBLE);
                }
                else
                    warningTextView.setVisibility(View.INVISIBLE);
            }
        });
        adapter.setItemClickListener(new ReminderItemClickListener(this));
        adapter.setBtnClickListener(new NotificationButtonClickListener(application));

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        fabAddReminder = view.findViewById(R.id.new_reminder_fab);
        fabAddReminder.setOnClickListener(new FabAddReminderClickListener(getActivity().getSupportFragmentManager()));


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
