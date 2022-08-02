package com.blogspot.roomdb.dbUtils

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Query("SELECT * FROM USER_TABLE ORDER BY id ASC")
    fun readAllData(): LiveData<List<UserEntity>>

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("delete from USER_TABLE")
    fun deleteAll()

}