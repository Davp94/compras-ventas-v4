package com.blumbit.compras_ventas.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blumbit.compras_ventas.entity.Almacen;
import com.blumbit.compras_ventas.entity.AlmacenProducto;
import com.blumbit.compras_ventas.entity.Categoria;
import com.blumbit.compras_ventas.entity.ClienteProveedor;
import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.entity.Persona;
import com.blumbit.compras_ventas.entity.Producto;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.entity.Sucursal;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.enums.TipoClienteProveedor;
import com.blumbit.compras_ventas.repository.AlmacenProductoRepository;
import com.blumbit.compras_ventas.repository.AlmacenRepository;
import com.blumbit.compras_ventas.repository.CategoriaRepository;
import com.blumbit.compras_ventas.repository.ClienteProveedorRepository;
import com.blumbit.compras_ventas.repository.PermisoRepository;
import com.blumbit.compras_ventas.repository.PersonaRepository;
import com.blumbit.compras_ventas.repository.ProductoRepository;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.repository.SucursalRepository;
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
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final AlmacenRepository almacenRepository;
    private final AlmacenProductoRepository almacenProductoRepository;
    private final ClienteProveedorRepository clienteProveedorRepository;

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

        //TODO add data seeding
        
        // Seed Categorias (10)
        List<Categoria> categorias = new ArrayList<>();
        if(categoriaRepository.count() == 0) {
            String[] categoriaNombres = {"Electrónica", "Ropa", "Alimentos", "Libros", "Hogar", 
                                        "Deportes", "Juguetes", "Belleza", "Automotriz", "Herramientas"};
            for(String nombre : categoriaNombres) {
                Categoria categoria = Categoria.builder()
                    .nombre(nombre)
                    .descripcion(faker.lorem().sentence())
                    .build();
                categorias.add(categoriaRepository.save(categoria));
            }
        }
        
        // Seed Productos (1000)
        if(productoRepository.count() == 0) {
            for(int i = 0; i < 1000; i++) {
                Producto producto = Producto.builder()
                    .nombre(faker.commerce().productName())
                    .descripcion(faker.lorem().sentence())
                    .unidadMedida(faker.commerce().department())
                    .codigoBarra(faker.code().asin())
                    .precioVentaActual(new java.math.BigDecimal(faker.number().numberBetween(10, 500)))
                    .marca(faker.company().name())
                    .imagen(faker.internet().url())
                    .estado(true)
                    .categoria(categorias.get(random.nextInt(categorias.size())))
                    .build();
                productoRepository.save(producto);
            }
        }
        
        // Seed Sucursales (10)
        List<Sucursal> sucursales = new ArrayList<>();
        if(sucursalRepository.count() == 0) {
            for(int i = 0; i < 10; i++) {
                Sucursal sucursal = Sucursal.builder()
                    .nombre("Sucursal " + (i + 1))
                    .direccion(faker.address().fullAddress())
                    .telefono(faker.phoneNumber().cellPhone())
                    .ciudad(faker.address().city())
                    .build();
                sucursales.add(sucursalRepository.save(sucursal));
            }
        }
        
        // Seed Almacenes (4 per sucursal = 40 total)
        List<Almacen> almacenes = new ArrayList<>();
        if(almacenRepository.count() == 0) {
            for(Sucursal sucursal : sucursales) {
                for(int i = 0; i < 4; i++) {
                    Almacen almacen = new Almacen();
                    almacen.setNombre("Almacén " + (i + 1) + " - " + sucursal.getNombre());
                    almacen.setCodigo("ALM" + sucursal.getId() + String.format("%02d", i + 1));
                    almacen.setDescripcion(faker.lorem().sentence());
                    almacen.setDireccion(faker.address().fullAddress());
                    almacen.setTelefono(faker.phoneNumber().cellPhone());
                    almacen.setCiudad(sucursal.getCiudad());
                    almacen.setSucursal(sucursal);
                    almacenes.add(almacenRepository.save(almacen));
                }
            }
        }
        
        // Seed AlmacenProducto (200 random assignments)
        if(almacenProductoRepository.count() == 0) {
            List<Producto> productos = productoRepository.findAll();
            for(int i = 0; i < 200; i++) {
                AlmacenProducto almacenProducto = new AlmacenProducto();
                almacenProducto.setAlmacen(almacenes.get(random.nextInt(almacenes.size())));
                almacenProducto.setProducto(productos.get(random.nextInt(productos.size())));
                almacenProducto.setStock(random.nextInt(1000) + 1);
                almacenProducto.setFechaActualizacion(java.time.LocalDateTime.now());
                almacenProductoRepository.save(almacenProducto);
            }
        }
        
        // Seed ClienteProveedor (50)
        if(clienteProveedorRepository.count() == 0) {
            TipoClienteProveedor[] tipos = TipoClienteProveedor.values();
            for(int i = 0; i < 50; i++) {
                ClienteProveedor clienteProveedor = ClienteProveedor.builder()
                    .tipo(tipos[random.nextInt(tipos.length)])
                    .razonSocial(faker.company().name())
                    .nroIdentificacion(faker.number().digits(10))
                    .telefono(faker.phoneNumber().cellPhone())
                    .direccion(faker.address().fullAddress())
                    .correo(faker.internet().emailAddress())
                    .estado(true)
                    .build();
                clienteProveedorRepository.save(clienteProveedor);
            }
        }
    }
}
