package com.ardondev.noteskmp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY clipped DESC, modificationDate DESC")
    fun getAll(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity): Long?

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getById(id: Long): Flow<NoteEntity>

    @Update
    suspend fun update(noteEntity: NoteEntity): Int

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

}