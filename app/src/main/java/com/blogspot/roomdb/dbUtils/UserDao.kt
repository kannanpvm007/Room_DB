package com.blogspot.roomdb.dbUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<UserEntity>>

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("delete from user_table")
    fun deleteAll()

    @Query("SELECT * FROM user_table WHERE fristName LIKE :name OR lastName LIKE :name")
     fun searchByString(name: String):Flow<List<UserEntity>>
}