package com.backcube.settings.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.navigation.AppNavigationController
import com.backcube.settings.R
import com.backcube.settings.common.di.SettingComponentProvider
import com.backcube.settings.language.models.SettingLanguageEffect
import com.backcube.settings.language.models.SettingLanguageIntent
import com.backcube.settings.language.models.SettingLanguageState
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.CustomOption
import com.backcube.ui.utils.CollectEffect
import com.backcube.ui.utils.LocalAppContext
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingLanguageScreenRoot(
    navController: AppNavigationController
) {
    val context = LocalAppContext.current
    val applicationContext = LocalContext.current.applicationContext
    val settingComponent = (applicationContext as SettingComponentProvider).provideSettingComponent()
    val viewModel = remember {
        settingComponent.settingLanguageViewModel
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = context.getString(R.string.settings_language),
                leadingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_close),
                onLeadingClick = { viewModel.handleIntent(SettingLanguageIntent.GoBack) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        SettingLanguageScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
internal fun SettingLanguageScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: SettingLanguageState,
    effects: Flow<SettingLanguageEffect>,
    onIntent: (SettingLanguageIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    CollectEffect(effects) { effect ->
        when (effect) {
            SettingLanguageEffect.NavigateBack -> navController.popBackStack()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(16.dp)
    ) {
        state.availableLocales.forEach { (localeCode, localeName) ->
            CustomOption (
                text = localeName,
                selected = state.selectedLocale == localeCode,
                onSelect = { onIntent(SettingLanguageIntent.ChangeLanguage(localeCode)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}