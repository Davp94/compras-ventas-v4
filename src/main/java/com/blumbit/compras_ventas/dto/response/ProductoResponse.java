package com.blumbit.compras_ventas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precioVentaActual;
    private String codigoBarra;
    private String imagen;
    private String nombreCategoria;
    private Boolean estado;

}
