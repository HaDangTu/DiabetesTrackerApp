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
import com.example.diabetestracker.listeners.EatingAdvicePageChanged;
import com.example.diabetestracker.viewmodels.AdviceViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EatingAdvicesFragment extends Fragment {

    private ViewPager2 viewPager;
    private MaterialTextView pageNumTextView;
    private EatingAdviceRecyclerAdapter adapter;

    private EatingAdvicePageChanged callback;

    private TabLayout tabLayout;

    public EatingAdvicesFragment() {
        // Required empty public constructor
    }

    public EatingAdvicesFragment(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eating_advices, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        adapter = new EatingAdviceRecyclerAdapter(getContext());
        viewPager.setAdapter(adapter);

        callback = new EatingAdvicePageChanged(this);
        viewPager.registerOnPageChangeCallback(callback);

        pageNumTextView = view.findViewById(R.id.page_number_text_view);
        AdviceViewModel viewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(AdviceViewModel.class);

        viewModel.getEatingAdvices().observe(requireActivity(),
                new Observer<List<AdviceAndType>>() {
                    @Override
                    public void onChanged(List<AdviceAndType> adviceAndTypes) {
                        adapter.setAdvices(adviceAndTypes);
                    }
                });


        return view;
    }

    public void setPageNum(int num, int maxPage) {
        pageNumTextView.setText(getString(R.string.page_num, num, maxPage));
    }

    public int getNumOfPage() {
        return adapter.getItemCount();
    }

    public void setSelectedTab() {
        tabLayout.selectTab(tabLayout.getTabAt(0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPager.unregisterOnPageChangeCallback(callback);
    }
}
