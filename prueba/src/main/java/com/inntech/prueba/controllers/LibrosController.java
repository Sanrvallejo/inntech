/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inntech.prueba.controllers;

import com.inntech.prueba.dto.LibroDTO;
import com.inntech.prueba.model.Libro;
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
@RequestMapping("/libros")
public class LibrosController {
    
    @Autowired
    private DataSource dataSource;
    
    @PostMapping("/nuevo-libro")
    public ResponseEntity<String> crearLibro(
        @RequestBody() LibroDTO libroDto
    ) {
        try(Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{call PKG_LIBROS_CUD.CREAR_LIBRO(?, ?)}")) {
            
            cs.setString(1, libroDto.getTitulo());
            cs.setInt(2, libroDto.getAutorID());
            cs.execute();
            
            return ResponseEntity.ok("Libro creado");
        }catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        } 
    }
    
    @PutMapping("/libro-actualizado")
    public ResponseEntity<String> actualizarLibro(
        @RequestBody() Libro libro
    ) {
        try(Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{call PKG_LIBROS_CUD.ACTUALIZAR_LIBRO(?, ?)}")) {
            
            cs.setInt(1, libro.getId());
            cs.setString(2, libro.getTitulo());
            cs.execute();
            
            return ResponseEntity.ok("Libro actualizado");
        }catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
    @DeleteMapping("/libro-eliminado/{id}")
    public ResponseEntity<String> eliminarLibro(
            @PathVariable() int id
    ) {
        try (Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{call PKG_LIBROS_CUD.ELIMINAR_LIBRO(?)}")) {
            
            cs.setInt(1, id);
            cs.execute();
            
            return ResponseEntity.ok("Libro eliminado");
        }catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/libro/{id}")
    public ResponseEntity<LibroDTO> libroById(
            @PathVariable() int id
    ) {
        
        try(Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{ ? = call PKG_LIBROS_READ.obtener_libro(?) }")) {
            
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setInt(2, id);
            cs.execute();
            
            ResultSet rs = (ResultSet) cs.getObject(1);

            if (rs.next()) {
                LibroDTO libro = new LibroDTO();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutorID(rs.getInt("autor_id"));

                return ResponseEntity.ok(libro);
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<LibroDTO>> listarAutores() {
        List<LibroDTO> autores = new ArrayList<>();
        
        try(Connection conn = dataSource.getConnection();
                CallableStatement cs = conn.prepareCall("{? = call PKG_LIBROS_READ.LISTAR_LIBROS}")) {
            
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            
            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {                
                LibroDTO libro = new LibroDTO();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutorID(rs.getInt("autor_id"));
                
                autores.add(libro);
            }
            
            return ResponseEntity.ok(autores);
            
        }catch (SQLException e){
            e.printStackTrace();;
            return ResponseEntity.status(500).build();
        }
            
    }
}
