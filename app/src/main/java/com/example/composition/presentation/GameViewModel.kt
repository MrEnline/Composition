package com.example.composition.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel: ViewModel() {
    private val gameRepository = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(gameRepository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepository)

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = question

//    private val _gameSettings = MutableLiveData<GameSettings>()
//    val gameSettings: LiveData<GameSettings>
//        get() = _gameSettings
    private lateinit var gameSettings: GameSettings

    fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun getGameSettings(level: Level) {
        gameSettings = getGameSettingsUseCase(level)
    }

}