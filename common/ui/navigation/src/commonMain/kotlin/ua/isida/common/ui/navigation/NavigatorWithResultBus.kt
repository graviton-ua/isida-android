package ua.isida.common.ui.navigation

import ua.isida.common.ui.navigation.result.ResultEventBus

interface NavigatorWithResultBus : Navigator {

    // ResultBus that provides you results between screens based on single event strategy
    val resultBus: ResultEventBus
}