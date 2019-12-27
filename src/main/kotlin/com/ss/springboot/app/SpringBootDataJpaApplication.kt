package com.ss.springboot.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootDataJpaApplication

fun main(args: Array<String>) {
	runApplication<SpringBootDataJpaApplication>(*args)
}
