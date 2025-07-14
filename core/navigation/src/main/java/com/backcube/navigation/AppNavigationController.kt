package com.backcube.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.StateFlow

interface AppNavigationController {
    /**
     * Навигация к маршруту [route]
     */
    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit = {},
        argument: Pair<String, Any?>? = null
    )

    /**
     * Переход к прошлому экрнау по стеку
     */
    fun popBackStack(
        argument: Pair<String, Any?>? = null
    ): Boolean

    /**
     * Позволяет подписаться на StateFlow из SavedStateHandle текущего бэкстека.
     * @param key — ключ, под которым лежит значение
     * @param initial — значение по умолчанию, если ещё нет ничего в handle
     */
    fun <T> observeArgsAsState(
        key: String,
        initial: T?
    ): StateFlow<T?>

    /**
     * Позволяет удалить ключ со значением из стека.
     * @param key — ключ, под которым лежит значение
     */
    fun <T> removeSavedArgs(
        key: String
    ): Boolean
}