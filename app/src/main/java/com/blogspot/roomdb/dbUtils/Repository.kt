package com.blogspot.roomdb.dbUtils

import android.util.Log
import androidx.lifecycle.LiveData


class Repository(private val user: UserDao) {
    fun readAll() : LiveData<List<UserEntity>>
    {
        val w = user.readAllData()
        println("read data--->"+w.value?.size)
        return w
    }

    suspend fun addUser(userEntity: UserEntity) {
        user.addUser(userEntity)
    }

    suspend fun updateUser(userEntity: UserEntity) {
        Log.d("system", "updateUser:--->${userEntity} ")
        user.updateUser(userEntity)
    }
    suspend fun deleteUser(userEntity: UserEntity){
        user.updateUser(userEntity)
    }

}