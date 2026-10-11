package pe.edu.cibertec.pedidoservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.pedidoservice.dto.PedidoResponseDTO;
import pe.edu.cibertec.pedidoservice.dto.ResumenConsolidadoClienteDTO;
import pe.edu.cibertec.pedidoservice.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{idPedido}")
    public PedidoResponseDTO obtenerPedido(@PathVariable Long idPedido) {
        return pedidoService.obtenerPedido(idPedido);
    }

    @GetMapping("/verificar-y-completar/{idPedido}")
    public PedidoResponseDTO verificarYCompletar(@PathVariable Long idPedido) {
        return pedidoService.verificarYCompletar(idPedido);
    }

    @GetMapping("/consolidado-cliente/{clienteId}")
    public ResumenConsolidadoClienteDTO consolidadoCliente(@PathVariable Long clienteId) {
        return pedidoService.consolidadoCliente(clienteId);
    }
}
