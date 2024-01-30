package com.raion.keynotes.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun processHoursSinceLogin(tokenTimeStamp: String): Long{
    val formatter            = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val parseTokenTimeStamp  = LocalDateTime.parse(tokenTimeStamp, formatter)
    val currentTime          = LocalDateTime.now()
    var durationD            = Duration.between(parseTokenTimeStamp, currentTime)
    var hoursSinceLastLogin  = durationD.toHours()
    return hoursSinceLastLogin
}