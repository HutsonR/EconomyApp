package com.backcube.data.local.impl.entities.converters

import androidx.room.TypeConverter
import com.backcube.data.local.impl.entities.transactions.AccountBriefSerialEntity
import kotlinx.serialization.json.Json

class AccountBriefEntityConverter {
    @TypeConverter
    fun fromAccount(account: AccountBriefSerialEntity): String {
        return Json.encodeToString(AccountBriefSerialEntity.serializer(), account)
    }

    @TypeConverter
    fun toAccount(json: String): AccountBriefSerialEntity {
        return Json.decodeFromString(AccountBriefSerialEntity.serializer(), json)
    }
}
