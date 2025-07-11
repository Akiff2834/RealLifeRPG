package com.example.realliferpg.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("game_prefs")

class DataStoreManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val XP_KEY = intPreferencesKey("xp")
        val LEVEL_KEY = intPreferencesKey("level")
    }

    val xpFlow: Flow<Int> = dataStore.data.map { it[XP_KEY] ?: 0 }
    val levelFlow: Flow<Int> = dataStore.data.map { it[LEVEL_KEY] ?: 1 }

    suspend fun saveXp(xp: Int) {
        dataStore.edit { it[XP_KEY] = xp }
    }

    suspend fun saveLevel(level: Int) {
        dataStore.edit { it[LEVEL_KEY] = level }
    }
}
