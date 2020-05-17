package com.example.diabetestracker.listeners;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.diabetestracker.DetailRecordFragment;
import com.example.diabetestracker.HomeFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.RecordRecyclerAdapter;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.viewmodels.RecordViewModel;

public class RecordItemClickListener implements RecordRecyclerAdapter.OnCardViewClickListener {
    private HomeFragment fragment;

    public RecordItemClickListener(HomeFragment fragment) {
        this.fragment = fragment;
    }
    @Override
    public void onClick(RecordTag selectedRecord) {
        RecordViewModel viewModel = new ViewModelProvider(fragment.requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getActivity().getApplication()))
                .get(RecordViewModel.class);

        viewModel.selectRecord(selectedRecord);

        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, new DetailRecordFragment())
                .addToBackStack("Edit record")
                .commit();
    }
}
