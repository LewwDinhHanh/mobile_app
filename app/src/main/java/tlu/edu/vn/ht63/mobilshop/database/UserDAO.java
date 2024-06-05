package tlu.edu.vn.ht63.mobilshop.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import tlu.edu.vn.ht63.mobilshop.model.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User authenticate(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username")
    User checkUser(String username);

    @Query("SELECT * FROM users WHERE employeename = :employeename")
    User checkUserByEmployeeID(String employeename);
}
