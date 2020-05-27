package com.example.diabetestracker.listeners;

import android.app.Notification;
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
import com.example.diabetestracker.TimePickerDialogFragment;
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
                String time = sharedPreferences.getString(SettingsFragment.TIME_KEY, TimePickerDialogFragment.TIME_24);


                float glycemicIndex = addRecordFragment.getGlycemicIndex();

                //Chuyển vể đơn vị mg/dL để tiện quản lý
                if (unit.equals(RecordRecyclerAdapter.MMOL_L))
                    glycemicIndex = UnitConverter.mmol_To_mg(glycemicIndex);

                record.setGlycemicIndex((int) glycemicIndex);

                String recordDate = addRecordFragment.getDateTimeRecord();

                //Convert lại thành kiểu yyyy-MM-dd HH:mm:ss để dễ xử lý
                if (time.equals(TimePickerDialogFragment.TIME_24)) {
                    recordDate = DateTimeUtil.convertDate24(recordDate);
                }
                else {
                    recordDate = DateTimeUtil.convertDate12(recordDate);
                }

                record.setRecordDate(recordDate);
                record.setTagId(tag.getId());
                record.setNote(addRecordFragment.getNote());

                int min = scale.getMin();
                int max = scale.getMax();
                int index = record.getGlycemicIndex();

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
