package com.backcube.data.remote.impl.repositories.mappers

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val apiDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun Instant.toApiDate(): String {
    return this.atZone(ZoneId.systemDefault()).toLocalDate().format(apiDateFormatter)
}
