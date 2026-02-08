package io.mishka.voyager.common.audiocontroller.impl.utils

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.Inject
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlin.time.Duration.Companion.hours

/**
 * Utility class for resolving Supabase Storage audio URLs
 * Converts audioFullPath (e.g., "podcasts/CM.wav") to authenticated signed URL
 */
@Inject
class SupabaseAudioUrlResolver(
    private val storage: Storage
) {

    suspend fun resolveUrl(audioFullPath: String): String {
        Logger.d { "SupabaseAudioUrlResolver: Resolving URL for: $audioFullPath" }

        val parts = audioFullPath.split("/", limit = 2)
        require(parts.size == 2) {
            "Invalid audioFullPath format. Expected 'bucket/file', got: $audioFullPath"
        }

        val bucket = parts[0]
        val filePath = parts[1]

        Logger.d { "SupabaseAudioUrlResolver: Bucket: $bucket, File: $filePath" }

        return try {
            val signedUrl = storage.from(bucket).createSignedUrl(
                path = filePath,
                expiresIn = 1.hours
            )
            Logger.d { "SupabaseAudioUrlResolver: Resolved URL successfully" }
            signedUrl
        } catch (e: Exception) {
            Logger.e(e) { "SupabaseAudioUrlResolver: Failed to resolve URL for $audioFullPath" }
            throw e
        }
    }

    fun resolvePublicUrl(audioFullPath: String): String {
        Logger.d { "SupabaseAudioUrlResolver: Resolving public URL for: $audioFullPath" }

        val parts = audioFullPath.split("/", limit = 2)
        require(parts.size == 2) {
            "Invalid audioFullPath format. Expected 'bucket/file', got: $audioFullPath"
        }

        val bucket = parts[0]
        val filePath = parts[1]

        return storage.from(bucket).publicUrl(filePath)
    }
}
