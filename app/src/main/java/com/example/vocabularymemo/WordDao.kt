package com.example.vocabularymemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {
    @Insert
    suspend fun insert(word: WordEntity)

    @Query("SELECT * FROM words")
    suspend fun getAll(): List<WordEntity>
}
