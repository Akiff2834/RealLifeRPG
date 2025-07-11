package com.example.realliferpg.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.realliferpg.data.DataStoreManager
import com.example.realliferpg.data.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = DataStoreManager(application.applicationContext)

    private val _xp = MutableStateFlow(0)
    val xp: StateFlow<Int> = _xp.asStateFlow()

    private val _level = MutableStateFlow(1)
    val level: StateFlow<Int> = _level.asStateFlow()

    val tasks = mutableStateListOf(
        Task("Yürüyüş yap"),
        Task("Kitap oku"),
        Task("Su iç"),
        Task("Müzik dinle"),
        Task("Nefes egzersizi")
    )

    init {
        viewModelScope.launch {
            dataStore.xpFlow.collect {
                Log.d("GameViewModel", "DataStore XP okundu: $it")
                _xp.value = it
            }
        }

        viewModelScope.launch {
            dataStore.levelFlow.collect {
                Log.d("GameViewModel", "DataStore LEVEL okundu: $it")
                _level.value = it
            }
        }
    }

    fun completeTask(task: Task) {
        Log.d("GameViewModel", "completeTask çağrıldı: ${task.title}")

        val index = tasks.indexOf(task)
        if (index != -1 && !tasks[index].isCompleted) {
            tasks[index] = task.copy(isCompleted = true)

            val currentXp = _xp.value
            val newXp = currentXp + 20
            val levelUp = newXp / 100
            val remainingXp = newXp % 100
            val newLevel = _level.value + levelUp

            Log.d("GameViewModel", "XP önce: $currentXp, +20 eklendi → $newXp")
            Log.d("GameViewModel", "Yeni XP: $remainingXp, LevelUp: $levelUp, Yeni Level: $newLevel")

            _xp.value = remainingXp
            _level.value = newLevel

            viewModelScope.launch {
                dataStore.saveXp(remainingXp)
                dataStore.saveLevel(newLevel)
                Log.d("GameViewModel", "XP ve Level DataStore'a kaydedildi")
            }
        } else {
            Log.d("GameViewModel", "Görev zaten tamamlanmış veya bulunamadı")
        }
    }
}
