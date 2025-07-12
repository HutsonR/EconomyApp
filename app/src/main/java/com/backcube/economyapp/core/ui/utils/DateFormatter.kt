package com.backcube.economyapp.core.ui.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val zoneId = ZoneId.systemDefault()

private val periodFormatter = DateTimeFormatter
    .ofPattern("d MMMM yyyy")
    .withLocale(Locale("ru"))

private val transactionFormatter = DateTimeFormatter
    .ofPattern("d MMMM HH:mm")
    .withLocale(Locale("ru"))

private val dateFormatter = DateTimeFormatter
    .ofPattern("dd.MM.yyyy")
    .withLocale(Locale("ru"))

private val timeFormatter = DateTimeFormatter
    .ofPattern("HH:mm")
    .withLocale(Locale("ru"))


fun Instant.formatAsPeriodDate(): String =
    this.atZone(zoneId).format(periodFormatter)

fun Instant.formatAsTransactionDate(): String =
    this.atZone(zoneId).format(transactionFormatter)

fun Instant.formatAsSimpleDate(): String =
    this.atZone(zoneId).format(dateFormatter)

fun Instant.formatAsSimpleTime(): String =
    this.atZone(zoneId).format(timeFormatter)

fun Instant.toHourMinute(): Pair<Int, Int> {
    val zonedDateTime = this.atZone(zoneId)
    return zonedDateTime.hour to zonedDateTime.minute
}