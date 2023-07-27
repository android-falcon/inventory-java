package com.example.user54.InventoryApp.ROOM;



import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemSwitch;
import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.OfferTable;
import com.example.user54.InventoryApp.controll;


@Database(entities = {ItemCard.class, ItemSwitch.class, ItemUnit.class, OfferTable.class}, version =  105 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDaoCard itemCard();

    public abstract UserDaoSwitch itemSwitch();
    public abstract UserDaoUnit itemUnit();
    public abstract UserDaoOffer itemOffer();

    private static AppDatabase InstanceDatabase;
    private static String DatabaseName="InventoryDBase";


    static final Migration FROM_1_TO_2 = new Migration(99, 105) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Repo
//                    ADD COLUMN createdAt TEXT");

            //[{"CONO":"290","ITEMOCODE":"40043","ITEMNAMEA":"دجاج مسلخ عمان","DESCNAME":"","F_D":"1.49",
            // "FRMDATE":"01\/01\/2023","TODATE":"02\/01\/2023","OFFERNO":"1851"}
try {
    database.execSQL("CREATE TABLE `OFFER_TABLE` (`OFFERNO` INTEGER, "
            + "`CONO` TEXT ,`ITEMOCODE` TEXT ,`ITEMNAMEA` TEXT,`DESCNAME` TEXT,`FRMDATE` TEXT, `TODATE` TEXT, PRIMARY KEY(`id`))");
}catch (Exception e){
    Log.e("Exception ","create in megration table offer"+e.toString());
}

        }
    };

    public static synchronized AppDatabase getInstanceDatabase(Context context) {



        if (InstanceDatabase == null) {

            InstanceDatabase = Room.databaseBuilder(context, AppDatabase.class, DatabaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                   .fallbackToDestructiveMigrationFrom(36, 37, 38, 39,40)
                    .addMigrations(FROM_1_TO_2)
                    .build();

        }

        return InstanceDatabase;

    }

}

