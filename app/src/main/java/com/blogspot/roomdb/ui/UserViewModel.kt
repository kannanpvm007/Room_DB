package com.blogspot.roomdb.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blogspot.roomdb.dbUtils.Repository
import com.blogspot.roomdb.dbUtils.UserDao
import com.blogspot.roomdb.dbUtils.UserDb
import com.blogspot.roomdb.dbUtils.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow


class UserViewModel(aplication:Application) : AndroidViewModel(aplication){
     lateinit var readAllData : LiveData<List<UserEntity>>
    private val repository:Repository

    init {
        val userDao=UserDb.getUserDB(aplication).userDao()
        repository= Repository(userDao)
        //readAllData= repository.readAll()
        readAllData()
    }
    fun  readAllData(){
        Log.d("TAG", "readAllData:read-- ----------------------->")

        readAllData= repository.readAll()
    }

    fun addUser(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userEntity)
        }
    }

    fun updateUser(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(userEntity)
        }
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userEntity)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserAll()
        }
    }

    fun searchByString(string: String): LiveData<List<UserEntity>>? {


        try {

            val filterdata = repository.searchByString(string)
          return  if (filterdata != null) {

                 filterdata.asLiveData()
            } else {
                null
            }


        } catch (e: Exception) {
            Log.e("TAG", "exeSer: ${e.localizedMessage}")

        }
        return  null

    }

}