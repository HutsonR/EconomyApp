package com.backcube.data.local.impl.entities.converters

import androidx.room.TypeConverter
import com.backcube.data.local.impl.entities.accounts.StatSerialEntity
import kotlinx.serialization.json.Json

class StatListConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromList(value: List<StatSerialEntity>): String =
        json.encodeToString(value)

    @TypeConverter
    fun toList(value: String): List<StatSerialEntity> =
        json.decodeFromString(value)
}
