package tlu.edu.vn.ht63.mobilshop.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tlu.edu.vn.ht63.mobilshop.model.Product;

@Dao
public interface ProductDAO {
    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM product")
    List<Product> getListProduct();
}
