package com.pedrycz.phonedb.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pedrycz.phonedb.DAO.PhoneDao;
import com.pedrycz.phonedb.entities.Phone;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneDB extends RoomDatabase {

    public abstract PhoneDao phoneDao();

    private static volatile PhoneDB INSTANCE;

    public static PhoneDB getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (PhoneDB.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PhoneDB.class, "phonedb")
                            .addCallback(phoneDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final RoomDatabase.Callback phoneDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                PhoneDao dao = INSTANCE.phoneDao();
                List<Phone> phones = List.of(
                        new Phone("Samsung", "Galaxy S23 Ultra", "13", "https://www.samsung.com/pl/smartphones/galaxy-s23-ultra/buy/"),
                        new Phone("Huawei", "Mate 20 lite", "10", "https://www.mgsm.pl/pl/katalog/huawei/mate20lite/"),
                        new Phone("Nokia", "C12 PRO", "12 Go", "https://www.mgsm.pl/pl/katalog/nokia/c12pro/"),
                        new Phone("Sony", "Xperia J", "4.0 Ice Cream Sandwich", "https://www.mgsm.pl/pl/katalog/sony/xperiaj/"),
                        new Phone("OnePlus", "Nord 2T 5G", "12", "https://www.gadgets360.com/oneplus-nord-2t-5g-price-in-india-106885")
                );
                for(Phone p: phones){
                    dao.insert(p);
                }
            });
        }
    };
}
