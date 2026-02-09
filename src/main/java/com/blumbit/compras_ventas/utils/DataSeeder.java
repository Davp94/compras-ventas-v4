package com.blumbit.compras_ventas.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.PermisoRepository;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.repository.UsuarioRepository;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner{
    
    private final UsuarioRepository usuarioRepository;
    private final PermisoRepository permisoRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        List<String> generos = List.of("MASCULINO", "FEMENINO", "OTRO");
        
        List<String> acciones = List.of("create", "read", "update", "delete");
        List<String> resources = List.of("usuarios", "roles", "permisos");
        
        //permisos seeders
        List<Permiso> permisos = new ArrayList<>();
 
        for(String resource: resources) {
            for(String accion: acciones){
                Permiso permiso = Permiso.builder()
                .nombre(accion.toUpperCase()+"_"+resource.toUpperCase())
                .recurso(resource)
                .accion(accion)
                .build();
                permisos.add(permisoRepository.save(permiso));
            }
        }

        //rol seeders
        List<Rol> roles = new ArrayList<>();
        if(rolRepository.count() == 0){
            Rol adminRol = Rol.builder()
            .nombreCompleto("ADMIN")
            .descripcion("Administrador con acceso completo a sistema")
            .build(); 
            roles.add(rolRepository.save(adminRol));

        }
        //
        if(usuarioRepository.count() == 0){
            int minAge = 18;
            int maxAge = 65;
            for(int i = 0; i<10;i++){
                String correo = faker.internet().emailAddress();
                Usuario usuario = Usuario.builder()
                .correo(correo)
                .nombre(correo)
                .password(passwordEncoder.encode("123456"))
                .build();

                usuario = usuarioRepository.save(usuario);
                Persona persona = Persona.builder()
                .apellidos(faker.name().lastName())
                .nombres(faker.name().firstName())
                .direccion(faker.address().fullAddress())
                .genero(generos.get(random.nextInt(generos.size())))
                .telefono(faker.phoneNumber().cellPhone())
                .usuario(usuario)
                .nacionalidad(faker.address().country())
                .fechaNacimiento(LocalDate.now().minusYears(minAge+random.nextInt(maxAge-minAge+1))
                .minusDays(random.nextInt(365)))
                .build();
                persona = personaRepository.save(persona);
            }
        }
    }

}
