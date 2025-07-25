package com.backcube.settings.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.navigation.AppNavigationController
import com.backcube.navigation.model.Screens
import com.backcube.settings.R
import com.backcube.settings.common.di.SettingComponentProvider
import com.backcube.settings.main.models.SettingsEffect
import com.backcube.settings.main.models.SettingsIntent
import com.backcube.settings.main.models.SettingsState
import com.backcube.settings.main.models.ui.SettingType
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.CustomListItem
import com.backcube.ui.utils.CollectEffect
import com.backcube.ui.utils.LocalAppContext
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingsScreenRoot(
    navController: AppNavigationController
) {
    val context = LocalAppContext.current
    val applicationContext = LocalContext.current.applicationContext
    val settingComponent = (applicationContext as SettingComponentProvider).provideSettingComponent()
    val viewModel = remember {
        settingComponent.settingsViewModel
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = context.getString(R.string.settings_title),
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        SettingsScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
internal fun SettingsScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: SettingsState,
    effects: Flow<SettingsEffect>,
    onIntent: (SettingsIntent) -> Unit
) {
    val context = LocalAppContext.current
    val colors = MaterialTheme.colorScheme

    CollectEffect(effects) { effect ->
        when (effect) {
            SettingsEffect.NavigateToAbout -> navController.navigate(Screens.SettingAboutScreen.route)
            SettingsEffect.NavigateToColor -> navController.navigate(Screens.SettingMainColorScreen.route)
            SettingsEffect.NavigateToHaptics -> navController.navigate(Screens.SettingVibrateScreen.route)
            SettingsEffect.NavigateToLanguage -> navController.navigate(Screens.SettingLanguageScreen.route)
            SettingsEffect.NavigateToPasscode -> Toast.makeText(
                context,
                context.getString(com.backcube.ui.R.string.will_be_add),
                Toast.LENGTH_SHORT
            ).show()
            SettingsEffect.NavigateToSync -> Toast.makeText(
                context,
                context.getString(com.backcube.ui.R.string.will_be_add),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {
        LazyColumn {
            items(
                items = state.items,
                key = { it.id }
            ) { item ->
                when (item.type) {
                    SettingType.SWITCH -> CustomListItem(
                        title = context.getString(item.nameRes),
                        isSmallItem = true,
                        showLeading = false,
                        trailingContent = {
                            Switch(
                                modifier = Modifier
                                    .height(32.dp)
                                    .width(52.dp),
                                checked = state.isDarkTheme,
                                onCheckedChange = {
                                    onIntent(SettingsIntent.ToggleDarkTheme(it))
                                }
                            )
                        }
                    )

                    SettingType.LINK -> CustomListItem(
                        title = context.getString(item.nameRes),
                        isSmallItem = true,
                        showLeading = false,
                        trailingContent = {
                            Image(
                                painter = painterResource(com.backcube.ui.R.drawable.ic_arrow_solid),
                                contentDescription = null
                            )
                        },
                        onItemClick = { onIntent(SettingsIntent.ItemClicked(item.id)) }
                    )
                }
            }
        }
    }
}