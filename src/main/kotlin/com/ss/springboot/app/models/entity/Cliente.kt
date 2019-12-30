package com.ss.springboot.app.models.entity

import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotEmpty
    var nombre: String = ""
    @NotEmpty
    var apellido: String = ""
    @NotEmpty
    @Email
    var email: String = ""

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var createAt: Date? = null

    var foto: String = ""
}