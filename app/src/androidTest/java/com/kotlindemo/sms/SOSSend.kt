package com.kotlindemo.sms

import android.util.Base64
import androidx.test.filters.SmallTest
import com.kotlindemo.appconstants.Consts
import okhttp3.internal.http2.Http2Reader.Companion.logger
import org.junit.Test
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * Created by Manish Patel on 6/21/2019.
 */
@SmallTest
class SOSSend {

    /**
     * KEY_APP_MASTER key used for encrypt data.
     */
    val KEY_APP_MASTER = "adminadmin123456"
    private val inputData =  "1;1044;4774;23.0504333,72.51734;MEDICAL;1558506367"

    @Test
    fun sendSMS() {
        val encryptStr = encrypt2(inputData)
        println("encrypt string length ${encryptStr.length}")
        println("encrypt string $encryptStr")
    }

    var secretKey: SecretKeySpec? = null
    var key: ByteArray? = null

    fun encrypt2(stringToEncrypt: String): String {

        var sha: MessageDigest? = null
        try {
            key = KEY_APP_MASTER.toByteArray(charset("UTF-8"))
            sha = MessageDigest.getInstance("SHA-1")
            key = sha.digest(key)
            key = Arrays.copyOf(key, 16) // use only first 128 bit
            secretKey = SecretKeySpec(key, "AES")
            val ivParameterSpec = IvParameterSpec(KEY_APP_MASTER.toByteArray(charset("UTF-8")))

            try {
                val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
                val data = cipher.doFinal(stringToEncrypt.toByteArray(charset("UTF-8")))
                return Base64.encodeToString(data, Base64.NO_PADDING)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error while encrypting: $e")
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return ""
    }


    private val algorithm = "AES/CBC/NoPadding"

    private val keyValue = byteArrayOf(
        'a'.toByte(),
        'd'.toByte(),
        'm'.toByte(),
        'i'.toByte(),
        'n'.toByte(),
        'a'.toByte(),
        'd'.toByte(),
        'm'.toByte(),
        'i'.toByte(),
        'n'.toByte(),
        '1'.toByte(),
        '2'.toByte(),
        '3'.toByte(),
        '4'.toByte(),
        '5'.toByte(),
        '6'.toByte()
    )

    private val ivValue = byteArrayOf(
        'a'.toByte(),
        'd'.toByte(),
        'm'.toByte(),
        'i'.toByte(),
        'n'.toByte(),
        'a'.toByte(),
        'd'.toByte(),
        'm'.toByte(),
        'i'.toByte(),
        'n'.toByte(),
        '1'.toByte(),
        '2'.toByte(),
        '3'.toByte(),
        '4'.toByte(),
        '5'.toByte(),
        '6'.toByte()
    )

    private val ivspec = IvParameterSpec(ivValue)
    private val keyspec = SecretKeySpec(keyValue, "AES")

    fun encrypt3(plainText: ByteArray): String {
        val c = Cipher.getInstance(algorithm)
        c.init(Cipher.ENCRYPT_MODE, keyspec, ivspec)
        val encVal = c.doFinal(plainText)
        return Base64.encodeToString(encVal, Base64.NO_PADDING)
    }

    fun encrypt(plainText: String): String {
        val c = Cipher.getInstance(algorithm)
        c.init(Cipher.ENCRYPT_MODE, keyspec, ivspec)
        val encVal = c.doFinal(plainText.toByteArray())
        return Base64.encodeToString(encVal, Base64.NO_PADDING)
    }
}
