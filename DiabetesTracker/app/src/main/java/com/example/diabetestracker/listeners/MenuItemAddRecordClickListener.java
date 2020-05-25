package com.example.diabetestracker.listeners;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.MenuItem;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.diabetestracker.AddRecordFragment;
import com.example.diabetestracker.MainActivity;
import com.example.diabetestracker.R;
import com.example.diabetestracker.RecordRecyclerAdapter;
import com.example.diabetestracker.SettingsFragment;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.Scale;
import com.example.diabetestracker.entities.Tag;
import com.example.diabetestracker.entities.TagScale;
import com.example.diabetestracker.repository.RecordRepository;
import com.example.diabetestracker.util.DateTimeUtil;
import com.example.diabetestracker.util.UnitConverter;
import com.example.diabetestracker.viewmodels.AdviceViewModel;


public class MenuItemAddRecordClickListener extends BaseMenuItemClickListener {
    private RecordRepository repository;

    public MenuItemAddRecordClickListener(AddRecordFragment fragment)
    {
        super(fragment);
        repository = new RecordRepository(fragment.getActivity().getApplication());
        this.fragment = fragment;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        AddRecordFragment addRecordFragment = (AddRecordFragment) fragment;
        final Context context = fragment.getContext();
        int id = item.getItemId();
        if (id == R.id.item_save) {
            if (!addRecordFragment.hasError()) {
                BloodSugarRecord record = new BloodSugarRecord();
                TagScale tagScale = ((AddRecordFragment) fragment).getTagScale();
                Tag tag = tagScale.getTag();
                Scale scale = tagScale.getScale();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, RecordRecyclerAdapter.MMOL_L);

                float glycemicIndex = addRecordFragment.getGlycemicIndex();
//                if (unit.equals(RecordRecyclerAdapter.MG_DL)) {//IMPORTANT chuyển mg/dL thành mmol/L
//                    glycemicIndex = UnitConverter.mg_To_mmol(glycemicIndex);
//                }

                record.setBloodSugarLevel(glycemicIndex);
                String recordDate = DateTimeUtil.convertDateString(addRecordFragment.getDateTimeRecord());

                record.setRecordDate(recordDate);
                record.setTagId(tag.getId());
                record.setNote(addRecordFragment.getNote());

                float min = scale.getMin();
                float max = scale.getMax();
                float index = record.getBloodSugarLevel();



                AdviceViewModel viewModel = new ViewModelProvider(fragment.requireActivity(),
                        ViewModelProvider.AndroidViewModelFactory
                                .getInstance(fragment.getActivity().getApplication()))
                        .get(AdviceViewModel.class);
                //Thông báo khi chỉ số đường huyết quá cao hoặc quá thấp
                if (index >= max) {
                    viewModel.getHighWarning().observe(fragment.requireActivity(),
                            new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    Notification notification = new NotificationCompat.Builder(context,
                                            MainActivity.NOTIFICATION_CHANEL_ID)
                                            .setSmallIcon(R.drawable.ic_diabetes)
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setContentTitle("Warning")
                                            .setContentText(s)
                                            .setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText(s))
                                            .setAutoCancel(true)
                                            .build();

                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                                    notificationManager.notify(2, notification);
                                }
                            });
                }
                else if (index <= min) {
                    viewModel.getLowWarning().observe(fragment.requireActivity(),
                            new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    Notification notification = new NotificationCompat.Builder(context,
                                            MainActivity.NOTIFICATION_CHANEL_ID)
                                            .setSmallIcon(R.drawable.ic_diabetes)
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setContentTitle("Warning")
                                            .setContentText(s)
                                            .setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText(s))
                                            .setAutoCancel(true)
                                            .build();

                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                                    notificationManager.notify(2, notification);
                                }
                            });
                }
                repository.insert(record);

                FragmentManager fragmentManager = fragment.getFragmentManager();
                fragmentManager.popBackStack();
            }
            return true;
        }
        return false;
    }
}
