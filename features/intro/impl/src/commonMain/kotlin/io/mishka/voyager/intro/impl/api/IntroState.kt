package io.mishka.voyager.intro.impl.api

sealed interface IntroState {

    object ShouldShowAuth : IntroState

    object ShouldShowOnboarding : IntroState
}
