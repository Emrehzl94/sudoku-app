package com.emrehzl.service

import com.emrehzl.utils.Response

class SudokuServiceImpl: SudokuService {
    override suspend fun generateBoard(gridSize: Int, difficulty: Difficulty): Response<Array<Array<Int>>> {
        val board = Board(gridSize, difficulty)
        board.generate()
        return Response.Success(data = board.get())
    }
}