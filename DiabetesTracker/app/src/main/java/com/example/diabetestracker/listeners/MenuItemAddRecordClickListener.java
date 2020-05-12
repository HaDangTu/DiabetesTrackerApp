package com.example.diabetestracker.listeners;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.diabetestracker.AddRecordActivity;
import com.example.diabetestracker.AddReminderActivity;
import com.example.diabetestracker.EditRecordActivity;
import com.example.diabetestracker.R;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.repository.RecordRepository;
import com.example.diabetestracker.util.DateTimeUtil;

import androidx.fragment.app.FragmentManager;


public class MenuItemAddRecordClickListener extends BaseMenuItemClickListener {
    private RecordRepository repository;

    public MenuItemAddRecordClickListener(AddRecordActivity activity)
    {
        super(activity);
        repository = new RecordRepository(activity.getApplication());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.item_save) {
            AddRecordActivity addActivity = (AddRecordActivity) activity;
            if(addActivity.GetGlycemicIndex() == null)
            {
                Context context = addActivity.getApplicationContext();
                CharSequence text = "Chỉ Số Đường huyết rỗng";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return false;
            }
            else if(addActivity.getDateRecord().equals(""))
            {
                Context context = addActivity.getApplicationContext();
                CharSequence text = "Ngày Rỗng";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return false;
            }
            else if(addActivity.getTimeRecord().equals(""))
            {
                Context context = addActivity.getApplicationContext();
                CharSequence text = "Thời gian rỗng";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return false;
            }
            else if(addActivity.getTagId() == 0 )
            {
                Context context = addActivity.getApplicationContext();
                CharSequence text = "Tag rỗng";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return false;
            }
            else
            {
                BloodSugarRecord record=new BloodSugarRecord();
                record.setBloodSugarLevel(addActivity.GetGlycemicIndex());
                String recordDateTime = DateTimeUtil.convertDateString(addActivity.getDateTimeRecord());
                record.setRecordDate(recordDateTime);
                record.setNote(addActivity.GetNote());
                record.setTagId(addActivity.getTagId());
                repository.insert(record);
                activity.onBackPressed();
                return true;
            }
        }
        return false;
    }
}
