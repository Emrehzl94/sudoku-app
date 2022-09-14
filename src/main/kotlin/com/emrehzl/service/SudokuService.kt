package com.emrehzl.service

import com.emrehzl.utils.Response

interface SudokuService {
    suspend fun generateBoard(gridSize: Int, difficulty: Difficulty): Response<Array<Array<Int>>>
}