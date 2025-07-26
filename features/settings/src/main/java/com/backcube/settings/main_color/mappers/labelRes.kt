package com.backcube.settings.main_color.mappers

import androidx.annotation.StringRes
import com.backcube.domain.models.entities.ThemeColor
import com.backcube.ui.R

@StringRes
fun ThemeColor.labelRes(): Int = when (this) {
    ThemeColor.GREEN -> R.string.color_green
    ThemeColor.BLUE -> R.string.color_blue
    ThemeColor.ORANGE -> R.string.color_orange
    ThemeColor.PURPLE -> R.string.color_purple
    ThemeColor.PINK -> R.string.color_pink
}