package ua.graviton.isida.ui.navigation

import ua.graviton.isida.ui.navigation.result.ResultEventBus

interface NavigatorWithResultBus : Navigator {

    // ResultBus that provides you results between screens based on single event strategy
    val resultBus: ResultEventBus
}