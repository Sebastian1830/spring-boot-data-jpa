package com.ss.springboot.app.models.dao

import com.ss.springboot.app.models.entity.Cliente

interface IClienteDao {

    fun findAll(): List<Cliente>

    fun save(cliente: Cliente)

}