package com.ss.springboot.app

import com.ss.springboot.app.models.service.IUploadFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootDataJpaApplication: CommandLineRunner {

	@Autowired
	lateinit var uploadFileService: IUploadFileService

	override fun run(vararg args: String?) {
		uploadFileService.deleteAll()
		uploadFileService.init()
	}
}

fun main(args: Array<String>) {
	runApplication<SpringBootDataJpaApplication>(*args)
}
