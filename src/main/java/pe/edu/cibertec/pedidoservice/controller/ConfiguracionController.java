
package pe.edu.cibertec.pedidoservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RefreshScope
public class ConfiguracionController {

    @Value("${ecom.impuesto-igv}")
    private Double impuestoIgv;

    @Value("${ecom.monto-minimo-envio-gratis}")
    private Double montoMinimoEnvioGratis;

    @Value("${ecom.mensaje-bienvenida}")
    private String mensajeBienvenida;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @GetMapping("/api/v1/pedidos/configuracion")
    public Map<String, Object> obtenerConfiguracion() {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("impuesto-igv", impuestoIgv);
        respuesta.put("monto-minimo-envio-gratis", montoMinimoEnvioGratis);
        respuesta.put("mensaje-bienvenida", mensajeBienvenida);
        respuesta.put("datasource.url", datasourceUrl);
        return respuesta;
    }
}
