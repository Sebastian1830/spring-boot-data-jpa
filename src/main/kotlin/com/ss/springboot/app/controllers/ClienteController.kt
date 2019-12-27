package com.ss.springboot.app.controllers

import com.ss.springboot.app.models.dao.IClienteDao
import com.ss.springboot.app.models.entity.Cliente
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*
import javax.validation.Valid

@Controller
class ClienteController {

    @Autowired
    lateinit var clientedao: IClienteDao

    @RequestMapping("/listar")
    fun listar(model: Model): String{
        model.addAttribute("titulo","Listado de Cliente")
        model.addAttribute("clientes", clientedao.findAll())
        return "listar"
    }

    @GetMapping("/form")
    fun crear(model: MutableMap<String, Any?>): String{
        var cliente = Cliente()
        model["titulo"] = "Formulario del Cliente"
        model["cliente"] = cliente
        return "form"
    }

    @PostMapping("/form")
    fun guardar(@Valid cliente: Cliente, result: BindingResult, model: Model): String{
        if (result.hasErrors()) {
//            model.addAttribute("titulo","Formulario del Cliente")
            return "form"
        }
        clientedao.save(cliente)
        return "redirect:listar"
    }
}