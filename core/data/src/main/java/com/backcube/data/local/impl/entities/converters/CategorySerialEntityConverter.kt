package com.backcube.data.local.impl.entities.converters

import androidx.room.TypeConverter
import com.backcube.data.local.impl.entities.transactions.CategorySerialEntity
import kotlinx.serialization.json.Json

class CategorySerialEntityConverter {
    @TypeConverter
    fun fromCategorySerial(category: CategorySerialEntity): String {
        return Json.encodeToString(CategorySerialEntity.serializer(), category)
    }

    @TypeConverter
    fun toCategorySerial(json: String): CategorySerialEntity {
        return Json.decodeFromString(CategorySerialEntity.serializer(), json)
    }
}
