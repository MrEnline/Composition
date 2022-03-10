package com.example.composition.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameResult
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
        get() = _question

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private lateinit var gameSettings: GameSettings
    private var countOfRightAnswers: Int = 0
    private var countOfQuestions: Int = 0

    fun generateQuestion(valButton: String = "") {
        countOfQuestions++
        if (!valButton.isBlank()) {
            checkRightAnswer(valButton.toInt())
        }
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun checkRightAnswer(valAnswer: Int) {
        val rightAnswer = _question.value?.sum!! - _question.value?.visibleNumber!!
        if (rightAnswer == valAnswer) {
            countOfRightAnswers++
        }
        _gameResult.value = GameResult(false, countOfRightAnswers, countOfQuestions, gameSettings)
    }

    fun getGameSettings(level: Level) {
        gameSettings = getGameSettingsUseCase(level)
        _gameResult.value = GameResult(false, countOfRightAnswers, countOfQuestions, gameSettings)
    }
}