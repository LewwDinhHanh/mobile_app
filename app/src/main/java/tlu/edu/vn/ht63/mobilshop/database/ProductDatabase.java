package tlu.edu.vn.ht63.mobilshop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tlu.edu.vn.ht63.mobilshop.model.Product;

@Database(entities = {Product.class},version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "product.db";
    private static ProductDatabase INSTANCE;
    public abstract ProductDAO productDAO();

    public static synchronized ProductDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ProductDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
