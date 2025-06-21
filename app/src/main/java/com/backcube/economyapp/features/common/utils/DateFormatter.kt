package com.backcube.economyapp.features.common.utils

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

fun Instant.formatAsPeriodDate(): String =
    this.atZone(zoneId).format(periodFormatter)

fun Instant.formatAsTransactionDate(): String =
    this.atZone(zoneId).format(transactionFormatter)