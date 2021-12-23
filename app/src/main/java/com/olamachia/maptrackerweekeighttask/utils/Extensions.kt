package com.olamachia.maptrackerweekeighttask.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun Activity.checkPermissionExt(): Boolean {
    val finePermission = Manifest.permission.ACCESS_FINE_LOCATION
    val coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION

    return (ActivityCompat.checkSelfPermission(
        this, finePermission
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this, coarsePermission
    ) == PackageManager.PERMISSION_GRANTED)
}

fun Activity.requestPermissions(requestCode: Int) {

    val finePermission = Manifest.permission.ACCESS_FINE_LOCATION
    val coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION

    if (ActivityCompat.checkSelfPermission(
            this, finePermission
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this, coarsePermission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(finePermission, coarsePermission),
            requestCode
        )
        return
    }


}