package com.ss.springboot.app.models.service

import com.ss.springboot.app.models.dao.IClienteDao
import com.ss.springboot.app.models.entity.Cliente
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ClienteServiceImpl: IClienteService {

    @Autowired
    private lateinit var clientedao: IClienteDao

    override fun findAll(): List<Cliente> {
        return clientedao.findAll() as List<Cliente>
    }

    override fun findAll(pageable: Pageable): Page<Cliente> {
        return clientedao.findAll(pageable)
    }

    @Transactional
    override fun save(cliente: Cliente) {
        clientedao.save(cliente)
    }

    override fun findOne(id: Long): Cliente {
        return clientedao.findById(id).orElse(null)
    }

    @Transactional
    override fun delete(id: Long) {
        clientedao.deleteById(id)
    }
}