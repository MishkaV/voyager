package io.mishka.voyager.${__moduleName}.impl

import io.mishka.voyager.${__moduleName}.api.I${__formattedModuleName}
<#if needDI>
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject

@ContributesBinding(AppScope::class)
@Inject
</#if>
class ${__formattedModuleName} : I${__formattedModuleName} {
    // TODO: Implement your API methods here
}
