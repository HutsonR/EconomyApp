package com.backcube.settings.about.models

internal sealed interface SettingAboutIntent {
    data object GoBack : SettingAboutIntent
}