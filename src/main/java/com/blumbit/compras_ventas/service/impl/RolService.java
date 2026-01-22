package com.blumbit.compras_ventas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.RolRequest;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.service.spec.IRolService;

@Service
public class RolService implements IRolService{

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Rol findRolById(Integer id) {
        return rolRepository.findById(id).get();
    }

    @Override
    public Rol findRolByNombre(String nombre) {
       return rolRepository.findByNombre(nombre).get();
    }

    @Override
    public Rol createRol(RolRequest rol) {
       Rol rolToCreate = new Rol();
       rolToCreate.setNombreCompleto(rol.getNombre());
       rolToCreate.setDescripcion(rol.getDescripcion());
       return rolRepository.save(rolToCreate);
    }

    @Override
    public Rol updateRol(Integer id, RolRequest rol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRol'");
    }

    @Override
    public void deleteById(Integer id) {
       rolRepository.deleteById(id);
    }


}
