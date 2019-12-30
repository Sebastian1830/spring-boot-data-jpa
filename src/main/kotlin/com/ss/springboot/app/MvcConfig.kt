package com.ss.springboot.app

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

/*@Configuration
class MvcConfig: WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)
        val resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString()
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourcePath)
    }
}*/