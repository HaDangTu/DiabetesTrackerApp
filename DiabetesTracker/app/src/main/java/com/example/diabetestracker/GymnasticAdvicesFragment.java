package com.example.diabetestracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diabetestracker.entities.AdviceAndType;
import com.example.diabetestracker.listeners.GymnasticAdvicePageChanged;
import com.example.diabetestracker.viewmodels.AdviceViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GymnasticAdvicesFragment extends Fragment {

    private ViewPager2 viewPager;
    private GymnasticAdviceRecyclerAdapter adapter;
    private MaterialTextView pageNumberTextView;
    private GymnasticAdvicePageChanged callBack;

    private TabLayout tabLayout;

    public GymnasticAdvicesFragment() {
        // Required empty public constructor
    }


    public GymnasticAdvicesFragment(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gymnastic_advices, container, false);

        viewPager = view.findViewById(R.id.view_pager_gymnastic);

        pageNumberTextView = view.findViewById(R.id.page_number_text_view);
        adapter = new GymnasticAdviceRecyclerAdapter(getContext());
        viewPager.setAdapter(adapter);

        callBack = new GymnasticAdvicePageChanged(this);
        viewPager.registerOnPageChangeCallback(callBack);


        AdviceViewModel viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(AdviceViewModel.class);

        viewModel.getGymnasticAdvices().observe(requireActivity(), new Observer<List<AdviceAndType>>() {
            @Override
            public void onChanged(List<AdviceAndType> adviceAndTypes) {
                adapter.setAdvices(adviceAndTypes);
            }
        });

        return view;
    }

    public void setPageNum(int num, int maxPage) {
        pageNumberTextView.setText(getString(R.string.page_num, num, maxPage));
    }

    public int getNumOfPage() {
        return adapter.getItemCount();
    }

    public void setSelectedTab() {
        tabLayout.selectTab(tabLayout.getTabAt(1));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPager.unregisterOnPageChangeCallback(callBack);
    }
}
