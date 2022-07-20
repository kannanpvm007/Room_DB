package com.blogspot.roomdb.dbUtils

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val fristName: String,
    val lastName: String,
    val age:Int,
):Parcelable
