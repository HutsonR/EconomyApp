package com.backcube.settings.about

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.navigation.AppNavigationController
import com.backcube.settings.R
import com.backcube.settings.about.models.SettingAboutEffect
import com.backcube.settings.about.models.SettingAboutIntent
import com.backcube.settings.about.models.SettingAboutState
import com.backcube.settings.common.di.SettingComponentProvider
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.utils.CollectEffect
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SettingAboutScreenRoot(
    navController: AppNavigationController
) {
    val applicationContext = LocalContext.current.applicationContext
    val settingComponent = (applicationContext as SettingComponentProvider).provideSettingComponent()
    val viewModel = remember {
        settingComponent.settingAboutViewModel
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.settings_about),
                leadingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_close),
                onLeadingClick = { viewModel.handleIntent(SettingAboutIntent.GoBack) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        SettingAboutScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
internal fun SettingAboutScreen(
    modifier: Modifier = Modifier,
    navController: AppNavigationController,
    state: SettingAboutState,
    effects: Flow<SettingAboutEffect>,
    onIntent: (SettingAboutIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    CollectEffect(effects) { effect ->
        when (effect) {
            SettingAboutEffect.NavigateBack -> navController.popBackStack()
        }
    }

    val packageInfo = remember {
        context.packageManager.getPackageInfo(context.packageName, 0)
    }

    // Версия приложения
    val versionName: String = try {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0"
    } catch (e: Exception) {
        "1.0"
    }
    val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode.toString()
    } else {
        @Suppress("DEPRECATION")
        packageInfo.versionCode.toString()
    }

    // Дата последнего обновления
    val lastUpdateMillis = packageInfo.lastUpdateTime
    val formattedDate = remember(lastUpdateMillis) {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        sdf.format(Date(lastUpdateMillis))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_about_version, versionName, versionCode),
            color = colors.onSurface
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = stringResource(R.string.settings_about_last_update, formattedDate),
            color = colors.onSurface
        )
    }
}