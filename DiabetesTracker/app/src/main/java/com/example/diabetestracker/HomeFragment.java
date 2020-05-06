package com.example.diabetestracker;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.listeners.CardViewClickListener;
import com.example.diabetestracker.repository.RecordRepository;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.viewmodels.RecordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecordRecyclerAdapter adapter;
    private RecordRepository recordRepository;
    private RecordViewModel viewModel;
    private FloatingActionButton fabAddRecord;

    private static HomeFragment __instance = null;

    public HomeFragment() {
        // Required empty public constructor

    }

    public static HomeFragment getInstance(){
        if (__instance == null) __instance = new HomeFragment();
        return __instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.record_recycler_view);

        viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(RecordViewModel.class);
        viewModel.getAllRecords().observe(getViewLifecycleOwner(), new Observer<List<RecordTag>>() {
            @Override
            public void onChanged(List<RecordTag> recordTags) {
                Collections.sort(recordTags, new DateTimeUtil.DateComparator());
                adapter.setRecords(recordTags);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecordRecyclerAdapter(getContext());
        adapter.setListener(new CardViewClickListener(getViewModelStore(), getActivity().getApplication()));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        fabAddRecord = view.findViewById(R.id.new_record_fab);
        return view;
    }

    public RecordRecyclerAdapter getAdapter() {
        return adapter;
    }

    public RecordViewModel getViewModel() {
        return viewModel;
    }
}
