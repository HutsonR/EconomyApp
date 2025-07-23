package com.backcube.account.main.models

import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.utils.CurrencyIsoCode
import com.backcube.ui.components.graphics.ChartPoint
import com.backcube.ui.components.graphics.ChartType

data class AccountState(
    val isLoading: Boolean = false,
    val item: AccountResponseModel? = null,
    val currencies: List<CurrencyIsoCode> = CurrencyIsoCode.entries,
    val chartPoints: List<ChartPoint> = emptyList(),
    val chartType: ChartType = ChartType.Bar
)
