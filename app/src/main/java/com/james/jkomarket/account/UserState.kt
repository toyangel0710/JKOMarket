package com.james.jkomarket.account

import android.content.Context
import com.james.jkomarket.utils.SharedpreferencesUtils

class UserState (context: Context){

    private val spUtils = SharedpreferencesUtils.getInstance(context)
    /**
     * 是否已登入
     */
    var isLoggin: Boolean
        get() = spUtils.get("isLoggin", false)
        set(value) = spUtils.put("isLoggin", value)
    /**
     * 用戶名
     */
    var userName: String?
        get() = spUtils.get("userName", null)
        set(value) = spUtils.put("userName", value)

    /**
     * 用戶id
     */
    var id: Int
        get() = spUtils.get("userId", -1)
        set(value) = spUtils.put("userId", value)

    // TODO Implement another user info here in the future.

}