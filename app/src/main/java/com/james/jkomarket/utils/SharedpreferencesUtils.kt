package com.james.jkomarket.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedpreferencesUtils private constructor() {

    companion object {
        private val singleton = SharedpreferencesUtils()
        private lateinit var sharedPreferences: SharedPreferences
        @JvmStatic
        fun getInstance(context: Context): SharedpreferencesUtils {
            if (!::sharedPreferences.isInitialized) {
                synchronized(singleton::class.java) {
                    if (!::sharedPreferences.isInitialized) {
                        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                    }
                }
            }
            return singleton
        }

    }

    fun removePlaceObj(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun put(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun put(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    operator fun get(key: String, defValue: String?): String? {
        return sharedPreferences.getString(key, defValue)
    }

    operator fun get(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    operator fun get(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }
}