package com.ss.springboot.app.controllers

import com.ss.springboot.app.models.entity.Cliente
import com.ss.springboot.app.models.service.IClienteService
import com.ss.springboot.app.util.paginator.PageRender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.SessionStatus
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.data.domain.Pageable
import javax.validation.Valid

@Controller
@SessionAttributes("cliente")
class ClienteController {

    @Autowired
    lateinit var clienteservice: IClienteService

    @RequestMapping("/listar")
    fun listar(@RequestParam(name = "page", defaultValue = "0") page: Int, model: Model): String{
        val requestPageable: Pageable = PageRequest.of(page,4)
        val cliente  = clienteservice.findAll(requestPageable)
        val pageRender = PageRender<Cliente>("/listar", cliente)
        model.addAttribute("titulo","Listado de Cliente")
        model.addAttribute("clientes", cliente)
        model.addAttribute("page", pageRender)
        return "listar"
    }

    @GetMapping("/form")
    fun crear(model: MutableMap<String, Any?>): String{
        val cliente = Cliente()
        model["titulo"] = "Formulario del Cliente"
        model["cliente"] = cliente
        return "form"
    }

    @PostMapping("/form")
    fun guardar(@Valid cliente: Cliente, result: BindingResult, model: Model, flash: RedirectAttributes, status: SessionStatus): String{
        if (result.hasErrors()) {
            model.addAttribute("titulo","Formulario del Cliente")
            return "form"
        }
        val mensajeFlash = if (cliente.id != null) {
            "Cliente editado con exito"
        } else {
            "Cliente creado con exito"
        }
        clienteservice.save(cliente)
        status.setComplete()
        flash.addFlashAttribute("success",mensajeFlash)
        return "redirect:listar"
    }

    @GetMapping("/form/{id}")
    fun editar(@PathVariable("id") id: Long, model: MutableMap<String, Any?>, flash: RedirectAttributes): String{
        var cliente: Cliente? = null
        if (id>0){
             cliente = clienteservice.findOne(id)
        }else {
            flash.addFlashAttribute("error","El id no puede ser 0 o menor")
            return "redirect:/listar"
        }
        model["titulo"] = "Formulario del Cliente"
        model["cliente"] = cliente
        return "form"
    }

    @RequestMapping("/eliminar/{id}")
    fun eliminar(@PathVariable("id") id: Long, model: Model, flash: RedirectAttributes): String{
        if (id>0) {
            clienteservice.delete(id)
            flash.addFlashAttribute("success","Cliente eliminado con exito")
        } else{
            flash.addFlashAttribute("error","El id del cliente es menor a 0")
        }
        return "redirect:/listar"
    }
}