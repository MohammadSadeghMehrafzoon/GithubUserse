package ir.misterdeveloper.githubusers.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoDatabase {



    @Insert
    Long insertFavoriteUser(Favorite favorite);


    @Query("select * from  tbl_favorite ")
    List<Favorite> getFavoriteUser();

    @Query("DELETE FROM tbl_favorite WHERE id = :id")
    void deleteFavoriteById(int id);

}
