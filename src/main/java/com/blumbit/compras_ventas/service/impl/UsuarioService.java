package com.blumbit.compras_ventas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.request.UsuarioRequest;
import com.blumbit.compras_ventas.dto.response.UsuarioResponse;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;
import com.blumbit.compras_ventas.service.spec.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PersonaRepository personaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public List<UsuarioResponse> findAllUsuarios() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllUsuarios'");
    }

    @Override
    public UsuarioResponse findUsuarioById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUsuarioById'");
    }

    @Override
    public UsuarioResponse createUsuario(UsuarioRequest usuarioRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUsuario'");
    }

    @Override
    public UsuarioResponse updateUsuario(Integer id, UsuarioRequest usuarioRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUsuario'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
