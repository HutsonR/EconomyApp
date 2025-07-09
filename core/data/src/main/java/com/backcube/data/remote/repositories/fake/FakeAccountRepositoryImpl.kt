package com.backcube.data.remote.repositories.fake

import com.backcube.data.remote.repositories.fake.utils.LOADING_DELAY
import com.backcube.domain.models.accounts.AccountCreateRequestModel
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.domain.models.accounts.CurrencyIsoCode
import com.backcube.domain.models.accounts.StatModel
import com.backcube.domain.repositories.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.time.Instant
import javax.inject.Inject

class FakeAccountRepositoryImpl @Inject constructor() : AccountRepository {
    override suspend fun getAccounts(): List<AccountModel> = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun createAccount(request: AccountCreateRequestModel): AccountModel = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountById(id: Int): AccountResponseModel? = withContext(Dispatchers.IO) {
        delay(LOADING_DELAY)
        return@withContext accounts.find { it.id == id }
    }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestModel
    ): AccountModel = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseModel = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    val accounts = listOf(
        AccountResponseModel(
            id = 1,
            name = "Основной счёт",
            balance = BigDecimal("-12500.00"),
            currency = CurrencyIsoCode.RUB,
            incomeStats = listOf(
                StatModel(
                    categoryId = 1,
                    categoryName = "Зарплата",
                    emoji = "💰",
                    amount = BigDecimal("10000.00")
                ),
                StatModel(
                    categoryId = 2,
                    categoryName = "Фриланс",
                    emoji = "🧑‍💻",
                    amount = BigDecimal("5000.00")
                )
            ),
            expenseStats = listOf(
                StatModel(
                    categoryId = 3,
                    categoryName = "Продукты",
                    emoji = "🛒",
                    amount = BigDecimal("2500.00")
                )
            ),
            createdAt = Instant.parse("2025-06-13T10:00:00.000Z"),
            updatedAt = Instant.parse("2025-06-14T02:24:39.480Z")
        ),
        AccountResponseModel(
            id = 2,
            name = "Копилка",
            balance = BigDecimal("70000.00"),
            currency = CurrencyIsoCode.RUB,
            incomeStats = listOf(
                StatModel(
                    categoryId = 4,
                    categoryName = "Подарок",
                    emoji = "🎁",
                    amount = BigDecimal("20000.00")
                )
            ),
            expenseStats = listOf(
                StatModel(
                    categoryId = 5,
                    categoryName = "Путешествия",
                    emoji = "✈️",
                    amount = BigDecimal("5000.00")
                ),
                StatModel(
                    categoryId = 6,
                    categoryName = "Гаджеты",
                    emoji = "📱",
                    amount = BigDecimal("3000.00")
                )
            ),
            createdAt = Instant.parse("2025-06-01T09:15:00.000Z"),
            updatedAt = Instant.parse("2025-06-14T02:24:39.480Z")
        )
    )

}
