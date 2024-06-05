package tlu.edu.vn.ht63.mobilshop.database;

import androidx.room.Dao;
import androidx.room.Insert;

import tlu.edu.vn.ht63.mobilshop.model.Order;

@Dao
public interface OrderDao {
    @Insert
    long insert(Order order);
}

