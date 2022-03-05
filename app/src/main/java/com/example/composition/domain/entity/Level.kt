package com.example.composition.domain.entity

import java.io.Serializable

//enum class неявно реализует интерфейс Serializable, который позволяет передавать
//экземпляр данного объекта запакованного в байты и которые в получателе можно распаковать
enum class Level {
    TEST, EASY, NORMAL, HARD
}