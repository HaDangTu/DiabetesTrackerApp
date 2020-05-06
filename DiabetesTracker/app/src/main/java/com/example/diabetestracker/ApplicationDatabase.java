package com.example.diabetestracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.diabetestracker.daos.BloodSugarRecordDao;
import com.example.diabetestracker.daos.ReminderDao;
import com.example.diabetestracker.daos.ScaleDao;
import com.example.diabetestracker.daos.TagDao;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.Scale;
import com.example.diabetestracker.entities.Tag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Scale.class, Tag.class, BloodSugarRecord.class, Reminder.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static ApplicationDatabase __instance = null;

    private static final int AVAILABLE_PROCESSOR = Runtime.getRuntime().availableProcessors();
    public static ExecutorService dbExecutor = Executors.newFixedThreadPool(AVAILABLE_PROCESSOR);

    public static ApplicationDatabase getInstance(Context context){
        if (__instance == null){
            synchronized (ApplicationDatabase.class) {
                __instance = Room.databaseBuilder(context, ApplicationDatabase.class, "diabetes_tracker.db")
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                __instance.populateData(db);
                            }
                        })
                        .build();
            }
        }
        return __instance;
    }
    private void populateData(SupportSQLiteDatabase database){
        String insertScaleSql = "INSERT INTO scales (id, name, min, max) VALUES " +
                "(:id, :name, :min, :max)";

        String insertSessionSql = "INSERT INTO tags (id, name, scale_id, is_default) VALUES" +
                "(:id, :name, :scale_id, :is_default)";

        String insertBloodSugarRecSql = "INSERT INTO blood_sugar_records (id, blood_sugar_level, record_date, note, tag_id) " +
                "VALUES (:id, :blood_sugar_level, :record_date, :note, :tag_id)";
        //Insert scales
        database.execSQL(insertScaleSql, new Object[] {1, "Trước bữa sáng", 5, 7.2});
        database.execSQL(insertScaleSql, new Object[] {2, "Trước bữa ăn", 4.9, 7.2});
        database.execSQL(insertScaleSql, new Object[] {3, "2h sau khi ăn", 3.8, 10});
        database.execSQL(insertScaleSql, new Object[] {4, "Trước khi đi ngủ", 5.6, 7.8});
        database.execSQL(insertScaleSql, new Object[] {5, "2 - 3 sáng hôm sau", 5.6, 6.6});
        database.execSQL(insertScaleSql, new Object[] {6, "Chung", 3.8, 7.8});


        //Insert sessions
        database.execSQL(insertSessionSql, new Object[] {1,	"Trước bữa sáng", 1, true});
        database.execSQL(insertSessionSql, new Object[] {2,	"Sau bữa sáng", 3, true});
        database.execSQL(insertSessionSql, new Object[] {3,	"Trước bữa trưa", 2, true});
        database.execSQL(insertSessionSql, new Object[] {4,	"Sau bữa trưa", 3, true});
        database.execSQL(insertSessionSql, new Object[] {5,	"Trước bữa tối", 2, true});
        database.execSQL(insertSessionSql, new Object[] {6,	"Sau bữa tối", 3, true});
        database.execSQL(insertSessionSql, new Object[] {7,	"Trước khi đi ngủ",	4, true});
        database.execSQL(insertSessionSql, new Object[] {8,	"2 - 3 AM hôm sau",	5, true});
        database.execSQL(insertSessionSql, new Object[] {9,	"General",	6, true});

        //Insert blood sugar records
        database.execSQL(insertBloodSugarRecSql, new Object[] {1,6,"2020-04-12 07:00:00","", 1});
        database.execSQL(insertBloodSugarRecSql, new Object[] {2,6.1,"2020-04-12 09:00:00","", 2});
        database.execSQL(insertBloodSugarRecSql, new Object[] {3,6.2,"2020-04-12 11:00:00","", 3});
        database.execSQL(insertBloodSugarRecSql, new Object[] {4,8.4,"2020-04-12 13:00:00","", 4});
        database.execSQL(insertBloodSugarRecSql, new Object[] {5,6.3,"2020-04-12 19:00:00","", 5});
        database.execSQL(insertBloodSugarRecSql, new Object[] {6,6.6,"2020-04-12 21:00:00","", 6});
        database.execSQL(insertBloodSugarRecSql, new Object[] {7,7.1,"2020-04-12 22:30:00","", 7});
        database.execSQL(insertBloodSugarRecSql, new Object[] {8,6,"2020-04-13 02:00:00","", 8});
        database.execSQL(insertBloodSugarRecSql, new Object[] {9,6.4,"2020-04-13 07:00:00","", 1});
        database.execSQL(insertBloodSugarRecSql, new Object[] {10,7.3,"2020-04-13 09:00:00","", 2});
        database.execSQL(insertBloodSugarRecSql, new Object[] {11,6.7,"2020-04-13 11:00:00","", 3});
        database.execSQL(insertBloodSugarRecSql, new Object[] {12,6.8,"2020-04-13 13:00:00","", 4});
        database.execSQL(insertBloodSugarRecSql, new Object[] {13,6.1,"2020-04-13 19:00:00","", 5});
        database.execSQL(insertBloodSugarRecSql, new Object[] {14,6,"2020-04-13 21:00:00","", 6});
        database.execSQL(insertBloodSugarRecSql, new Object[] {15,7.1,"2020-04-13 22:30:00","", 7});
        database.execSQL(insertBloodSugarRecSql, new Object[] {16,6.2,"2020-04-14 03:00:00","", 8});
        database.execSQL(insertBloodSugarRecSql, new Object[] {17,6,"2020-04-14 07:00:00","", 1});
        database.execSQL(insertBloodSugarRecSql, new Object[] {18,6.1,"2020-04-14 09:00:00","", 2});
        database.execSQL(insertBloodSugarRecSql, new Object[] {19,7.4,"2020-04-14 11:00:00","", 3});
        database.execSQL(insertBloodSugarRecSql, new Object[] {20,6.9,"2020-04-14 13:00:00","", 4});
        database.execSQL(insertBloodSugarRecSql, new Object[] {21,7,"2020-04-14 19:00:00","", 5});
        database.execSQL(insertBloodSugarRecSql, new Object[] {22,6.4,"2020-04-14 21:00:00","", 6});
        database.execSQL(insertBloodSugarRecSql, new Object[] {23,7.2,"2020-04-14 22:30:00","", 7});
        database.execSQL(insertBloodSugarRecSql, new Object[] {24,6.4,"2020-04-15 02:00:00","", 8});
        database.execSQL(insertBloodSugarRecSql, new Object[] {25,6,"2020-04-15 07:00:00","", 1});
        database.execSQL(insertBloodSugarRecSql, new Object[] {26,6.1,"2020-04-15 09:00:00","", 2});
        database.execSQL(insertBloodSugarRecSql, new Object[] {27,7,"2020-04-15 11:00:00","", 3});
        database.execSQL(insertBloodSugarRecSql, new Object[] {28,6.9,"2020-04-15 13:00:00","", 4});
        database.execSQL(insertBloodSugarRecSql, new Object[] {29,10.3,"2020-04-15 19:00:00","", 5});
        database.execSQL(insertBloodSugarRecSql, new Object[] {30,6.6,"2020-04-15 21:00:00","", 6});
        database.execSQL(insertBloodSugarRecSql, new Object[] {31,7.1,"2020-04-15 22:30:00","", 7});
        database.execSQL(insertBloodSugarRecSql, new Object[] {32,6,"2020-04-16 02:00:00","", 8});
        database.execSQL(insertBloodSugarRecSql, new Object[] {33,6.4,"2020-04-16 07:00:00","", 1});
        database.execSQL(insertBloodSugarRecSql, new Object[] {34,6.1,"2020-04-16 09:00:00","", 2});
        database.execSQL(insertBloodSugarRecSql, new Object[] {35,8.3,"2020-04-16 11:00:00","", 3});
        database.execSQL(insertBloodSugarRecSql, new Object[] {36,10.1,"2020-04-16 13:00:00","", 4});
        database.execSQL(insertBloodSugarRecSql, new Object[] {37,6.3,"2020-04-16 19:00:00","", 5});
        database.execSQL(insertBloodSugarRecSql, new Object[] {38,6.6,"2020-04-16 22:00:00","", 6});
        database.execSQL(insertBloodSugarRecSql, new Object[] {39,7.1,"2020-04-16 22:30:00","", 7});
    }
    public abstract BloodSugarRecordDao recordDao();
    public abstract TagDao tagDao();
    public abstract ScaleDao scaleDao();
    public abstract ReminderDao reminderDao();
}
