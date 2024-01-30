package com.raion.keynotes.util

import com.raion.keynotes.model.GetUserDetailResponse

fun processUserDetail(userDetail: GetUserDetailResponse?): List<String>{
    var userId: String   = "NO INTERNET"
    var userName: String = "NO INTERNET"
    var password: String = "NO INTERNET"

    if(userDetail != null){
        userId   = userDetail.data.id
        userName = userDetail.data.name
        password = userDetail.data.password
    }
    var listOfUserDetail = listOf(userName, userId, password)
    return listOfUserDetail
}