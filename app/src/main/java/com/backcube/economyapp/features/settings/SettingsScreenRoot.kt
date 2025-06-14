package com.backcube.economyapp.features.settings

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.backcube.economyapp.R
import com.backcube.economyapp.features.common.baseComponents.CustomTopBar
import com.backcube.economyapp.features.common.ui.CustomListItem
import com.backcube.economyapp.features.settings.models.SettingType
import com.backcube.economyapp.features.settings.store.models.SettingsEffect
import com.backcube.economyapp.features.settings.store.models.SettingsIntent
import com.backcube.economyapp.features.settings.store.models.SettingsState
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingsScreenRoot(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
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
fun SettingsScreen(
    modifier: Modifier,
    navController: NavController,
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
                        },
                    )

                    SettingType.LINK -> CustomListItem(
                        title = item.name,
                        isSmallItem = true,
                        showLeading = false,
                        trailingContent = {
                            Image(
                                painter = painterResource(R.drawable.ic_arrow_solid),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}