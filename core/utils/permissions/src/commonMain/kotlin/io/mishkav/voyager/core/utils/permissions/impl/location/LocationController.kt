package io.mishkav.voyager.core.utils.permissions.impl.location

import io.mishkav.voyager.core.utils.context.VoyagerPlatformContext

expect fun getLocationController(context: VoyagerPlatformContext): ILocationController
