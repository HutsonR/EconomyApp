package com.backcube.data.local.impl.entities.converters

import androidx.room.TypeConverter
import com.backcube.data.local.impl.entities.transactions.CategorySerialEntity
import kotlinx.serialization.json.Json

internal class CategorySerialEntityConverter {
    @TypeConverter
    internal fun fromCategorySerial(category: CategorySerialEntity): String {
        return Json.encodeToString(CategorySerialEntity.serializer(), category)
    }

    @TypeConverter
    internal fun toCategorySerial(json: String): CategorySerialEntity {
        return Json.decodeFromString(CategorySerialEntity.serializer(), json)
    }
}
