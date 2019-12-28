package com.ss.springboot.app.models.dao

import com.ss.springboot.app.models.entity.Cliente
import org.springframework.data.repository.PagingAndSortingRepository

interface IClienteDao: PagingAndSortingRepository<Cliente,Long>