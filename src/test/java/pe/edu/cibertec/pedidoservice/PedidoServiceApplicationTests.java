package pe.edu.cibertec.pedidoservice;

import org.junit.jupiter.api.Test;

class PedidoServiceApplicationTests {

    // Se deja sin @SpringBootTest a propósito: levantar el contexto completo
    // aquí requeriría tener Eureka y el Config Server corriendo también
    // durante cada build. El arranque real se prueba manualmente con Run.
    @Test
    void contextLoads() {
    }

}
