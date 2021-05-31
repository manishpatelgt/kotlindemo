package com.kotlindemo.json

import androidx.test.filters.SmallTest
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.junit.Test
import com.kotlindemo.application.DemoApplication.Companion.context

/**
 * Created by Manish Patel on 5/7/2019.
 */

/**
 * Instrumented test for a json parser. It uses org.json classes from the Android SDK
 */

//http://kotlination.com/kotlin/kotlin-convert-object-to-from-json-gson
//https://www.callicoder.com/kotlin-type-checks-smart-casts/

@SmallTest
class JsonParserTest {

    @Test
    fun readAppAsset() {

        /*val JsonString = context.assets.open("AppConfig.json").bufferedReader().use {
            it.readText()
        }
        println("Json String ${JsonString}")*/

        //var sos: SOS? = Klaxon().parse<SOS>(json_string)
        //assertEquals("main myAsset", json_string)

        /*val gson = Gson()
        val sos: SOS = gson.fromJson(JsonString, SOS::class.java)

        println("Medical: ${sos.Medical}")
        println("Security: ${sos.Security}")
        println("Other: ${sos.Other}")
        println("SMSNumber: ${sos.SMSNumber}")*/

        //val medical: String = Klaxon().toJsonString(sos.Medical)
        //println("medical List: $medical")

        /*val result = Klaxon().parse<AppConfiguration>(JsonString)
        println("periodicUpdates: ${result?.periodicUpdates.toString()}")
        println("Event: ${result?.EventCode.toString()}")*/

        val JsonString = context.assets.open("MessageBody.json").bufferedReader().use {
            it.readText()
        }
        println("Json String ${JsonString}")

        val result = Klaxon().parse<ConversationMessageBody>(JsonString)
        println("conversationBody: $result")

        /*val result = Klaxon().parse<SOS>(JsonString)
        println("Medical: ${result?.Medical}")
        println("Security: ${result?.Security}")
        println("Other: ${result?.Other}")
        println("SMSNumber: ${result?.SMSNumber}")

        val medicalListJson: List<Any>? = result?.Medical
        println("medical List JSON: $medicalListJson")

        val number: Any? = medicalListJson?.get(0)
        val str: String? = number as String? // Works
        println("Calling Number: $str")*/

        /*val medicalListJson: List<Any>? = Klaxon().parseArray<String>(sos.Medical.toString())
        println("medical List JSON: $medicalListJson")

        val number: Any? = medicalListJson?.get(0)
        val str: Int? = number as Int? // Works
        println("Calling Number: $str")*/

        //assertEquals("Json", sos.toString())
    }

    data class SOS(
        @SerializedName("medical")
        var Medical: List<String>,
        @SerializedName("security")
        var Security: List<String>,
        @SerializedName("other")
        var Other: List<String>,
        @SerializedName("smsNumber")
        var SMSNumber: String? = ""
    )

    data class AppConfiguration(
        var periodicUpdates: HHUpdatesConfiguration? = null,
        var EventCode: EventCodeConfiguration? = null,
        var allowedlogin: LoginConfiguration? = null,
        var showSplashIcon: Boolean = false
    )

    data class HHUpdatesConfiguration(
        var location: Int = 0,
        var battery: Int = 0
    )

    data class EventCodeConfiguration(
        var eventMinChars: Int = 0,
        var loginMinChars: Int = 0,
        var boundingChar: String = "",
        var allowQRscan: Boolean = false
    )

    data class LoginConfiguration(
        var loginCode: Boolean = false,
        var nameAndPwd: Boolean = false,
        var selfLogin: Boolean = false
    )


    data class ConversationMessageBody(
        val conversationsID: Int,
        val fromItemType: String,
        val fromItemId: Int = 0,
        val toItemType: String,
        val toItemId: Int = 0,
        val conversationJson: String,
        val fileID: Int? = null,
        val fileName: String? = null,
        val chatID: String,
        val conversationType: String,
        val parentChatID: Int? = null,
        val isActive: Int,
        val eventID: Int,
        val timeStamp: Long
    )

}