package io.mishka.voyager.profile.impl.ui.utils

import androidx.compose.ui.platform.UriHandler
import co.touchlab.kermit.Logger
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

fun UriHandler.safeOpenUri(uri: String) {
    try {
        openUri(uri)
    } catch (e: Exception) {
        Logger.e("Error to open uri - $e", e)
    }
}

fun UriHandler.sendEmail(email: String) {
    val subject = URLEncoder.encode("Voyager", UTF_8.name())

    safeOpenUri("mailto:$email?subject=$subject")
}
