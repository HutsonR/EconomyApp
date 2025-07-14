package com.backcube.settings

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.navigation.AppNavigationController
import com.backcube.settings.di.SettingComponentProvider
import com.backcube.settings.models.SettingsEffect
import com.backcube.settings.models.SettingsIntent
import com.backcube.settings.models.SettingsState
import com.backcube.settings.models.ui.SettingType
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.CustomListItem
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingsScreenRoot(
    navController: AppNavigationController
) {
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
                title = stringResource(R.string.settings_title),
                onTrailingClick = {},
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
    val colors = MaterialTheme.colorScheme
    val checked = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {
        LazyColumn {
            items(
                items = state.items,
                key = { it.name }
            ) { item ->
                when (item.type) {
                    SettingType.SWITCH -> CustomListItem(
                        title = item.name,
                        isSmallItem = true,
                        showLeading = false,
                        trailingContent = {
                            Switch(
                                modifier = Modifier
                                    .height(32.dp)
                                    .width(52.dp),
                                checked = checked.value, onCheckedChange = {
                                    checked.value = it
                                }
                            )
                        }
                    )

                    SettingType.LINK -> CustomListItem(
                        title = item.name,
                        isSmallItem = true,
                        showLeading = false,
                        trailingContent = {
                            Image(
                                painter = painterResource(com.backcube.ui.R.drawable.ic_arrow_solid),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}