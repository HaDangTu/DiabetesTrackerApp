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

import com.example.diabetestracker.DetailRecordFragment;
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

public class EditRecordMenuItemClickListener extends BaseMenuItemClickListener {
    private RecordRepository recordRepository;

    public EditRecordMenuItemClickListener(DetailRecordFragment fragment) {
        super(fragment);
        this.fragment = fragment;
        recordRepository = new RecordRepository(fragment.getActivity().getApplication());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        boolean result = false;
        int id = item.getItemId();
        DetailRecordFragment detailRecordFragment = (DetailRecordFragment) fragment;
        BloodSugarRecord record = detailRecordFragment.getRecord();
        TagScale tagScale = detailRecordFragment.getTagScale();
        Tag tag = tagScale.getTag();
        Scale scale = tagScale.getScale();

        switch (id) {
            case R.id.item_edit:
                if (!detailRecordFragment.hasError()) {
                    float glycemicIndex = detailRecordFragment.getGlycemicIndex();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(fragment.getContext());
                    String unit = sharedPreferences.getString(SettingsFragment.UNIT_KEY, RecordRecyclerAdapter.MMOL_L);
                    String time = sharedPreferences.getString(SettingsFragment.TIME_KEY, TimePickerDialogFragment.TIME_24);

                    String recordDateTime = detailRecordFragment.getDateTimeRecord();
                    //Convert lại thành kiểu yyyy-MM-dd HH:mm:ss để dễ xử lý
                    if (time.equals(TimePickerDialogFragment.TIME_24)) {
                        recordDateTime = DateTimeUtil.convertDate24(recordDateTime);
                    }
                    else
                        recordDateTime = DateTimeUtil.convertDate12(recordDateTime);

                    String note = detailRecordFragment.getNote();


                    if (unit.equals(RecordRecyclerAdapter.MMOL_L)) {
                        glycemicIndex = UnitConverter.mmol_To_mg(glycemicIndex);
                    }
                    record.setGlycemicIndex((int) glycemicIndex);

                    record.setRecordDate(recordDateTime);
                    record.setNote(note);
                    record.setTagId(tag.getId());

                    int max = scale.getMax();
                    int min = scale.getMin();
                    int index = record.getGlycemicIndex();
                    final Context context = fragment.getContext();

                    AdviceViewModel viewModel = new ViewModelProvider(fragment.requireActivity(),
                            ViewModelProvider.AndroidViewModelFactory
                                    .getInstance(fragment.getActivity().getApplication()))
                            .get(AdviceViewModel.class);

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
                    else if (index < min) {
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

                    recordRepository.update(record);
                }
                result = true;
                break;
            case R.id.item_delete:
                //TODO Add dialog asking user agreement
                recordRepository.delete(record);
                result = true;
                break;
        }

        FragmentManager fragmentManager = fragment.getFragmentManager();
        fragmentManager.popBackStack();
        return result;
    }
}
