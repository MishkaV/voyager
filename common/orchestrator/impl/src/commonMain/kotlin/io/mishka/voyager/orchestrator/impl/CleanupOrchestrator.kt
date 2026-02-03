package io.mishka.voyager.orchestrator.impl

import io.mishka.voyager.core.repositories.base.Cleanable

/**
 * Orchestrator for cleaning up user-specific data
 *
 * All implementing [Cleanable] interface will be cleaned up in parallel.
 */
// @SingleIn(AppScope::class)
// @Inject
// class CleanupOrchestrator(
//    private val cleanables: Set<Cleanable>,
// ) {
//    private val logger by lazy { Logger.withTag("CleanupOrchestrator") }
//
//    suspend fun cleanupAll() {
//        if (cleanables.isEmpty()) {
//            logger.i { "No cleanable cleanable registered" }
//            return
//        }
//
//        logger.i { "Starting cleanup for ${cleanables.size} clenable" }
//
//        coroutineScope {
//            val jobs = cleanables.map { entity ->
//                launch {
//                    val name = entity::class.simpleName ?: "Unknown"
//                    try {
//                        logger.d { "Cleaning up: $name" }
//                        entity.cleanup()
//                        logger.d { "Cleanup completed: $name" }
//                    } catch (e: Exception) {
//                        logger.e(e) { "Cleanup failed for $name" }
//                        // Continue with other repositories
//                    }
//                }
//            }
//            jobs.joinAll()
//        }
//
//        logger.i { "Cleanup completed for all cleanable" }
//    }
// }
