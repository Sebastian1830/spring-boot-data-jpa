package com.ss.springboot.app.util.paginator

import org.springframework.data.domain.Page

class PageRender<T> (var url: String, var page: Page<T> ){
    var totalPaginas: Int = 0
    var numeroElementoPagina: Int = 0
    var paginaActual: Int = 0
    var paginas: MutableList<PageItem> = mutableListOf()
    var desde: Int
    var hasta: Int

    init {
        this.numeroElementoPagina = this.page.size
        this.totalPaginas = this.page.totalPages
        this.paginaActual = this.page.number + 1
        if (totalPaginas <= numeroElementoPagina) {
            this.desde = 1
            hasta = totalPaginas
        } else {
            if (paginaActual <= numeroElementoPagina/2){
                this.desde = 1
                hasta = numeroElementoPagina
            } else if (paginaActual >= totalPaginas - numeroElementoPagina/2){
                this.desde = totalPaginas - numeroElementoPagina + 1
                hasta = numeroElementoPagina
            }else {
                this.desde = paginaActual - numeroElementoPagina/2
                hasta = numeroElementoPagina
            }
        }

        for (i in 0 until hasta){
            this.paginas.add(PageItem(desde + 1, paginaActual == desde + 1))
        }
    }

    fun isFirst(): Boolean {
        return page.isFirst
    }

    fun isLast(): Boolean {
        return page.isLast
    }

    fun isHasNext(): Boolean {
        return page.hasNext()
    }

    fun isHasPrevious(): Boolean {
        return page.hasPrevious()
    }
}