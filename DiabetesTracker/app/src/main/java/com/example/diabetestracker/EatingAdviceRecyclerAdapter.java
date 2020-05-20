package com.example.diabetestracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetestracker.entities.Advice;
import com.example.diabetestracker.entities.AdviceAndType;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class EatingAdviceRecyclerAdapter extends RecyclerView.Adapter<EatingAdviceRecyclerAdapter.AdviceViewHolder> {
    private Context context;
    private List<AdviceAndType> advices;

    public EatingAdviceRecyclerAdapter(Context context) {
        this.context = context;
    }

    public EatingAdviceRecyclerAdapter(Context context, List<AdviceAndType> advices) {
        this.context = context;
        this.advices = advices;
    }

    public void setAdvices(List<AdviceAndType> advices) {
        this.advices = advices;
        notifyDataSetChanged();
    }

    public List<AdviceAndType> getAdvices() {
        return advices;
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

    public static class AdviceViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView adviceNumTextView;
        private MaterialTextView adviceTitleTextView;
        private MaterialTextView adviceDescriptionTextView;

        public AdviceViewHolder(@NonNull View itemView) {
            super(itemView);

            adviceNumTextView = itemView.findViewById(R.id.advice_num_text);
            adviceTitleTextView = itemView.findViewById(R.id.advice_title);
            adviceDescriptionTextView = itemView.findViewById(R.id.advice_description);
        }

        public void setNumText(String numText) {
            adviceNumTextView.setText(numText);
        }

        public void setTitle(String title) {
            adviceTitleTextView.setText(title);
        }

        public void setDescription(String description) {
            adviceDescriptionTextView.setText(description);
        }
    }
}
