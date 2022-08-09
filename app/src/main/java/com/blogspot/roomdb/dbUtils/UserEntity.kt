package com.blogspot.roomdb.dbUtils

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val fristName: String,
    val lastName: String,
    val age:Int,
    @Embedded
    val address: Address?= null,
    val profleImage: Bitmap?= null


):Parcelable

@Parcelize
data class Address(
    var street: String?,
    var zipCode:String?

) : Parcelable