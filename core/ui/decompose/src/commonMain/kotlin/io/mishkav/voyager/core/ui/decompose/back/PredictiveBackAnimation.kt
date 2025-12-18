package io.mishkav.voyager.core.ui.decompose.back

import androidx.compose.animation.core.tween
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatableV2
import com.arkivanov.essenty.backhandler.BackHandler

@OptIn(ExperimentalDecomposeApi::class)
fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T> {
    val animationSpec = tween<Float>(durationMillis = 500)
    return stackAnimation(
        animator = fade(animationSpec) + scale(animationSpec),
        predictiveBackParams = {
            PredictiveBackParams(
                backHandler = backHandler,
                onBack = onBack,
                animatable = ::androidPredictiveBackAnimatableV2,
            )
        },
    )
}
