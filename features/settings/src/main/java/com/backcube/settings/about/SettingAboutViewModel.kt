package com.backcube.settings.about

import com.backcube.settings.about.models.SettingAboutEffect
import com.backcube.settings.about.models.SettingAboutIntent
import com.backcube.settings.about.models.SettingAboutState
import com.backcube.ui.BaseViewModel
import javax.inject.Inject

class SettingAboutViewModel @Inject constructor() :
    BaseViewModel<SettingAboutState, SettingAboutEffect>(SettingAboutState()) {

    internal fun handleIntent(intent: SettingAboutIntent) {
        when (intent) {
            SettingAboutIntent.GoBack -> effect(SettingAboutEffect.NavigateBack)
        }
    }
}