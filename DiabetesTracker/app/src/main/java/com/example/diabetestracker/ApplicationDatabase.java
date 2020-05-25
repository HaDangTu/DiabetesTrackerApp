package com.example.diabetestracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.diabetestracker.daos.AdviceDao;
import com.example.diabetestracker.daos.BloodSugarRecordDao;
import com.example.diabetestracker.daos.FoodDao;
import com.example.diabetestracker.daos.ReminderDao;
import com.example.diabetestracker.daos.ReminderInfoDao;
import com.example.diabetestracker.daos.ScaleDao;
import com.example.diabetestracker.daos.TagDao;
import com.example.diabetestracker.entities.Advice;
import com.example.diabetestracker.entities.AdviceType;
import com.example.diabetestracker.entities.BloodSugarRecord;
import com.example.diabetestracker.entities.Food;
import com.example.diabetestracker.entities.FoodType;
import com.example.diabetestracker.entities.Reminder;
import com.example.diabetestracker.entities.ReminderInfo;
import com.example.diabetestracker.entities.Scale;
import com.example.diabetestracker.entities.Tag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Scale.class, Tag.class, BloodSugarRecord.class, Reminder.class, ReminderInfo.class,
AdviceType.class, Advice.class, FoodType.class, Food.class}, version = 1)
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

        String insertAdviceTypeSql = "INSERT INTO advice_types (name) VALUES (:name)";

        String insertAdviceSql = "INSERT INTO advices (title, description, type_id) " +
                "VALUES (:title, :description, :type_id)";
        //Insert scales
        database.execSQL(insertScaleSql, new Object[] {1, "Trước bữa sáng", 5, 7.2});
        database.execSQL(insertScaleSql, new Object[] {2, "Trước bữa ăn", 4.4, 7.2});
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

        //Insert advice type
        database.execSQL(insertAdviceTypeSql, new Object[] {"Eating advice"});
        database.execSQL(insertAdviceTypeSql, new Object[] {"Gymnastic advice"});
        database.execSQL(insertAdviceTypeSql, new Object[] {"Warning advice"});

        //Insert advice
        database.execSQL(insertAdviceSql, new Object[] {"Chế độ dinh dưỡng lành mạnh","Một chế độ dinh dưỡng phù hợp cho người đái tháo đường (tiểu đường) sẽ bao gồm 5 nhóm thực phẩm: \n - Rau củ và các loại đậu.\n - Trái cây tươi nguyên vỏ.\n - Thực phẩm nguyên hạt.\n - Trứng, thịt, cá ít béo.\n - Sữa và các sản phẩm từ sữa.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Ăn đủ bữa","Khi bị đái tháo đường (tiểu đường) bạn không được bỏ bữa, để cơ thể cảm thấy quá đói.\nKhi đói, bạn dễ ăn nhiều, cùng lúc nạp lượng lớn năng lượng vào cơ thể. Ăn đủ 3 bữa mỗi ngày, kèm những bữa ăn nhẹ sẽ giúp tránh nguy cơ trên, ổn định mức glucose máu.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Hạn chế đường, muối và chất béo bão hòa","Lượng muối ăn tiêu chuẩn ở người trưởng thành là 1500-2300 mg / ngày. \nThay vào đó, bạn có thể hấp thụ một lượng vừa phải chất béo không bão hòa (có trong các loại hạt, cá, quả bơ…) và lượng đường phù hợp với mức glucose máu của bản thân.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Ăn uống kết hợp tập thể dục thể thao","Thật vậy, cơ thể bạn sẽ hấp thụ dinh dưỡng tối ưu nhất khi kết hợp với hoạt động thể dục thể thao.\nĂn uống đủ chất, năng lượng cho cơ thể vận động là chìa khóa giúp người đái tháo đường kiểm soát cân nặng và mức glucose máu.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Uống đủ nước mỗi ngày","Nước là yếu tố cần thiết cho mọi hoạt động sống trong cơ thể.\nĐặc biệt, nước lọc cũng không hề ảnh hưởng đến mức glucose máu trong cơ thể bạn. Ngược lại, bạn cần hạn chế các thức uống có đường và bia rượu (không quá 1 ly mỗi ngày).",1});
        database.execSQL(insertAdviceSql, new Object[] {"Bánh kẹo, nước ngọt","Hầu như tất cả loại bánh ngọt, kẹo và nước có ga trên thị trường đều có hại cho người đái tháo đường. Những thực phẩm này không có dưỡng chất, và cung cấp quá nhiều đường bột vào cơ thể. Khi ăn thường xuyên, bánh kẹo khiến bạn tăng cân, cũng như làm tăng mức glucose trong máu, từ đó làm trầm trọng thêm biến chứng của bệnh đái tháo đường.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Trái cây khô","Trái cây có nhiều chất dinh dưỡng cần thiết cho cơ thể. Tuy nhiên, quá trình xử lý và sấy khô khiến lượng đường tự nhiên trong trái cây bị cô đặc lại. Những túi nho khô, mít và chuối sấy khô là lý do khiến mức glucose máu của bạn đột ngột tăng mạnh. Giải pháp tốt là bạn nên chọn ăn trái cây tươi, vừa hạn chế đường, vừa bổ sung chất xơ và vitamin.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Thực phẩm đóng hộp, chế biến sẵn","Tuy tiện lợi và giá cả phải chăng, các thực phẩm đóng hộp trong siêu thị lại tiềm ẩn rất nhiều nguy cơ sức khỏe. Bạn không thể kiểm soát lượng gia vị, chất bảo quản có trong những thức ăn này và thường là chúng đều có nhiều muối, đường. Thường xuyên sử dụng thực phẩm đóng hộp thay cho nguyên liệu tươi là bạn đang đối diện với nguy cơ mắc bệnh tim mạch, đái tháo đường và nhiều bệnh mãn tính khác.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Cơm, gạo và tinh bột","Cơm là món không thể thiếu trong bữa ăn truyền thống của người Việt Nam. Bị đái tháo đường không có nghĩa là bạn phải ngừng ăn cơm, nhưng cần hạn chế lượng vừa phải cho mỗi bữa. Theo nghiên cứu vào năm 2012, ăn nhiều cơm và tinh bột có thể làm tăng 11% nguy cơ bị đái tháo đường typ 2. Tốt nhất, bạn nên chọn các loại ngũ cốc nguyên hạt (có chữ whole-grain trên bao bì}); đang ngày càng thịnh hành trên thị trường.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Một số sản phẩm từ sữa","Sản phẩm từ sữa bao hàm những loại chứa đường và chất béo như sữa đậu nành, kem, phô mai béo, sữa chua có đường...Không chỉ làm tăng lượng cholesterol có hại, những sản phẩm này còn làm nặng thêm tình trạng kháng insulin ở người đái tháo đường. Bạn nên cân nhắc các sản phẩm thay thế như sữa ít béo, sữa không đường, sản phẩm đã được xử lý để loại bỏ chất béo hoàn toàn.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Thịt chứa nhiều mỡ","Tương tự với sữa có đường, thịt mỡ là nguồn cung cấp giàu chất béo bão hòa. Khi vào trong cơ thể, chất béo bão hòa làm tăng cholesterol, gây xơ vữa thành động mạch. Do đó, người đái tháo đường ăn thịt chứa nhiều mỡ sẽ đối diện với nguy cơ cao bị bệnh tim mạch như suy tim, tăng huyết áp… Nếu muốn bổ sung protein, bạn nên ăn cá, thịt ức gà bỏ da, thịt nạc heo và thịt thăn bò.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Cá hồi","Cá hồi giàu vitamin B6 – chất cần thiết để tổng hợp melatonin giúp bạn dễ ngủ. Đồng thời, cá hồi cũng là nguồn chất đạm, Omega-3 rất tốt trong việc cân bằng glucose huyết. Bạn nên ăn khoảng 100 – 170gr cá hồi, ít nhất hai lần một tuần.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Quả cherry","Hiếm có loại hoa quả nào có khả năng kiểm soát đồng hồ sinh học của cơ thể tuyệt vời như quả cherry. Cherry có tác dụng cải thiện thời gian và chất lượng giấc ngủ đối với người bị mất ngủ mãn tính. Đặc biệt, một nghiên cứu của Đại học Michigan, Mỹ còn cho thấy quả cherry giúp kiểm soát và cân bằng glucose huyết nhờ khả năng giúp cơ thể người bệnh đái tháo đường tăng sản xuất insulin. Bạn có thể mua cherry tại siêu thị và nên ăn quả tươi thay vì uống nước ép.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Chuối","Ăn chuối không những tốt cho sức khỏe mà còn có lợi trong việc cân bằng glucose huyết ở người đái tháo đường. Chuối chứa vitamin B6 có tác dụng giảm căng thẳng, đồng thời các khoáng chất Ma-giê và Kali giúp ngủ ngon hơn và lâu hơn, đặc biệt là ở người cao tuổi.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Thực phẩm làm từ sữa","Trong sữa có chứa các axit amin giúp bạn đi vào giấc ngủ dễ dàng hơn. Buổi tối trước khi ngủ bạn nên uống một ly sữa ấm. Ngoài ra trong khẩu phần ăn hàng ngày có thể bổ sung một số thực phẩm làm từ sữa như phô mai, sữa chua…Tuy nhiên bạn nên lưu ý chọn loại sữa, sản phẩm ít hoặc không béo để mang lại hiệu quả tốt nhất.\nSản phẩm từ sữa bao hàm những loại chứa đường và chất béo như sữa đậu nành, kem, phô mai béo, sữa chua có đường...Không chỉ làm tăng lượng cholesterol có hại, những sản phẩm này còn làm nặng thêm tình trạng kháng insulin ở người đái tháo đường.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Trà thảo mộc","Giấc ngủ ngon là một trong những yếu tố quan trọng giúp cân bằng glucose huyết. Một số loại trà thảo mộc có tác dụng thư giãn, an thần và cải thiện đáng kể chất lượng giấc ngủ như trà atiso, trà tía tô đất (lemon balm}); trà hoa nữ lang (valerian tea) hoặc trà hoa bia (hop tea)…",1});
        database.execSQL(insertAdviceSql, new Object[] {"Hạnh nhân","Chứa chất béo có lợi cho sức khỏe và chất xơ hỗ trợ tiêu hóa, hạnh nhân là loại thực phẩm rất hiệu quả cho người đái tháo đường. Hạnh nhân có nhiều ma-giê giúp thư giãn cơ bắp. Đồng thời loại hạt này cũng giàu canxi giúp bộ não chuyển tryptophan thành melatonin tạo cảm giác buồn ngủ. Ăn hạnh nhân nguyên hạt hoặc uống sữa hạnh nhân giúp khẩu phần phong phú và ngon miệng hơn.",1});
        database.execSQL(insertAdviceSql, new Object[] {"Cải bó xôi","Cải bó xôi (rau chân vịt) chứa một “danh sách” các chất giúp ngủ ngon như tryptophan, axit folic, ma-giê, vitamin B6 và vitamin C. Hàm lượng chất xơ cao là ưu điểm của loại rau xanh này trong việc cân bằng glucose huyết cho người đái tháo đường. Lưu ý khi chế biến cải bó xôi không nên nấu quá lâu để tránh mất các dưỡng chất. Một số loại rau xanh khác có tác dụng tương tự cải bó xôi mà bạn có thể ăn mỗi bữa gồm cải xoăn, bông cải xanh…",1});
        database.execSQL(insertAdviceSql, new Object[] {"Thể dục thể thao","Vận động thể lực là thói quen tốt, nâng cao sức khỏe cho tất cả mọi người. Điều này lại càng quan trọng hơn với người bệnh đái tháo đường (tiểu đường) trên hành trình giảm cân, kiểm soát mức glucose máu, và sống vui khỏe, lạc quan.\nNhững lợi ích vô giá mà thể dục thể thao mang lại cho bạn gồm:\nTăng khả năng sử dụng insulin.\nGiữ thân hình cân đối.\nCân bằng huyết áp.\nCải thiện giấc ngủ.\nGiảm nguy cơ bệnh tim mạch.\nCải thiện trí nhớ.",2});
        database.execSQL(insertAdviceSql, new Object[] {"Tập luyện một cách an toàn","Hỏi ý kiến bác sĩ trước khi bắt đầu tập.\n - Không tập luyện khi trời quá nóng hay quá lạnh.\n - Uống đủ nước trước, trong và sau khi tập luyện.\n - Lập tức ngừng tập khi thấy kiệt sức, choáng váng.\n - Đo glucose máu để điều chỉnh bài tập cho phù hợp.\n - Lựa chọn giày thích hợp khi luyện tập.",2});
        database.execSQL(insertAdviceSql, new Object[] {"Bài tập thể lực","Giúp tuần hoàn máu, hỗ trợ tim và xương, giảm stress\n - Tập ít nhất 5 ngày/tuần, 30 phút/ lần\n - Cường độ tập vừa phải, có thể chia thành bài tập nhỏ\n - Đi bộ nhanh, bơi lội, đạp xe, học nhảy…",2});
        database.execSQL(insertAdviceSql, new Object[] {"Bài tập cơ bắp","Cải thiện insulin, giảm glucose máu, hỗ trợ cơ xương.\n - Tập ít nhất 2 ngày/tuần.\n - Cường độ vừa phải, tập ở nhà hoặc phòng tập.\n - Tập tạ, hít đất, các lớp tập thể lực…",2});
        database.execSQL(insertAdviceSql, new Object[] {"Bài tập co giãn","Tăng độ linh hoạt ở khớp, tránh chấn thương khi tập.\n - 5-10 phút trước và sau khi tập luyện.\n - Co giãn vừa phải, dừng lại khi bị đau.\n - Yoga, thái cực quyền, khởi động căn bản.",2});
        database.execSQL(insertAdviceSql, new Object[] {"Cao đường huyết", "Chỉ số đường huyết của bạn đang ở mức cao, cần hạn chế các món ăn có chứa nhiều carbohydrate như các loại ngũ cốc tinh chế, đồ uống ngọt và thực phẩm có đường, đồng thời cần tăng cường chất xơ trong bữa ăn và tập thể dục thường xuyên.", 3});
        database.execSQL(insertAdviceSql, new Object[] {"Hạ đường huyết", "Chỉ số đường huyết của bạn đang ở thấp, bạn có thể ăn kẹo hoặc uống nước trái cây để lượng đường huyết trong máu trở lại bình thường.", 3});
    }

    public abstract BloodSugarRecordDao recordDao();
    public abstract TagDao tagDao();
    public abstract ScaleDao scaleDao();
    public abstract ReminderDao reminderDao();
    public abstract ReminderInfoDao reminderInfoDao();
    public abstract AdviceDao adviceDao();
    public abstract FoodDao foodDao();
}
