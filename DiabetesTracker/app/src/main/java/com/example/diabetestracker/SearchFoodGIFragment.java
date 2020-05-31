package com.example.diabetestracker;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.diabetestracker.entities.FoodAndType;
import com.example.diabetestracker.listeners.CancelOnClickListener;
import com.example.diabetestracker.listeners.DropdownItemClickListener;
import com.example.diabetestracker.viewmodels.FoodViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFoodGIFragment extends Fragment {
    private MaterialToolbar toolbar;
    private AutoCompleteTextView FoodNameAutoComplete;
    private TextView name_food_text;
    private TextView type_food_text;
    private TextView GI_text;
    private TextView advice_text;

    private FoodAndType foodandtype;

    public SearchFoodGIFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_food_gi, container, false);
        Application application = getActivity().getApplication();

        name_food_text = view.findViewById(R.id.name_food_textview);
        type_food_text = view.findViewById(R.id.type_food_textview);
        GI_text = view.findViewById(R.id.GI_textview);
        advice_text = view.findViewById(R.id.advice_textview);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new CancelOnClickListener(this));

        FoodNameAutoComplete = view.findViewById(R.id.name_food_autocompletetext);
        FoodNameAutoComplete.setOnItemClickListener(new DropdownItemClickListener(this));

        FoodViewModel foodviewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(FoodViewModel.class);

        final ArrayAdapter<FoodAndType> adapter = new ArrayAdapter<>(getContext(),
                R.layout.dropdown_menu_item);

        foodviewModel.getAllFood().observe(getViewLifecycleOwner(), new Observer<List<FoodAndType>>() {
            @Override
            public void onChanged(List<FoodAndType> foods) {
                adapter.addAll(foods);
                FoodNameAutoComplete.setAdapter(adapter);
            }
        });

        return view;
    }
    public void setName(String name) {
        name_food_text.setText(name);
    }
    public void setType(String type) {
        type_food_text.setText(type);
    }
    public void setGI(float gi) {
        GI_text.setText(String.valueOf(gi));
    }
    public void setAdvice(String advice) {
        advice_text.setText(advice);
    }
    public void setColorAdvice(String color) {
        advice_text.setTextColor(Color.parseColor(color));
    }
}
