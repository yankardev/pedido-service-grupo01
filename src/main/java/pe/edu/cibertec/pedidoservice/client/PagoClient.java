package pe.edu.cibertec.pedidoservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.cibertec.pedidoservice.dto.PagoDTO;

import java.util.List;

/**
 * Cliente Feign hacia pago-service-grupo01.
 *
 * IMPORTANTE: no se pone "url", solo "name". Eso hace que Feign busque la
 * dirección real del microservicio a través de Eureka (lb://), tal como
 * pide el enunciado. Para que esto funcione, deben estar corriendo:
 *  1. discovery-server (puerto 8761)
 *  2. pago-service-grupo01, registrado en Eureka con ese mismo nombre
 */
@FeignClient(name = "pago-service-grupo01")
public interface PagoClient {

    @GetMapping("/api/v1/pagos/pedido/{idPedido}")
    PagoDTO obtenerPagoPorPedido(@PathVariable("idPedido") Long idPedido);

    @GetMapping("/api/v1/pagos/cliente/{clienteId}")
    List<PagoDTO> obtenerPagosPorCliente(@PathVariable("clienteId") Long clienteId);
}
