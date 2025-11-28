package io.mishka.voyager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform