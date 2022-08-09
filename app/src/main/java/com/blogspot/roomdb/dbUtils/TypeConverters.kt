package com.blogspot.roomdb.dbUtils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class TypeConverters {

    @TypeConverter
    fun fromBitmap(bitMap: Bitmap): ByteArray {
        val outPutStream =ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.PNG,80,outPutStream)
        return outPutStream.toByteArray()

    }

    @TypeConverter
    fun toBitMap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }
}