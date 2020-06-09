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

        String insertBloodSugarRecSql = "INSERT INTO blood_sugar_records (id, glycemic_index, record_date, tag_id, note) " +
                "VALUES (:id, :glycemic_index, :record_date, :tag_id, :note)";

        String insertAdviceTypeSql = "INSERT INTO advice_types (name) VALUES (:name)";

        String insertAdviceSql = "INSERT INTO advices (title, description, type_id) " +
                "VALUES (:title, :description, :type_id)";

        String insertFoodSql = "INSERT INTO foods (id, name, glycemic_index,type_id) " +
                "VALUES (:id, :name, :glycemic_index, :type_id)";

        String insertFoodTypeSql = "INSERT INTO food_types (id, name) " +
                "VALUES (:id, :name)";

        //Insert scales
        database.execSQL(insertScaleSql, new Object[] {1,"Before breakfast",90,130});
        database.execSQL(insertScaleSql, new Object[] {2,"Before meal",80,130});
        database.execSQL(insertScaleSql, new Object[] {3,"2h After meal",70,180});
        database.execSQL(insertScaleSql, new Object[] {4,"Before sleep",100,140});
        database.execSQL(insertScaleSql, new Object[] {5,"2 - 3 AM tomorrow",100,120});
        database.execSQL(insertScaleSql, new Object[] {6,"general",70,140});


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
        database.execSQL(insertAdviceSql, new Object[] {"Cao đường huyết", "Chỉ số đường huyết của bạn đang ở mức cao, bạn cần ăn nhiều trái cây như táo, lê, đào chuối, cá, đậu, thịt gà trong bữa ăn và tập thể dục thường xuyên.", 3});
        database.execSQL(insertAdviceSql, new Object[] {"Hạ đường huyết", "Chỉ số đường huyết của bạn đang ở thấp, bạn có thể ăn kẹo hoặc uống nước trái cây để lượng đường huyết trong máu trở lại bình thường.", 3});

        //Insert Food
        database.execSQL(insertFoodSql, new Object[] {1,"Bông cải xanh",10,1});
        database.execSQL(insertFoodSql, new Object[] {2,"Bắp cải",10,1});
        database.execSQL(insertFoodSql, new Object[] {3,"Nấm",10,1});
        database.execSQL(insertFoodSql, new Object[] {4,"Cà tím",15,1});
        database.execSQL(insertFoodSql, new Object[] {5,"Súp lơ",15,1});
        database.execSQL(insertFoodSql, new Object[] {6,"Cà chua",15,1});
        database.execSQL(insertFoodSql, new Object[] {7,"Đậu ván",15,1});
        database.execSQL(insertFoodSql, new Object[] {8,"Đậu nành nấu chín",16,1});
        database.execSQL(insertFoodSql, new Object[] {9,"Cà rốt tươi",16,1});
        database.execSQL(insertFoodSql, new Object[] {10,"Đậu lăng luộc",29,1});
        database.execSQL(insertFoodSql, new Object[] {11,"Cà rốt luộc",41,1});
        database.execSQL(insertFoodSql, new Object[] {12,"Ngô ngọt đông lạnh",47,1});
        database.execSQL(insertFoodSql, new Object[] {13,"Các loại đậu đóng hộp",52,1});
        database.execSQL(insertFoodSql, new Object[] {14,"Khoai luộc",56,1});
        database.execSQL(insertFoodSql, new Object[] {15,"Củ cải đường",64,1});
        database.execSQL(insertFoodSql, new Object[] {16,"Khoai nghiền",70,1});
        database.execSQL(insertFoodSql, new Object[] {17,"Đậu tằm",79,1});
        database.execSQL(insertFoodSql, new Object[] {18,"Khoai chế biến sẵn",83,1});
        database.execSQL(insertFoodSql, new Object[] {19,"Hành tây",10,1});
        database.execSQL(insertFoodSql, new Object[] {20,"Ớt, tiêu",10,1});
        database.execSQL(insertFoodSql, new Object[] {21,"Rau xà lách",10,1});
        database.execSQL(insertFoodSql, new Object[] {22,"Cần tây",15,1});
        database.execSQL(insertFoodSql, new Object[] {23,"Dưa chuột",15,1});
        database.execSQL(insertFoodSql, new Object[] {24,"Bí xanh",15,1});
        database.execSQL(insertFoodSql, new Object[] {25,"Rau bina",15,1});
        database.execSQL(insertFoodSql, new Object[] {26,"Đậu cove",15,1});
        database.execSQL(insertFoodSql, new Object[] {27,"Đậu Hà Lan khô",22,1});
        database.execSQL(insertFoodSql, new Object[] {28,"Đậu xanh",33,1});
        database.execSQL(insertFoodSql, new Object[] {29,"Đậu đen",41,1});
        database.execSQL(insertFoodSql, new Object[] {30,"Khoai lang",51,1});
        database.execSQL(insertFoodSql, new Object[] {31,"Khoai tây",54,1});
        database.execSQL(insertFoodSql, new Object[] {32,"Khoai đóng hộp",61,1});
        database.execSQL(insertFoodSql, new Object[] {33,"Khoai hấp",64,1});
        database.execSQL(insertFoodSql, new Object[] {34,"Bí ngô",75,1});
        database.execSQL(insertFoodSql, new Object[] {35,"Khoai nướng lò vi sóng",82,1});
        database.execSQL(insertFoodSql, new Object[] {36,"Củ cải vàng",97,1});
        database.execSQL(insertFoodSql, new Object[] {37,"Mận khô",15,2});
        database.execSQL(insertFoodSql, new Object[] {38,"Mận",39,2});
        database.execSQL(insertFoodSql, new Object[] {39,"Cherry",22,2});
        database.execSQL(insertFoodSql, new Object[] {40,"Bưởi",25,2});
        database.execSQL(insertFoodSql, new Object[] {41,"Mơ khô",31,2});
        database.execSQL(insertFoodSql, new Object[] {42,"Dâu",32,2});
        database.execSQL(insertFoodSql, new Object[] {43,"Sung",35,2});
        database.execSQL(insertFoodSql, new Object[] {44,"Táo",38,2});
        database.execSQL(insertFoodSql, new Object[] {45,"Lê",38,2});
        database.execSQL(insertFoodSql, new Object[] {46,"Đào",42,2});
        database.execSQL(insertFoodSql, new Object[] {47,"Cam",44,2});
        database.execSQL(insertFoodSql, new Object[] {48,"Dừa",45,2});
        database.execSQL(insertFoodSql, new Object[] {49,"Nho",46,2});
        database.execSQL(insertFoodSql, new Object[] {50,"Kiwi",53,2});
        database.execSQL(insertFoodSql, new Object[] {51,"Chuối",54,2});
        database.execSQL(insertFoodSql, new Object[] {52,"Mơ",57,2});
        database.execSQL(insertFoodSql, new Object[] {53,"Cocktail trái cây",55,2});
        database.execSQL(insertFoodSql, new Object[] {54,"Xoài",56,2});
        database.execSQL(insertFoodSql, new Object[] {55,"Mơ trong siro",64,2});
        database.execSQL(insertFoodSql, new Object[] {56,"Đu đủ",60,2});
        database.execSQL(insertFoodSql, new Object[] {57,"Dứa",66,2});
        database.execSQL(insertFoodSql, new Object[] {58,"Dưa hấu",72,2});
        database.execSQL(insertFoodSql, new Object[] {59,"Chà là",13,2});
        database.execSQL(insertFoodSql, new Object[] {60,"Lúa mạch",28,3});
        database.execSQL(insertFoodSql, new Object[] {61,"Spaghetti lúa mì",37,3});
        database.execSQL(insertFoodSql, new Object[] {62,"Spaghetti trắng",41,3});
        database.execSQL(insertFoodSql, new Object[] {63,"Bánh ngô hấp",46,3});
        database.execSQL(insertFoodSql, new Object[] {64,"Mì ăn liền",48,3});
        database.execSQL(insertFoodSql, new Object[] {65,"Bún",53,3});
        database.execSQL(insertFoodSql, new Object[] {66,"Mì Udon",54,3});
        database.execSQL(insertFoodSql, new Object[] {67,"Cháo yến mạch xay nhỏ",55,3});
        database.execSQL(insertFoodSql, new Object[] {68,"Cơm gạo lứt ",68,3});
        database.execSQL(insertFoodSql, new Object[] {69,"Cháo kê",67,3});
        database.execSQL(insertFoodSql, new Object[] {70,"Bánh mì",73,3});
        database.execSQL(insertFoodSql, new Object[] {71,"Cơm trắng",74,3});
        database.execSQL(insertFoodSql, new Object[] {72,"Hạt kê",71,3});
        database.execSQL(insertFoodSql, new Object[] {73,"Cháo gạo trắng ",78,3});
        database.execSQL(insertFoodSql, new Object[] {74,"Bánh ngô nướng",81,3});
        database.execSQL(insertFoodSql, new Object[] {75,"Sắn tàu",80,3});
        database.execSQL(insertFoodSql, new Object[] {76,"Cháo yến mạch ăn liền",79,3});
        database.execSQL(insertFoodSql, new Object[] {77,"Xôi",86,3});
        database.execSQL(insertFoodSql, new Object[] {78,"Lạc",13,4});
        database.execSQL(insertFoodSql, new Object[] {79,"Quả óc chó",15,4});
        database.execSQL(insertFoodSql, new Object[] {80,"Hạt điều",25,4});
        database.execSQL(insertFoodSql, new Object[] {81,"Các loại hạt khô",21,4});
        database.execSQL(insertFoodSql, new Object[] {82,"Ngô dẻo",42,4});
        database.execSQL(insertFoodSql, new Object[] {83,"Bánh yến mạch giòn",55,4});
        database.execSQL(insertFoodSql, new Object[] {84,"Socola",49,4});
        database.execSQL(insertFoodSql, new Object[] {85,"Bánh hamburger",61,4});
        database.execSQL(insertFoodSql, new Object[] {86,"Bánh việt quất",59,4});
        database.execSQL(insertFoodSql, new Object[] {87,"Khoai tây chiên giòn",56,4});
        database.execSQL(insertFoodSql, new Object[] {88,"Nước ngọt/soda",59,4});
        database.execSQL(insertFoodSql, new Object[] {89,"Mật ong",58,4});
        database.execSQL(insertFoodSql, new Object[] {90,"Bắp rang",65,4});
        database.execSQL(insertFoodSql, new Object[] {91,"Bánh sừng trâu",67,4});
        database.execSQL(insertFoodSql, new Object[] {92,"Bánh Donut",76,4});
        database.execSQL(insertFoodSql, new Object[] {93,"Bánh quy",83,4});
        database.execSQL(insertFoodSql, new Object[] {94,"Bánh gạo",87,4});
        database.execSQL(insertFoodSql, new Object[] {95,"Bánh nướng ",92,4});
        database.execSQL(insertFoodSql, new Object[] {96,"Sữa chua có đường ",23,5});
        database.execSQL(insertFoodSql, new Object[] {97,"Sữa đậu nành ",34,5});
        database.execSQL(insertFoodSql, new Object[] {98,"Sữa trứng ",35,5});
        database.execSQL(insertFoodSql, new Object[] {99,"Sữa gầy ",37,5});
        database.execSQL(insertFoodSql, new Object[] {100,"Sữa đầy đủ chất béo ",39,5});
        database.execSQL(insertFoodSql, new Object[] {101,"Sữa chua trái cây ",41,5});
        database.execSQL(insertFoodSql, new Object[] {102,"Nước ép cà chua ",38,5});
        database.execSQL(insertFoodSql, new Object[] {103,"Nước ép táo ",41,5});
        database.execSQL(insertFoodSql, new Object[] {104,"Nước ép dứa, bưởi ",46,5});
        database.execSQL(insertFoodSql, new Object[] {105,"Nước ép cà rốt ",45,5});
        database.execSQL(insertFoodSql, new Object[] {106,"Nước cam, chanh ",52,5});
        database.execSQL(insertFoodSql, new Object[] {107,"Kem ít béo ",50,5});
        database.execSQL(insertFoodSql, new Object[] {108,"Nước trái cây lên men ",67,5});
        database.execSQL(insertFoodSql, new Object[] {109,"Sữa gạo ",86,5});
        database.execSQL(insertFoodSql, new Object[] {110,"Sữa gạo ",77,5});
        database.execSQL(insertFoodSql, new Object[] {111,"Milo ",55,5});
        database.execSQL(insertFoodSql, new Object[] {112,"Fanta ",68,5});

        //Insert Food Type
        database.execSQL(insertFoodTypeSql, new Object[] {1,"Rau quả và đậu"});
        database.execSQL(insertFoodTypeSql, new Object[] {2,"Trái cây"});
        database.execSQL(insertFoodTypeSql, new Object[] {3,"Thực phẩm chứa nhiều tinh bột"});
        database.execSQL(insertFoodTypeSql, new Object[] {4,"Đồ ăn nhẹ và thực phẩm ngọt"});
        database.execSQL(insertFoodTypeSql, new Object[] {5,"Sữa và đồ uống"});
    }

    public abstract BloodSugarRecordDao recordDao();
    public abstract TagDao tagDao();
    public abstract ScaleDao scaleDao();
    public abstract ReminderDao reminderDao();
    public abstract ReminderInfoDao reminderInfoDao();
    public abstract AdviceDao adviceDao();
    public abstract FoodDao foodDao();
}
