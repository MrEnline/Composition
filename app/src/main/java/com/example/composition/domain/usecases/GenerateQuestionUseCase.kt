package com.example.composition.domain.usecases

import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository

class GenerateQuestionUseCase (private val repository: GameRepository) {

    //т.к. классу UseCase нет смысла задавать метод с таким же названием
    //то можно использовать ключевые слоова "operator" и "invoke", чтобы можно было вызывать
    //класс следующим образом generateQuestionUseCase() или
    //generateQuestionUseCase.invoke()
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6;
    }
}