package com.blumbit.compras_ventas.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.blumbit.compras_ventas.dto.request.ProductoFilterCriteria;
import com.blumbit.compras_ventas.entity.Producto;

import jakarta.persistence.criteria.Predicate;

public class ProductoSpecification {

    public static Specification<Producto> createSpecification(ProductoFilterCriteria criteria){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(criteria.getNombre() != null && !criteria.getNombre().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")),
                 "%"+criteria.getNombre().toLowerCase()+"%")); 
            }

            if(criteria.getDescripcion() != null && !criteria.getDescripcion().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("descripcion")),
                 "%"+criteria.getDescripcion().toLowerCase()+"%"));
            }

            if(criteria.getCodigoBarra() != null && !criteria.getCodigoBarra().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("codigoBarra")),
                 "%"+criteria.getCodigoBarra().toLowerCase()+"%"));
            }

            if(criteria.getMarca() != null && !criteria.getMarca().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("marca")),
                 "%"+criteria.getMarca().toLowerCase()+"%"));
            }

            if(criteria.getNombreCategoria() != null && !criteria.getNombreCategoria().trim().isEmpty()){
                predicates.add(criteriaBuilder.like(root.join("categoria").get("nombre"), "%" + criteria.getNombreCategoria() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
