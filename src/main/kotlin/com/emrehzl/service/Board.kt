package com.emrehzl.service

import kotlin.math.sqrt

class Board(
    private val gridSize: Int = DEFAULT_GRID_SIZE,
    private val difficulty: Difficulty = DEFAULT_DIFFICULTY
) {
    private val board = Array(gridSize) { Array(gridSize) { 0 } }
    private val setOfNumbers = List(gridSize) { it + 1 }
    private val squareRoot = sqrt(gridSize.toDouble()).toInt()

    companion object {
        const val DEFAULT_GRID_SIZE = 9
        val DEFAULT_DIFFICULTY = Difficulty.Medium

        private val validGridSize = listOf(4, 9, 16, 25, 36)

        fun isValidGridSize(gridSize: Int): Boolean {
            return validGridSize.contains(gridSize)
        }
    }

    fun generate() {
        if (!isValidGridSize(gridSize)) {
            throw Exception("Invalid GridSize")
        }
        fillDiagonalRandomly()
        fillRemainingPart()
        removeItems()
    }

    fun get(): Array<Array<Int>> {
        return board
    }

    private fun fillDiagonalRandomly() {
        for (grid in 0 until squareRoot) {
            val shuffledArray = setOfNumbers.shuffled()
            for (i in 0 until gridSize / squareRoot) {
                for (j in 0 until gridSize / squareRoot) {
                    board[i + (grid * squareRoot)][j + (grid * squareRoot)] = shuffledArray[(i * squareRoot) + j]
                }
            }
        }
    }

    private fun fillRemainingPart() {
        solveBoard()
    }

    private fun removeItems() {
        val k = when (difficulty) {
            Difficulty.Easy -> (gridSize * gridSize) - ((34 / DEFAULT_GRID_SIZE) * gridSize)
            Difficulty.Medium -> (gridSize * gridSize) - ((28 / DEFAULT_GRID_SIZE) * gridSize)
            Difficulty.Hard -> (gridSize * gridSize) - ((26 / DEFAULT_GRID_SIZE) * gridSize)
        }

        removeKItems(k)
    }

    private fun removeKItems(k: Int) {
        var removedCount = 0
        while (removedCount < k) {
            val rowToRemove = (0 until gridSize).random()
            val columnToRemove = (0 until gridSize).random()
            if (board[rowToRemove][columnToRemove] != 0) {
                board[rowToRemove][columnToRemove] = 0
                removedCount++
            }
        }
    }

    private fun solveBoard(): Boolean {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                if (board[i][j] != 0) {
                    continue
                }
                for (number in setOfNumbers) {
                    if (isNumberValidToPut(number, i, j)) {
                        board[i][j] = number

                        if (solveBoard()) {
                            return true
                        } else {
                            board[i][j] = 0
                        }
                    }
                }
                return false
            }
        }
        return true
    }

    private fun isNumberValidToPut(number: Int, row: Int, column: Int): Boolean {
        return !isNumberInRow(number, row)
                && !isNumberInColumn(number, column)
                && !isNumberInBox(number, row, column)
    }

    private fun isNumberInRow(number: Int, row: Int): Boolean {
        for (i in 0 until gridSize) {
            if (board[row][i] == number) {
                return true
            }
        }
        return false
    }

    private fun isNumberInColumn(number: Int, column: Int): Boolean {
        for (i in 0 until gridSize) {
            if (board[i][column] == number) {
                return true
            }
        }
        return false
    }

    private fun isNumberInBox(number: Int, row: Int, column: Int): Boolean {
        val startRow = row - row % squareRoot
        val startColumn = column - column % squareRoot
        for (i in startRow until startRow + squareRoot) {
            for (j in startColumn until startColumn + squareRoot) {
                if (board[i][j] == number) {
                    return true
                }
            }
        }
        return false
    }

    fun printBoard(board: Array<Array<Int>>) {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                print("${board[i][j]} ")
            }
            println()
        }
    }
}