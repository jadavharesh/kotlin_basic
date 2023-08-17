package com.only.restapi.Utility

import android.content.Context
import java.io.IOException

import java.io.File

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat


class FileCompressor {
    private var maxWidth = 612
    private var maxHeight = 816
    private var compressFormat = CompressFormat.JPEG
    private var quality = 80
    private var destinationDirectoryPath: String? = null

    fun FileCompressor(context: Context) { destinationDirectoryPath =
            context.getCacheDir().getPath().toString() + File.separator + "images"
    }

    fun setMaxWidth(maxWidth: Int): FileCompressor? {
        this.maxWidth = maxWidth
        return this
    }

    fun setMaxHeight(maxHeight: Int): FileCompressor? {
        this.maxHeight = maxHeight
        return this
    }

    fun setCompressFormat(compressFormat: CompressFormat): FileCompressor? {
        this.compressFormat = compressFormat
        return this
    }

    fun setQuality(quality: Int): FileCompressor? {
        this.quality = quality
        return this
    }

    fun setDestinationDirectoryPath(destinationDirectoryPath: String?): FileCompressor? {
        this.destinationDirectoryPath = destinationDirectoryPath
        return this
    }

    @Throws(IOException::class)
    fun compressToFile(imageFile: File): File? {
        return compressToFile(imageFile, imageFile.name)
    }

    @Throws(IOException::class)
    fun compressToFile(imageFile: File?, compressedFileName: String): File? {
        return ImageUtils.compressImage(imageFile!!, maxWidth, maxHeight, compressFormat, quality, destinationDirectoryPath + File.separator + compressedFileName)
    }

    @Throws(IOException::class)
    fun compressToBitmap(imageFile: File?): Bitmap? {
        return ImageUtils.decodeSampledBitmapFromFile(imageFile!!, maxWidth, maxHeight)
    }
}