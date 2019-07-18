package com.kotlindemo.activity.otherthings.rxjava.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Manish Patel on 7/17/2019.
 */
@Entity(tableName = "users")
data class User(

    @PrimaryKey
    @ColumnInfo(name = "userid")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "username")
    val userName: String
)