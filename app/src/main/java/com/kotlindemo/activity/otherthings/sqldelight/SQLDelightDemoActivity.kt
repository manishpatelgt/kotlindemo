package com.kotlindemo.activity.otherthings.sqldelight

import android.os.Bundle
import android.util.Log
import com.kotlindemo.Database
import com.kotlindemo.ItemInCart
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.android.synthetic.main.activity_sqldelight_demo.*

/**
 * Created by Manish Patel on 8/28/2019.
 */
//https://handstandsam.com/2019/08/23/sqldelight-1-x-quick-start-guide-for-android/
//https://github.com/square/sqldelight
//https://johnoreilly.dev/posts/sqldelight-multiplatform/

class SQLDelightDemoActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqldelight_demo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Don't ever put db code in your activity, but this is a quick start guide
        val androidSqlDriver = AndroidSqliteDriver(
            schema = Database.Schema,
            context = applicationContext,
            name = "items.db"
        )

        val queries = Database(androidSqlDriver).itemInCartEntityQueries

        val itemsBefore: List<ItemInCart> = queries.selectAll().executeAsList()
        Log.d("ItemDatabase", "Items Before: $itemsBefore")

        for (i in 1..3) {
            queries.insertOrReplace(
                label = "Item $i",
                image = "https://localhost/item$i.png",
                quantity = i.toLong(),
                link = null
            )
        }

        val itemsAfter: List<ItemInCart> = queries.selectAll().executeAsList()
        Log.d("ItemDatabase", "Items After: $itemsAfter")
    }
}