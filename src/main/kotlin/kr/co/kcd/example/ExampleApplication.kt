package kr.co.kcd.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChickenApplication

fun main(args: Array<String>) {
    runApplication<ChickenApplication>(*args)
}
