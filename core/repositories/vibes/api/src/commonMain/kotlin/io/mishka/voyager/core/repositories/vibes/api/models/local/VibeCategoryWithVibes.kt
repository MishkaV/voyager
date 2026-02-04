package io.mishka.voyager.core.repositories.vibes.api.models.local

import androidx.room.Embedded
import androidx.room.Relation

data class VibeCategoryWithVibes(
    @Embedded val category: VibeCategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId",
    )
    val vibes: List<VibeEntity>,
)
