/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inntech.prueba.controllers;

import com.inntech.prueba.dto.AutorDTO;
import com.inntech.prueba.model.Autor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/autores")
public class AutoresController {
    
    @Autowired
    private DataSource dataSource;
    
    @PostMapping("/autor-creado")
    public ResponseEntity<String> crearAutor(
            @RequestBody() AutorDTO autorDto
    ){
        try (Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{call PKG_AUTORES_CUD.CREAR_AUTOR(?, ?)}")) {
            
            cs.setString(1, autorDto.getNombre());
            cs.setString(2, autorDto.getNacionalidad());
            cs.execute();
            
            return ResponseEntity.ok("Autor creado");
            
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/autor-actualizado")
    public ResponseEntity<String> actualizarAutor(
    @RequestBody() Autor autor
    ) {
        try(Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{call PKG_AUTORES_CUD.ACTUALIZAR_AUTOR(?, ?, ?)}")) {
            
            cs.setInt(1, autor.getId());
            cs.setString(2, autor.getNombre());
            cs.setString(3, autor.getNacionalidad());
            
            cs.execute();
            
            return ResponseEntity.ok("Autor actualizado");
        }catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/autor-eliminado/{id}")
    public ResponseEntity<String> eliminarAutor(
            @PathVariable() int id
    ) {
        try (Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{call PKG_AUTORES_CUD.ELIMINAR_AUTOR(?)}")) {
            
            cs.setInt(1, id);
            cs.execute();
            
            return ResponseEntity.ok("Autor eliminado");
        }catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/autor/{id}")
    public ResponseEntity<AutorDTO> autorById(
            @PathVariable() int id
    ) {
        
        try(Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{ ? = call PKG_AUTORES_READ.obtener_autor(?) }")) {
            
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setInt(2, id);
            cs.execute();
            
            ResultSet rs = (ResultSet) cs.getObject(1);

            if (rs.next()) {
                AutorDTO autor = new AutorDTO();
                autor.setId(rs.getInt("id"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));

                return ResponseEntity.ok(autor);
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<AutorDTO>> listarAutores() {
        List<AutorDTO> autores = new ArrayList<>();
        
        try(Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{? = call PKG_AUTORES_READ.LISTAR_AUTORES}")) {
            
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {                
                AutorDTO autor = new AutorDTO();
                autor.setId(rs.getInt("id"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                
                autores.add(autor);
            }
            
            return ResponseEntity.ok(autores);
            
        }catch (SQLException e){
            e.printStackTrace();;
            return ResponseEntity.status(500).build();
        }
            
    }
}
