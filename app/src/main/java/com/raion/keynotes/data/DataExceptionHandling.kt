package com.raion.keynotes.data

data class DataExceptionHandling<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)