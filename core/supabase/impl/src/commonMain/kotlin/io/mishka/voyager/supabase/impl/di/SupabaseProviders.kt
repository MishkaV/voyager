package io.mishka.voyager.supabase.impl.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.coil.Coil3Integration
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import voyager.core.supabase.impl.BuildKonfig

@ContributesTo(AppScope::class)
interface SupabaseProviders {

    @SingleIn(AppScope::class)
    @Provides
    fun provideClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildKonfig.SUPABASE_URL,
            supabaseKey = BuildKonfig.SUPABASE_API_KEY,
        ) {
            install(Auth) {
                scheme = BuildKonfig.DEEPLINK_SCHEME
                host = BuildKonfig.DEEPLINK_AUTH_HOST
            }
            install(Postgrest)
            install(Storage)
            install(Coil3Integration)
        }
    }
}
