package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.RolRequest;

@RestController
@RequestMapping("/rol")
public class RolController {

    @GetMapping
    public String listarRoles(){
        return "Listado de Roles";
    }

    @GetMapping("/{rolId}")
    public String obtenerRolPorId(@PathVariable Integer rolId){
        return "Rol con i: d" + rolId;
    }

    @PostMapping
    public String crearRol(@RequestBody RolRequest rolRequest){
        return "Rol creado";
    }

    @PutMapping("/{rolId}")
    public String actualizarRol(@PathVariable Integer rolId, @RequestBody RolRequest rolRequest){
        return "Rol actualizado";
    }

    @DeleteMapping("/{id}")
    public String borrarRol(@PathVariable Integer id){
        return "Rol eliminado";
    }

}
