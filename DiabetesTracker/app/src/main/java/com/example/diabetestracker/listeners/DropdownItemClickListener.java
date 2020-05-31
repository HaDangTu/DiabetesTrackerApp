package com.example.diabetestracker.listeners;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.AddReminderFragment;
import com.example.diabetestracker.DetailReminderFragment;
import com.example.diabetestracker.DetailRecordFragment;
import com.example.diabetestracker.R;
import com.example.diabetestracker.SearchFoodGIFragment;
import com.example.diabetestracker.entities.FoodAndType;
import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.entities.TagScale;

public class DropdownItemClickListener implements AdapterView.OnItemClickListener {
    private AppCompatActivity activity;
    private Fragment fragment;

    @Deprecated
    public DropdownItemClickListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    public DropdownItemClickListener(Fragment fragment) {
        this.fragment = fragment;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (fragment.getClass() == AddRecordFragment.class) {
            AddRecordFragment addRecordFragment = (AddRecordFragment) fragment;
            TagScale tagScale = (TagScale) parent.getItemAtPosition(position);
            addRecordFragment.setTagScale(tagScale);
        }
        else if (fragment.getClass() == DetailRecordFragment.class) {
            DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
            TagScale tagScale = (TagScale) parent.getItemAtPosition(position);
            detailRecordFragment.setTagScale(tagScale);
        }
        else if (fragment.getClass() == AddReminderFragment.class) {
            AddReminderFragment addReminderFragment = (AddReminderFragment) fragment;
            String type = (String) parent.getItemAtPosition(position);
            addReminderFragment.setType(type);
        }
        else if (fragment.getClass() == DetailReminderFragment.class) {
            DetailReminderFragment detailReminderFragment = (DetailReminderFragment) fragment;
            String type = (String) parent.getItemAtPosition(position);
            detailReminderFragment.setType(type);
        }
        else if (fragment.getClass() == SearchFoodGIFragment.class) {
            SearchFoodGIFragment searchfoodgiActivity = (SearchFoodGIFragment) fragment;
            FoodAndType foodandtype = (FoodAndType) parent.getItemAtPosition(position);
            searchfoodgiActivity.setName(foodandtype.getFood().getName());
            searchfoodgiActivity.setType(foodandtype.getFoodType().getName());
            int GI = 0;
            GI=foodandtype.getFood().getGlycemicIndex();
            searchfoodgiActivity.setGI(GI);
            Resources res = searchfoodgiActivity.getResources();
            if(GI<55) {
                searchfoodgiActivity.setAdvice(res.getString(R.string.safe_GI_advice));
                searchfoodgiActivity.setColorAdvice(res.getString(R.color.colorSafe));
            }
            else if(GI>=75) {
                searchfoodgiActivity.setAdvice(res.getString(R.string.high_GI_advice));
                searchfoodgiActivity.setColorAdvice(res.getString(R.color.colorHigh));
            }
            else {
                searchfoodgiActivity.setAdvice(res.getString(R.string.normal_GI_advice));
                searchfoodgiActivity.setColorAdvice(res.getString(R.color.colorLow));
            }
        }
    }
}
