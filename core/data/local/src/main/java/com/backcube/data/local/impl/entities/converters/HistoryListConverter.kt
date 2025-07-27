package com.backcube.data.local.impl.entities.converters

import androidx.room.TypeConverter
import com.backcube.data.local.impl.entities.accounts.HistoryModelEntity
import kotlinx.serialization.json.Json

class HistoryListConverter {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromList(list: List<HistoryModelEntity>): String =
        json.encodeToString(list)

    @TypeConverter
    fun toList(data: String): List<HistoryModelEntity> =
        json.decodeFromString(data)
}
