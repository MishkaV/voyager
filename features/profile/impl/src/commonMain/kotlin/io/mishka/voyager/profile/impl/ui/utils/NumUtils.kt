package io.mishka.voyager.profile.impl.ui.utils

import kotlin.math.abs
import kotlin.math.round

fun Float.isWhole(eps: Float = 1e-6f): Boolean = abs(this - round(this)) < eps