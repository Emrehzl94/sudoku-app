package com.emrehzl.routes

import com.emrehzl.service.Board
import com.emrehzl.service.Difficulty
import com.emrehzl.service.SudokuService
import com.emrehzl.utils.ExceptionUtils
import com.emrehzl.utils.Response
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.appRoutes(sudokuService: SudokuService) {

    routing {
        route("/api/sudoku") {
            get("/board/generate") {
                try {
                    val gridSizeString = call.request.queryParameters["gridSize"]
                    val difficultyString = call.request.queryParameters["difficulty"]
                    val gridSize = if (gridSizeString.isNullOrEmpty()) Board.DEFAULT_GRID_SIZE else gridSizeString.toInt()
                    val difficulty = if (difficultyString.isNullOrEmpty()) Board.DEFAULT_DIFFICULTY else Difficulty.valueOf(difficultyString)
                    val result = sudokuService.generateBoard(gridSize, difficulty)
                    call.respond(result.statusCode, result)
                } catch (e: Exception) {
                    val result = Response.Error(
                        statusCode = HttpStatusCode.InternalServerError,
                        message = e.message,
                        exception = ExceptionUtils.getExceptionNameString(e)
                    )
                    call.respond(result.statusCode, result)
                }
            }
        }
    }
}