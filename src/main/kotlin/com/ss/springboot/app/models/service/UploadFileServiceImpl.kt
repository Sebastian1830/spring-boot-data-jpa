package com.ss.springboot.app.models.service

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.lang.RuntimeException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class UploadFileServiceImpl: IUploadFileService {

    final val UPLOADS_FOLDER: String = "uploads"

    override fun load(filename: String): Resource {
        val pathFoto = getPath(filename)
        val recurso: Resource = UrlResource(pathFoto.toUri())
        if (!recurso.exists() && !recurso.isReadable){
            throw RuntimeException("Error: no se puede cargar la imagen: $pathFoto")
        }
        return recurso
    }

    override fun copy(file: MultipartFile): String {
        val uniqueFileName = UUID.randomUUID().toString() + "_" + file.originalFilename
        val rootPath = getPath(uniqueFileName)
        Files.copy(file.inputStream, rootPath)
        return uniqueFileName
    }

    override fun delete(filename: String): Boolean {
        val rootPath = getPath(filename)
        val archivo = rootPath.toFile()
        if (archivo.exists() && archivo.canRead()){
            if (archivo.delete()){
                return true
            }
        }
        return false
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile())
    }

    override fun init() {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER))
    }

    fun getPath(filename: String): Path{
        return Paths.get(UPLOADS_FOLDER).resolve(filename)
    }
}