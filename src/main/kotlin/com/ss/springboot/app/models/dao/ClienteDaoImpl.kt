package com.ss.springboot.app.models.dao

import com.ss.springboot.app.models.entity.Cliente
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class ClienteDaoImpl: IClienteDao {

    @PersistenceContext
    lateinit var em: EntityManager

    override fun findAll(): List<Cliente> {
        return em.createQuery("select id,nombre,apellido,email,createAt from Cliente").resultList as List<Cliente>
    }

    @Transactional
    override fun save(cliente: Cliente) {
        em.persist(cliente)
    }
}