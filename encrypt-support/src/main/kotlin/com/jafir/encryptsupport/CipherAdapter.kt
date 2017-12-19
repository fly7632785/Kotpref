package com.jafir.encryptsupport

/**
 * Created by jafir on 2017/12/19.
 */
interface CipherAdapter {
    fun encrypt(raw: String): String
    fun decrypt(raw: String): String
}