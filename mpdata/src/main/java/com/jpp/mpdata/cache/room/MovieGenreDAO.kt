
package com.jpp.mpdata.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieGenreDAO {

    @Query("select * from movie_genres where duedate >= :nowDate")
    fun getMovieGenres(nowDate: Long): List<DBMovieGenre>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieGenres(genres: List<DBMovieGenre>)
}
