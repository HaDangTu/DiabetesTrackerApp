package com.example.diabetestracker.listeners;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.diabetestracker.RecordRecyclerAdapter;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.viewmodels.RecordViewModel;

public class CardViewClickListener implements RecordRecyclerAdapter.OnCardViewClickListener {
    private ViewModelStore viewModelStore;
    private Application application;

    public CardViewClickListener(ViewModelStore viewModelStore, Application application) {
        this.viewModelStore = viewModelStore;
        this.application = application;
    }
    @Override
    public void onClick(RecordTag selectedRecord) {
        RecordViewModel viewModel = new ViewModelProvider(viewModelStore,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(RecordViewModel.class);

        viewModel.selectRecord(selectedRecord);

        Toast.makeText(application.getApplicationContext(), "Item :" +  selectedRecord.getRecord().toString() ,
                Toast.LENGTH_LONG).show();
        /**
         * TODO
         *  start edit activity
         */
    }
}
