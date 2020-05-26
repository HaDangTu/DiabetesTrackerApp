package com.example.diabetestracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.RecordTag;
import com.example.diabetestracker.entities.Scale;
import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.entities.TagScale;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.util.UnitConverter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class RecordRecyclerAdapter extends RecyclerView.Adapter {
    private static final int TYPE_DATE = 0;
    private static final int TYPE_RECORD = 1;

    private Context context;
    private List<RecordTag> records;

    private OnCardViewClickListener listener;

    public static final String MMOL_L = "mmol/L";
    public static final String MG_DL = "mg/dL";

    public interface OnCardViewClickListener {
        void onClick(RecordTag recordTag);
    }

    public RecordRecyclerAdapter(Context context){
        this.context = context;
    }

    public RecordRecyclerAdapter(Context context, List<RecordTag> records){
        this.context = context;
        this.records = records;
    }

    public void setRecords(List<RecordTag> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    public void setListener(OnCardViewClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_RECORD)
        {
            View itemView = LayoutInflater.from(context).inflate(R.layout.record_item, parent, false);
            return new RecordViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(context).inflate(R.layout.date_item, parent, false);
            return new DateViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecordTag recordTag = records.get(position);
        BloodSugarRecord record = recordTag.getRecord();
        TagScale tagScale = recordTag.getTagScale();
        Scale scale = tagScale.getScale();
        Tag tag = tagScale.getTag();

        float max = scale.getMax();
        float min = scale.getMin();
        float glycemicIndexMMol = record.getGlycemicIndexMMol();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, MMOL_L);

        String glycemicText = String.valueOf(glycemicIndexMMol);
        if (unit.equals(MG_DL)) {
            int glycemicIndexMg = record.getGlycemicIndexMg();
            glycemicText = String.valueOf(glycemicIndexMg);
        }


        if (holder.getClass() == RecordViewHolder.class) { //Record item
            RecordViewHolder viewHolder = (RecordViewHolder)holder;

            viewHolder.setUnitText(unit);

            if (glycemicIndexMMol < min) {
                viewHolder.setBloodSugarLevelText(glycemicText,
                        context.getResources().getColor(R.color.colorLow));
            }
            else if (glycemicIndexMMol < max) {
                viewHolder.setBloodSugarLevelText(glycemicText,
                        context.getResources().getColor(R.color.colorSafe));
            }
            else {
                viewHolder.setBloodSugarLevelText(glycemicText,
                        context.getResources().getColor(R.color.colorHigh));
            }

            try {
                Date recordDate = DateTimeUtil.parse(record.getRecordDate());
                viewHolder.setRecordTimeText(DateTimeUtil.formatTime24(recordDate));
                viewHolder.setSessionNameText(tag.getName());
            }
            catch (ParseException e){
                e.printStackTrace();
            }

        }
        else { //Date item
            DateViewHolder viewHolder = (DateViewHolder) holder;
            viewHolder.setUnitText(unit);

            if (glycemicIndexMMol < min) {
                viewHolder.setBloodSugarLevelText(glycemicText,
                        context.getResources().getColor(R.color.colorLow));
            }
            else if (glycemicIndexMMol < max) {
                viewHolder.setBloodSugarLevelText(glycemicText,
                        context.getResources().getColor(R.color.colorSafe));
            }
            else {
                viewHolder.setBloodSugarLevelText(glycemicText,
                        context.getResources().getColor(R.color.colorHigh));
            }


            try {
                Date recordDate = DateTimeUtil.parse(record.getRecordDate());
                viewHolder.setRecordTimeText(DateTimeUtil.formatTime24(recordDate));
                viewHolder.setDateText(DateTimeUtil.formatDate(recordDate));
                viewHolder.setSessionNameText(tag.getName());
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (records != null)
            return records.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (!isDuplicate(position))
            return TYPE_DATE;
        else
            return TYPE_RECORD;
    }

    private boolean isDuplicate(int position){
        if (position == 0)
            return false;
        else {
            BloodSugarRecord record1 = records.get(position).getRecord();
            BloodSugarRecord record2  = records.get(position - 1).getRecord();

            try {
                Date date1 = DateTimeUtil.parse(record1.getRecordDate());
                Date date2 = DateTimeUtil.parse(record2.getRecordDate());

                return DateTimeUtil.compareDatesWithoutTime(date1, date2) == 0;
            }
            catch (ParseException e){
                e.printStackTrace();
                return false;
            }
        }
    }

    private class DateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MaterialTextView recordDateTextView;
        private MaterialTextView bloodSugarLevelTextView;
        private MaterialTextView recordTimeTextView;
        private MaterialTextView tagNameTextView;
        private MaterialTextView unitTextView;

        private MaterialCardView cardView;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.record_card_view);
            recordDateTextView = itemView.findViewById(R.id.record_date_text);
            bloodSugarLevelTextView = itemView.findViewById(R.id.blood_sugar_level_text);
            recordTimeTextView = itemView.findViewById(R.id.record_time_text);
            tagNameTextView = itemView.findViewById(R.id.tag_name_text);
            unitTextView = itemView.findViewById(R.id.unit_text_view);

            cardView.setOnClickListener(this);
        }

        public void setDateText(String date){
            recordDateTextView.setText(date);
        }

        public void setBloodSugarLevelText(String level, int color){
            bloodSugarLevelTextView.setText(level);
            bloodSugarLevelTextView.setTextColor(color);
        }

        public void setRecordTimeText(String time){
            recordTimeTextView.setText(time);
        }

        public void setSessionNameText(String name){
            tagNameTextView.setText(name);
        }

        public void setUnitText(String unit) {
            unitTextView.setText(unit);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(records.get(getAdapterPosition()));
        }
    }

    private class RecordViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        private MaterialCardView cardView;
        private MaterialTextView bloodSugarLevelTextView;
        private MaterialTextView recordTimeTextView;
        private MaterialTextView tagNameTextView;
        private MaterialTextView unitTextView;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.record_card_view);
            bloodSugarLevelTextView = itemView.findViewById(R.id.blood_sugar_level_text);
            recordTimeTextView = itemView.findViewById(R.id.record_time_text);
            tagNameTextView = itemView.findViewById(R.id.tag_name_text);
            unitTextView = itemView.findViewById(R.id.unit_text_view);
            cardView.setOnClickListener(this);
        }

        public void setBloodSugarLevelText(String level, int color){
            bloodSugarLevelTextView.setText(level);
            bloodSugarLevelTextView.setTextColor(color);
        }

        public void setRecordTimeText(String time){
            recordTimeTextView.setText(time);
        }

        public void setSessionNameText(String name){
            tagNameTextView.setText(name);
        }

        public void setUnitText(String unit) {
            unitTextView.setText(unit);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(records.get(getAdapterPosition()));
        }
    }
}
