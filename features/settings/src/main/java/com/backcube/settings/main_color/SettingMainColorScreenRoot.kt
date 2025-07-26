package com.backcube.settings.main_color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.navigation.AppNavigationController
import com.backcube.settings.R
import com.backcube.settings.common.di.SettingComponentProvider
import com.backcube.settings.main_color.models.SettingMainColorEffect
import com.backcube.settings.main_color.models.SettingMainColorIntent
import com.backcube.settings.main_color.models.SettingMainColorState
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.CustomOption
import com.backcube.ui.utils.CollectEffect
import com.backcube.ui.utils.LocalAppContext
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingMainColorScreenRoot(
    navController: AppNavigationController
) {
    val context = LocalAppContext.current
    val applicationContext = LocalContext.current.applicationContext
    val settingComponent = (applicationContext as SettingComponentProvider).provideSettingComponent()
    val viewModel = remember {
        settingComponent.settingMainColorViewModel
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = context.getString(R.string.settings_main_color),
                leadingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_close),
                onLeadingClick = { viewModel.handleIntent(SettingMainColorIntent.GoBack) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        SettingMainColorScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
internal fun SettingMainColorScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: SettingMainColorState,
    effects: Flow<SettingMainColorEffect>,
    onIntent: (SettingMainColorIntent) -> Unit
) {
    val context = LocalAppContext.current
    val colors = MaterialTheme.colorScheme

    CollectEffect(effects) { effect ->
        when (effect) {
            SettingMainColorEffect.NavigateBack -> navController.popBackStack()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(16.dp)
    ) {
        state.availableColors.forEach {
            CustomOption (
                text = context.getString(it.labelRes),
                selected = state.selectedColor == it.color,
                trailingComposable = { PreviewColorBox(it.previewColor) }
            ) { onIntent(SettingMainColorIntent.ChangeColor(it.color)) }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PreviewColorBox(color: Color) {
    Box(
        modifier = Modifier
            .width(16.dp)
            .height(16.dp)
            .clip(CircleShape)
            .background(color)
    )
}