package com.chibatching.kotprefsample

import android.content.Context
import com.jafir.encryptsupport.CipherAdapter
import javax.crypto.SecretKey

/**
 * Created by jafir on 2017/12/19.
 */
class SharedPrefCipherAdapter @Throws(Exception::class)
constructor(context: Context) : CipherAdapter {
    private val secretKey: SecretKey

    init {
        this.secretKey = AESUtil.generateKey(context)
    }

    override fun encrypt(raw: String): String {
        return AESUtil.execEncrypted(secretKey, raw)
    }

    override fun decrypt(encode: String): String {
        return AESUtil.execDecrypted(secretKey, encode)
    }
}