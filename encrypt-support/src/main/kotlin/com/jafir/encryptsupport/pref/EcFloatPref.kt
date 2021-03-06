package com.jafir.encryptsupport.pref

import android.content.SharedPreferences
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.pref.AbstractPref
import com.jafir.encryptsupport.cipherAdapter
import kotlin.reflect.KProperty


 class EcFloatPref(val default: Float, val key: String?) : AbstractPref<Float>() {

    override fun getFromPreference(property: KProperty<*>, preference: SharedPreferences): Float {
        if (Kotpref.cipherAdapter != null) {
            try {
                return Kotpref.cipherAdapter!!.decrypt(preference.getString(key ?: property.name, default.toString())).toFloat()
            } catch (e: Exception) {
                return default
            }
        }
        return default
    }

    override fun setToPreference(property: KProperty<*>, value: Float, preference: SharedPreferences) {
        if (Kotpref.cipherAdapter != null) {
            try {
                preference.edit().putString(key ?: property.name, Kotpref.cipherAdapter!!.encrypt(value.toString())).apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun setToEditor(property: KProperty<*>, value: Float, editor: SharedPreferences.Editor) {
        if (Kotpref.cipherAdapter != null) {
            try {
                editor.putString(key ?: property.name, Kotpref.cipherAdapter!!.encrypt(value.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
