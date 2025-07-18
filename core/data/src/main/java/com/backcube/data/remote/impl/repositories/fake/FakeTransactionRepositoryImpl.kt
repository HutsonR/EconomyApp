package com.backcube.data.remote.impl.repositories.fake

//class FakeTransactionRepositoryImpl @Inject constructor(): TransactionRepository {
//    override suspend fun createTransaction(request: TransactionRequestModel): TransactionModel = withContext(Dispatchers.IO) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getTransactionById(id: Int): TransactionResponseModel = withContext(Dispatchers.IO) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun updateTransaction(
//        id: Int,
//        request: TransactionRequestModel
//    ): TransactionResponseModel = withContext(Dispatchers.IO) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun deleteTransaction(id: Int): Boolean = withContext(Dispatchers.IO) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getAccountTransactions(
//        accountId: Int,
//        startDate: Instant?,
//        endDate: Instant?
//    ): List<TransactionResponseModel> = withContext(Dispatchers.IO) {
//        delay(LOADING_DELAY)
//        return@withContext transactions
//    }
//
//    private val transactions = listOf(
//        TransactionResponseModel(
//            id = 1,
//            account = AccountBriefModel(
//                id = 1,
//                name = "Основной счёт",
//                balance = BigDecimal("1000.00"),
//                currency = CurrencyIsoCode.RUB
//            ),
//            category = CategoryModel(
//                id = 1,
//                name = "Зарплата",
//                emoji = "💰",
//                isIncome = true
//            ),
//            amount = BigDecimal("500.00"),
//            transactionDate = Instant.parse("2025-06-14T00:10:30.400Z"),
//            comment = "Зарплата за месяц",
//            createdAt = Instant.parse("2025-06-14T00:10:30.400Z"),
//            updatedAt = Instant.parse("2025-06-14T00:10:30.400Z")
//        ),
//        TransactionResponseModel(
//            id = 2,
//            account = AccountBriefModel(
//                id = 1,
//                name = "Основной счёт",
//                balance = BigDecimal("1500.00"),
//                currency = CurrencyIsoCode.RUB
//            ),
//            category = CategoryModel(
//                id = 2,
//                name = "Еда",
//                emoji = "🍕",
//                isIncome = false
//            ),
//            amount = BigDecimal("300.00"),
//            transactionDate = Instant.parse("2025-06-14T08:15:00.000Z"),
//            comment = "Ужин в ресторане",
//            createdAt = Instant.parse("2025-06-14T08:15:00.000Z"),
//            updatedAt = Instant.parse("2025-06-14T08:15:00.000Z")
//        ),
//        TransactionResponseModel(
//            id = 3,
//            account = AccountBriefModel(
//                id = 1,
//                name = "Основной счёт",
//                balance = BigDecimal("1200.00"),
//                currency = CurrencyIsoCode.RUB
//            ),
//            category = CategoryModel(
//                id = 3,
//                name = "Транспорт",
//                emoji = "🚗",
//                isIncome = false
//            ),
//            amount = BigDecimal("150.00"),
//            transactionDate = Instant.parse("2025-06-14T10:45:00.000Z"),
//            comment = "Заправка",
//            createdAt = Instant.parse("2025-06-14T10:45:00.000Z"),
//            updatedAt = Instant.parse("2025-06-14T10:45:00.000Z")
//        ),
//        TransactionResponseModel(
//            id = 4,
//            account = AccountBriefModel(
//                id = 1,
//                name = "Основной счёт",
//                balance = BigDecimal("1050.00"),
//                currency = CurrencyIsoCode.RUB
//            ),
//            category = CategoryModel(
//                id = 4,
//                name = "Подарки",
//                emoji = "🎁",
//                isIncome = false
//            ),
//            amount = BigDecimal("2000.00"),
//            transactionDate = Instant.parse("2025-06-14T12:30:00.000Z"),
//            comment = "Подарок другу",
//            createdAt = Instant.parse("2025-06-14T12:30:00.000Z"),
//            updatedAt = Instant.parse("2025-06-14T12:30:00.000Z")
//        ),
//        TransactionResponseModel(
//            id = 5,
//            account = AccountBriefModel(
//                id = 1,
//                name = "Основной счёт",
//                balance = BigDecimal("850.00"),
//                currency = CurrencyIsoCode.RUB
//            ),
//            category = CategoryModel(
//                id = 5,
//                name = "Инвестиции",
//                emoji = "📈",
//                isIncome = false
//            ),
//            amount = BigDecimal("400.00"),
//            transactionDate = Instant.parse("2025-06-14T15:00:00.000Z"),
//            comment = "Купил акции",
//            createdAt = Instant.parse("2025-06-14T15:00:00.000Z"),
//            updatedAt = Instant.parse("2025-06-14T15:00:00.000Z")
//        ),
//        TransactionResponseModel(
//            id = 6,
//            account = AccountBriefModel(
//                id = 1,
//                name = "Основной счёт",
//                balance = BigDecimal("450.00"),
//                currency = CurrencyIsoCode.RUB
//            ),
//            category = CategoryModel(
//                id = 6,
//                name = "Фриланс",
//                emoji = "🧑‍💻",
//                isIncome = true
//            ),
//            amount = BigDecimal("700.00"),
//            transactionDate = Instant.parse("2025-06-13T09:20:00.000Z"),
//            comment = null,
//            createdAt = Instant.parse("2025-06-13T09:20:00.000Z"),
//            updatedAt = Instant.parse("2025-06-13T09:20:00.000Z")
//        ),
//        TransactionResponseModel(
//            id = 7,
//            account = AccountBriefModel(
//                id = 1,
//                name = "Основной счёт",
//                balance = BigDecimal("1150.00"),
//                currency = CurrencyIsoCode.RUB
//            ),
//            category = CategoryModel(
//                id = 7,
//                name = "Развлечения",
//                emoji = "🎬",
//                isIncome = false
//            ),
//            amount = BigDecimal("250.00"),
//            transactionDate = Instant.parse("2025-06-13T20:00:00.000Z"),
//            comment = "Поход в кино",
//            createdAt = Instant.parse("2025-06-13T20:00:00.000Z"),
//            updatedAt = Instant.parse("2025-06-13T20:00:00.000Z")
//        )
//    )
//
//
//}