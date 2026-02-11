package com.blumbit.compras_ventas.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.compras_ventas.entity.AlmacenProducto;

public interface AlmacenProductoRepository extends ListCrudRepository<AlmacenProducto, Integer>{

    List<AlmacenProducto> findByAlmacenId(Integer almacenId);
}
