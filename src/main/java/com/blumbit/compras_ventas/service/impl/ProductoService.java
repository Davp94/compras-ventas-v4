package com.blumbit.compras_ventas.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.common.PageableRequest;
import com.blumbit.compras_ventas.common.PageableResponse;
import com.blumbit.compras_ventas.dto.request.PermisoRequest;
import com.blumbit.compras_ventas.dto.request.ProductoFilterCriteria;
import com.blumbit.compras_ventas.dto.response.PermisoResponse;
import com.blumbit.compras_ventas.dto.response.ProductoResponse;
import com.blumbit.compras_ventas.entity.Producto;
import com.blumbit.compras_ventas.repository.ProductoRepository;
import com.blumbit.compras_ventas.repository.specification.ProductoSpecification;
import com.blumbit.compras_ventas.service.spec.IProductoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;


    @Override
    public PageableResponse<ProductoResponse> getProductosPaginacion(
            PageableRequest<ProductoFilterCriteria> pageableRequest) {
        
        Sort sort = pageableRequest.getSortOrder().equalsIgnoreCase("asc") 
        ? Sort.by(pageableRequest.getSortField()).ascending()
        : Sort.by(pageableRequest.getSortField()).descending();

        Pageable pageable = PageRequest.of(
            pageableRequest.getPageNumber(), 
            pageableRequest.getPageSize(), 
            sort
        );

        Specification<Producto> specification = Specification.where(null);
        if(pageableRequest.getCriterials() !=null){
          specification = ProductoSpecification.createSpecification(pageableRequest.getCriterials());
        }

        Page<Producto> productoPage = productoRepository.findAll(specification, pageable);

        return PageableResponse.<ProductoResponse>builder()
            .content(productoPage.getContent().stream().map(ProductoResponse::fromEntity).toList())
            .pageNumber(productoPage.getNumber())
            .pageSize(productoPage.getSize())
            .totalElements(productoPage.getTotalElements())
            .totalPages(productoPage.getTotalPages())
            .build();
        
    }

    @Override
    public List<ProductoResponse> getProductosByAlmacen(Integer almacenId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductosByAlmacen'");
    }



}
