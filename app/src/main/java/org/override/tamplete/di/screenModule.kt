package org.override.tamplete.di

import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.override.tamplete.feature.auth.presentation.AuthRoot
import org.override.tamplete.feature.navigation.routes.RouteGlobal

val screenModule: Module
    get() = module {
        navigation<RouteGlobal.Auth> { AuthRoot(koinViewModel()) }
    }