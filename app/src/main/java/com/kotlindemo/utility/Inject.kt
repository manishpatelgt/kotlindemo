package com.kotlindemo.utility

import android.content.Context
import android.content.pm.PackageManager


/**
 * Created by Manish Patel on 9/6/2019.
 */
object Inject {

    fun getBroadcastTitleList(): ArrayList<String> {
        return arrayListOf("Emergency at the event area. Marshals please reach fast at desk","RAIN INCOMING FROM NORTHWEST INCLUDING","Information Alert")
    }

    var permissionList = emptyArray<String>()

    fun RetrivePermissionList(context: Context) : Array<String>{

        //App crashes here on requesting permission list
        permissionList = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions

        return permissionList
    }
}
