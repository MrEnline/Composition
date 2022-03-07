package com.example.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

//enum class неявно реализует интерфейс Serializable, который позволяет передавать
//экземпляр данного объекта запакованного в байты и которые в получателе можно распаковать
@Parcelize
enum class Level : Parcelable {
    TEST, EASY, NORMAL, HARD
}