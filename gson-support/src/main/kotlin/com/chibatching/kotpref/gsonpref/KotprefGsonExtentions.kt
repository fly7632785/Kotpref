package com.chibatching.kotpref.gsonpref

import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.KotprefModel
import com.google.gson.Gson
import kotlin.properties.ReadWriteProperty


/**
 * Gson object to serialize and deserialize delegated property
 */
var Kotpref.gson: Gson?
    get() {
        return KotprefGsonHolder.gson
    }
    set(value) {
        KotprefGsonHolder.gson = value
    }

/**
 * Delegate shared preferences property serialized and deserialized by gson
 * @param default default gson object value
 * @param key custom preferences key
 */
inline fun <reified T : Any> KotprefModel.gsonPref(default: T, key: String? = null)
        : ReadWriteProperty<KotprefModel, T> = GsonPref(T::class, default, key)

/**
 * Delegate shared preferences property serialized and deserialized by gson
 * @param default default gson object value
 * @param key custom preferences key resource id
 */
inline fun <reified T : Any> KotprefModel.gsonPref(default: T, key: Int)
        : ReadWriteProperty<KotprefModel, T> = GsonPref(T::class, default, context.getString(key))

/**
 * Delegate shared preferences property serialized and deserialized by gson
 * @param default default gson object value
 * @param key custom preferences key
 */
inline fun <reified T : Any> KotprefModel.gsonNullablePref(default: T? = null, key: String? = null)
        : ReadWriteProperty<KotprefModel, T?> = GsonNullablePref(T::class, default, key)

/**
 * Delegate shared preferences property serialized and deserialized by gson
 * @param default default gson object value
 * @param key custom preferences key resource id
 */
inline fun <reified T : Any> KotprefModel.gsonNullablePref(default: T? = null, key: Int)
        : ReadWriteProperty<KotprefModel, T?> = GsonNullablePref(T::class, default, context.getString(key))
