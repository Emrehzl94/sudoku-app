package com.emrehzl

import com.emrehzl.routes.appRoutes
import com.emrehzl.service.SudokuService
import com.emrehzl.service.SudokuServiceImpl
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            jackson()
        }

        val sudokuService: SudokuService = SudokuServiceImpl()
        appRoutes(sudokuService)
    }.start(wait = true)
}
