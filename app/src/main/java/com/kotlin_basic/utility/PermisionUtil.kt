package com.only.restapi.Utility

import android.Manifest
import android.net.Uri

import android.content.Intent

import android.content.DialogInterface

import android.R

import android.app.Activity
import android.content.Context

import androidx.core.app.ActivityCompat

import android.os.Build

import android.content.pm.PackageManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

import androidx.core.content.ContextCompat

import java.util.ArrayList




class PermisionUtil {

    val REQUEST_ALL_PERMISSION = 1
    val REQUEST_FINE_LOCATION_CODE = 2
    val REQUEST_PHONE_STATE_CODE = 3
    val REQUEST_READ_CONTACT = 4
    val REQUEST_CAMERA = 5
    val REQUEST_READ_STORAGE = 6
    val REQUEST_WRITE_STORAGE = 7
    val REQUEST_AUDIO_RECORD = 8
    val REQUEST_CALL_APP = 9

    val ACCESS_FINE_LOCATION: String = Manifest.permission.ACCESS_FINE_LOCATION
    val ACCESS_COARSE_LOCATION: String = Manifest.permission.ACCESS_COARSE_LOCATION
    val ACCESS_READ_PHONE_STATE: String = Manifest.permission.READ_PHONE_STATE
    val ACCESS_READ_CONTACTS: String = Manifest.permission.READ_CONTACTS
    val ACCESS_CAMEARA: String = Manifest.permission.CAMERA
    val ACCESS_READ_STORAGE: String = Manifest.permission.READ_EXTERNAL_STORAGE
    val ACCESS_WRITE_STORAGE: String = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val ACCESS_AUDIO_RECORD: String = Manifest.permission.RECORD_AUDIO

    fun checkAllPermission(activity: Activity?): Boolean {
        return checkPermissionForAccess(activity!!, arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_READ_CONTACTS,
                ACCESS_READ_STORAGE,
                ACCESS_WRITE_STORAGE,
                ACCESS_CAMEARA,
                ACCESS_AUDIO_RECORD,
                ACCESS_READ_PHONE_STATE
            ), REQUEST_ALL_PERMISSION
        )
    }

    fun checkPermissionForAccess(activity: Context, requirePermission: Array<String>, requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionArray = ArrayList<String>()
            for (aRequirePermission in requirePermission) {
                if (ContextCompat.checkSelfPermission(activity, aRequirePermission)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionArray.add(aRequirePermission)
                }
            }
            if (permissionArray.size > 0) {
                val requestPermission: Array<String>
                requestPermission = permissionArray.toTypedArray()
                (activity as Activity).requestPermissions(requestPermission, requestCode)
            } else {
//                Toast.makeText(MainActivity.this, "Permission Granted M", Toast.LENGTH_SHORT).show();
                return true
            }
        } else {
//            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            return true
        }
        return false
    }

    fun checkPermissionForAccessShould(activity: Activity, requirePermission: String, requestCode: Int, infoMessage: String?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, requirePermission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requirePermission)) {
                    val alertBuilder: android.app.AlertDialog.Builder =
                        android.app.AlertDialog.Builder(activity)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle("Permission necessary")
                    alertBuilder.setMessage(infoMessage)
                    alertBuilder.setPositiveButton(
                        R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                activity.requestPermissions(arrayOf(requirePermission), requestCode)
                            }
                        })
                    val alert: android.app.AlertDialog? = alertBuilder.create()
                    alert?.show()
                } else {
                    activity.requestPermissions(arrayOf(requirePermission), requestCode)
                }
            } else {
//                Toast.makeText(MainActivity.this, "Permission Granted M", Toast.LENGTH_SHORT).show();
                return true
            }
        } else {
//            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            return true
        }
        return false
    }

    fun ifNotGrantedWithDontAsk(activity: Activity, requirePermission: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, requirePermission!!)
            ) {
                showPermissionDailog(activity)
            }
        }
    }

    fun ifNotGrantedWithDontAsk(activity: Activity, requestCode: Int, requirePermission: String, message: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, requirePermission)) {
                showPermissionDailog(activity)
            } else {
                checkPermissionForAccessShould(activity, requirePermission, requestCode, message)
            }
        }
    }


    fun showPermissionDailog(activity: Activity) {
        val alertBuilder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Permission necessary")
        alertBuilder.setMessage("message")
        alertBuilder.setPositiveButton(
            R.string.yes,
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)
                /*startActivityForResult(intent, REQUEST_PERMISSION_SETTING);*/
            })
        val alert: android.app.AlertDialog? = alertBuilder.create()
        alert?.show()
    }
}