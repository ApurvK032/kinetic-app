package com.apurvk.kinetic.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

fun todayKey(): String = LocalDate.now().format(dateFormatter)
