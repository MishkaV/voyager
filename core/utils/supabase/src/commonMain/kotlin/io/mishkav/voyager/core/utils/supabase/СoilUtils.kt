package io.mishkav.voyager.core.utils.supabase

import io.github.jan.supabase.storage.StorageItem
import io.github.jan.supabase.storage.authenticatedStorageItem
import io.github.jan.supabase.storage.publicStorageItem

fun voyagerAuthenticatedStorageItem(fullPath: String): StorageItem? {
    val splitPath = fullPath.split("/")

    if (splitPath.size != 2) return null

    return authenticatedStorageItem(
        bucketId = splitPath[0],
        path = splitPath[1],
    )
}

fun voyagerPublicStorageItem(fullPath: String): StorageItem? {
    val splitPath = fullPath.split("/")

    if (splitPath.size != 2) return null

    return publicStorageItem(
        bucketId = splitPath[0],
        path = splitPath[1],
    )
}
