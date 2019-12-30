package com.ss.springboot.app.models.service

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface IUploadFileService {
    
    fun load(filename: String): Resource

    fun copy(file: MultipartFile): String

    fun delete(filename: String): Boolean

    fun deleteAll()

    fun init()
    
}