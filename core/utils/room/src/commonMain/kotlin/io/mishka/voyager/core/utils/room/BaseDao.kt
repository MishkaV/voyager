package io.mishka.voyager.core.utils.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert

/**
 * Base DAO interface providing common CRUD operations for Room entities.
 * All methods are suspend functions for coroutine support.
 */
interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     * @return the row ID of the inserted item.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     * @return array of row IDs of the inserted items.
     */
    @Insert
    suspend fun insert(vararg obj: T): LongArray

    /**
     * Insert an object in the database. Will ignore on conflict.
     *
     * @param obj the object to be inserted.
     * @return the row ID of the inserted item, or -1 if ignored.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWithIgnore(obj: T): Long

    /**
     * Insert an array of objects in the database. Will ignore on conflict.
     *
     * @param obj the objects to be inserted.
     * @return array of row IDs of the inserted items.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWithIgnore(vararg obj: T): LongArray

    /**
     * Upsert (insert or update) an object in the database.
     *
     * @param obj the object to be upserted.
     */
    @Upsert
    suspend fun upsert(obj: T)

    /**
     * Upsert (insert or update) an array of objects in the database.
     *
     * @param obj the objects to be upserted.
     */
    @Upsert
    suspend fun upsert(vararg obj: T)

    /**
     * Update an object in the database.
     *
     * @param obj the object to be updated.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: T)

    /**
     * Delete an object from the database.
     *
     * @param obj the object to be deleted.
     */
    @Delete
    suspend fun delete(obj: T)
}
