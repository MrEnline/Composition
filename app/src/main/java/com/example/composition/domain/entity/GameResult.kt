package com.example.composition.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GameResult(
    val winner: Boolean,
    var countOfRightAnswers: Int,
    var countOfQuestions: Int,
    val gameSettings: GameSettings
): Parcelable
