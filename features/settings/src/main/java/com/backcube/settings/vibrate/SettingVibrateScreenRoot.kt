package com.backcube.settings.vibrate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.navigation.AppNavigationController
import com.backcube.settings.R
import com.backcube.settings.common.di.SettingComponentProvider
import com.backcube.settings.vibrate.models.SettingVibrateEffect
import com.backcube.settings.vibrate.models.SettingVibrateIntent
import com.backcube.settings.vibrate.models.SettingVibrateState
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.utils.CollectEffect
import com.backcube.ui.utils.LocalAppContext
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingVibrateScreenRoot(
    navController: AppNavigationController
) {
    val context = LocalAppContext.current
    val applicationContext = LocalContext.current.applicationContext
    val settingComponent = (applicationContext as SettingComponentProvider).provideSettingComponent()
    val viewModel = remember {
        settingComponent.settingVibrateViewModel
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = context.getString(R.string.settings_vibrate),
                leadingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_close),
                onLeadingClick = { viewModel.handleIntent(SettingVibrateIntent.GoBack) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        SettingVibrateScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
internal fun SettingVibrateScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: SettingVibrateState,
    effects: Flow<SettingVibrateEffect>,
    onIntent: (SettingVibrateIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    CollectEffect(effects) { effect ->
        when (effect) {
            SettingVibrateEffect.NavigateBack -> navController.popBackStack()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {

    }
}