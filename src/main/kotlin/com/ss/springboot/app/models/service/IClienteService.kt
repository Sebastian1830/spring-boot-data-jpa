package com.ss.springboot.app.models.service

import com.ss.springboot.app.models.entity.Cliente
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IClienteService {

    fun findAll(): List<Cliente>

    fun findAll(pageable: Pageable): Page<Cliente>

    fun save(cliente: Cliente)

    fun findOne(id: Long): Cliente

    fun delete(id: Long)

}