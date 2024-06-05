package tlu.edu.vn.ht63.mobilshop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tlu.edu.vn.ht63.mobilshop.model.Order;

@Database(entities = {Order.class}, version = 1)
public abstract class OrderDatabase extends RoomDatabase {
    private static volatile OrderDatabase INSTANCE;

    public abstract OrderDao orderDao();

    public static OrderDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (OrderDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    OrderDatabase.class, "orders-db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
