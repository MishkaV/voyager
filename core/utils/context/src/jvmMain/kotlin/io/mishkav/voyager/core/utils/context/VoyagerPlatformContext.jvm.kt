package io.mishkav.voyager.core.utils.context

public actual abstract class VoyagerPlatformContext private constructor() {
    public companion object {
        public val INSTANCE: VoyagerPlatformContext = object : VoyagerPlatformContext() {}
    }
}
