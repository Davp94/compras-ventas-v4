package com.blumbit.compras_ventas.service.spec;

import java.util.List;
import java.util.Optional;

import com.blumbit.compras_ventas.dto.RolRequest;
import com.blumbit.compras_ventas.entity.Rol;

public interface IRolService {

    List<Rol> findAllRoles();

    Rol findRolById(Integer id);

    Rol findRolByNombre(String nombre);

    Rol createRol(RolRequest rol);

    Rol updateRol(Integer id, RolRequest rol);

    void deleteById(Integer id);
    
}
