package com.example.diabetestracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.diabetestracker.EatingAdviceRecyclerAdapter.AdviceViewHolder;

import com.example.diabetestracker.entities.Advice;
import com.example.diabetestracker.entities.AdviceAndType;

import java.util.List;

public class GymnasticAdviceRecyclerAdapter extends RecyclerView.Adapter<AdviceViewHolder> {
    private Context context;
    private List<AdviceAndType> advices;

    public GymnasticAdviceRecyclerAdapter(Context context) {
        this.context = context;
    }

    public GymnasticAdviceRecyclerAdapter(Context context, List<AdviceAndType> advices) {
        this.context = context;
        this.advices =  advices;
    }

    public void setAdvices(List<AdviceAndType> advices) {
        this.advices = advices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.slide, parent, false);
        return new AdviceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdviceViewHolder holder, int position) {
        Advice advice = advices.get(position).getAdvice();

        holder.setNumText(context.getString(R.string.advice_num, (position + 1)));
        holder.setTitle(advice.getTitle());
        holder.setDescription(context.getString(R.string.activity_advice_description,
                advice.getDescription()));
    }

    @Override
    public int getItemCount() {
        if (advices != null)
            return advices.size();
        return 0;
    }

}
