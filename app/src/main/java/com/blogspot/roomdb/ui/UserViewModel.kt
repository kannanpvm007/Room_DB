package com.blogspot.roomdb.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.blogspot.roomdb.dbUtils.Repository
import com.blogspot.roomdb.dbUtils.UserDao
import com.blogspot.roomdb.dbUtils.UserDb
import com.blogspot.roomdb.dbUtils.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(aplication:Application) : AndroidViewModel(aplication){
     val  readAllData : LiveData<List<UserEntity>>
    private val repository:Repository

    init {
        val userDao=UserDb.getUserDB(aplication).userDao()
        repository= Repository(userDao)
        readAllData= repository.readAll()
    }

    fun addUser(userEntity: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(userEntity)
        }
    }
    fun updateUser(userEntity: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUser(userEntity)
        }
    }
    fun deleteUser(userEntity: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteUser(userEntity)
        }
    }

}