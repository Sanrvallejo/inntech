/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inntech.prueba.dto;

/**
 *
 * @author Usuario
 */
public class LibroDTO {
    private Integer id;
    private String titulo;
    private Integer autorID;

    public LibroDTO() {
    }

    public LibroDTO(Integer id, String titulo, Integer autorID) {
        this.id = id;
        this.titulo = titulo;
        this.autorID = autorID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAutorID() {
        return autorID;
    }

    public void setAutorID(Integer autorID) {
        this.autorID = autorID;
    }
    
}
