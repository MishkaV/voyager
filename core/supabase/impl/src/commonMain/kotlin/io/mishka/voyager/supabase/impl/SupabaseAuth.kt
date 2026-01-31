package io.mishka.voyager.supabase.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.github.jan.supabase.SupabaseClient
import io.mishka.voyager.supabase.api.ISupabaseAuth

@ContributesBinding(AppScope::class)
class SupabaseAuth(
    private val supabase: SupabaseClient,
) : ISupabaseAuth {
    // TODO: Implement your API methods here
}
