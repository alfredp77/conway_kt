package com.conway.game

data class GameParameters(
    val width: Int = 0,
    val height: Int = 0,
    val generations: Int = 0,
    val exit: Boolean = false,
    val maxWidth: Int = 0,
    val maxHeight: Int = 0,
    val minGenerations: Int = 0,
    val maxGenerations: Int = 0
)
