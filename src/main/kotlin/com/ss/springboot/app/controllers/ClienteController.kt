package com.ss.springboot.app.controllers

import com.ss.springboot.app.models.entity.Cliente
import com.ss.springboot.app.models.service.IClienteService
import com.ss.springboot.app.models.service.IUploadFileService
import com.ss.springboot.app.util.paginator.PageRender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.SessionStatus
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@Controller
@SessionAttributes("cliente")
class ClienteController {

    @Autowired
    lateinit var clienteservice: IClienteService

    @Autowired
    lateinit var uploadService: IUploadFileService

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

    @GetMapping("/ver/{id}")
    fun ver(@PathVariable("id") id: Long, model: MutableMap<String,Any>, flash: RedirectAttributes): String{
        val cliente = clienteservice.findOne(id)
        if (cliente == null){
            flash.addFlashAttribute("error","El cliente no existe en la base de datos")
            return "redirect:/listar"
        }
        model["cliente"] = cliente
        model["titulo"] = "Detalle Cliente: ${cliente.nombre} ${cliente.apellido}"
        return "ver"
    }

    @GetMapping("/uploads/{filename:.+}")
    fun verFoto(@PathVariable filename: String): ResponseEntity<Resource>{
        val recurso = uploadService.load(filename)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.filename+"\"")
                .body(recurso)
    }

    @GetMapping("/form")
    fun crear(model: MutableMap<String, Any?>): String{
        val cliente = Cliente()
        model["titulo"] = "Formulario del Cliente"
        model["cliente"] = cliente
        return "form"
    }

    @PostMapping("/form")
    fun guardar(@Valid cliente: Cliente, result: BindingResult, model: Model, @RequestParam("file") foto: MultipartFile, flash: RedirectAttributes, status: SessionStatus): String{
        if (result.hasErrors()) {
            model.addAttribute("titulo","Formulario del Cliente")
            return "form"
        }

        if (!foto.isEmpty && foto.contentType == "image/jpeg"){
            if (cliente.id != null && cliente.id!! > 0 && cliente.foto != null && cliente.foto.isNotEmpty()){
                uploadService.delete(cliente.foto)
            }

            val uniqueFileName = uploadService.copy(foto)

            flash.addFlashAttribute("info", "Ha subido correctamente la imagen $uniqueFileName")
            cliente.foto = uniqueFileName
        } else {
            flash.addFlashAttribute("error", "El formato de la imagen no es el permitido")
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
        lateinit var cliente: Cliente
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
            val cliente = clienteservice.findOne(id)
            clienteservice.delete(id)
            flash.addFlashAttribute("success","Cliente eliminado con exito")

            if (uploadService.delete(cliente.foto)){
                flash.addFlashAttribute("info","Foto ${cliente.foto} eliminado con exito")
            }

        } else{
            flash.addFlashAttribute("error","El id del cliente es menor a 0")
        }
        return "redirect:/listar"
    }
}