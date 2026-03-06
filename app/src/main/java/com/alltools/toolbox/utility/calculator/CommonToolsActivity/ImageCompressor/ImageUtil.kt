package com.alltools.toolbox.utility.calculator.CommonToolsActivity.ImageCompressor

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ImageUtil {
    @JvmStatic
    @Throws(IOException::class)
    fun compressImage(
        file: File,
        i: Int,
        i2: Int,
        compressFormat: CompressFormat,
        i3: Int,
        str: String
    ): File {
        val parentFile = File(str).getParentFile()
        if (!parentFile!!.exists()) {
            parentFile.mkdirs()
        }
        val fileOutputStream = FileOutputStream(str)
        try {
            decodeSampledBitmapFromFile(file, i, i2).compress(compressFormat, i3, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            return File(str)
        } catch (unused: Throwable) {
            fileOutputStream.flush()
            fileOutputStream.close()
            return parentFile
        }
    }

    @Throws(IOException::class)
    fun decodeSampledBitmapFromFile(file: File, i: Int, i2: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.getAbsolutePath(), options)
        options.inSampleSize = calculateInSampleSize(options, i, i2)
        options.inJustDecodeBounds = false
        val decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options)
        val attributeInt =
            ExifInterface(file.getAbsolutePath()).getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
        val matrix = Matrix()
        if (attributeInt == 6) {
            matrix.postRotate(90.0f)
        } else if (attributeInt == 3) {
            matrix.postRotate(180.0f)
        } else if (attributeInt == 8) {
            matrix.postRotate(270.0f)
        }
        return Bitmap.createBitmap(
            decodeFile,
            0,
            0,
            decodeFile.getWidth(),
            decodeFile.getHeight(),
            matrix,
            true
        )
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, i: Int, i2: Int): Int {
        val i3 = options.outHeight
        val i4 = options.outWidth
        var i5 = 1
        if (i3 > i2 || i4 > i) {
            val i6 = i3 / 2
            val i7 = i4 / 2
            while (i6 / i5 >= i2 && i7 / i5 >= i) {
                i5 *= 2
            }
        }
        return i5
    }
}
