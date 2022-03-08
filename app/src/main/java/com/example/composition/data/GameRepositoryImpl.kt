package com.example.composition.data

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1


    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val rightAnswer = sum - visibleNumber
        var options = HashSet<Int>()
        options.add(rightAnswer)
        //countOfOptions - количество вариантов, которые видит пользователь
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)         //нижняя граница значения
        val to = min(maxSumValue, rightAnswer + countOfOptions)                  //верхняя граница значения
        //генерация чисел близких к правильному ответу и добавление в множество
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(10, 3, 50, 8)
            }
            Level.EASY -> {
                GameSettings(10, 10, 70, 60)
            }
            Level.NORMAL -> {
                GameSettings(20, 20, 80,40)
            }
            Level.HARD -> {
                GameSettings(30, 30, 90, 40)
            }
        }
    }
}