package pe.edu.cibertec.pedidoservice.service;

import org.springframework.stereotype.Service;
import pe.edu.cibertec.pedidoservice.client.PagoClient;
import pe.edu.cibertec.pedidoservice.dto.DetallePedidoDTO;
import pe.edu.cibertec.pedidoservice.dto.PagoDTO;
import pe.edu.cibertec.pedidoservice.dto.PedidoResponseDTO;
import pe.edu.cibertec.pedidoservice.dto.ResumenConsolidadoClienteDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PagoClient pagoClient;

    // "Base de datos" en memoria
    private final Map<Long, PedidoResponseDTO> pedidos = new HashMap<>();

    public PedidoService(PagoClient pagoClient) {
        this.pagoClient = pagoClient;
        cargarDatosDePrueba();
    }

    private void cargarDatosDePrueba() {
        // Pedido 1 - cliente 100
        List<DetallePedidoDTO> detalles1 = List.of(
                new DetallePedidoDTO(1L, "Laptop Lenovo", 1, 2500.00),
                new DetallePedidoDTO(2L, "Mouse inalambrico", 2, 50.00)
        );
        guardarPedido(1L, "PED-0001", 100L, "PENDIENTE", detalles1);

        // Pedido 2 - cliente 100
        List<DetallePedidoDTO> detalles2 = List.of(
                new DetallePedidoDTO(3L, "Teclado mecanico", 1, 180.00)
        );
        guardarPedido(2L, "PED-0002", 100L, "PENDIENTE", detalles2);

        // Pedido 3 - cliente 200
        List<DetallePedidoDTO> detalles3 = List.of(
                new DetallePedidoDTO(4L, "Monitor 24''", 1, 700.00)
        );
        guardarPedido(3L, "PED-0003", 200L, "PENDIENTE", detalles3);
    }

    private void guardarPedido(Long id, String codigo, Long clienteId, String estado, List<DetallePedidoDTO> detalles) {
        PedidoResponseDTO pedido = new PedidoResponseDTO();
        pedido.setIdPedido(id);
        pedido.setCodigoSeguimiento(codigo);
        pedido.setFechaEmision(LocalDateTime.now());
        pedido.setClienteId(clienteId);
        pedido.setEstadoPedido(estado);
        pedido.setListaDetalles(detalles);
        pedido.setMontoTotalCalculado(calcularMontoTotal(detalles));
        pedidos.put(id, pedido);
    }

    private Double calcularMontoTotal(List<DetallePedidoDTO> detalles) {
        return detalles.stream()
                .mapToDouble(DetallePedidoDTO::getSubtotal)
                .sum();
    }


    public PedidoResponseDTO obtenerPedido(Long idPedido) {
        PedidoResponseDTO pedido = pedidos.get(idPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("No existe el pedido con id " + idPedido);
        }
        pedido.setMontoTotalCalculado(calcularMontoTotal(pedido.getListaDetalles()));
        return pedido;
    }


    public PedidoResponseDTO verificarYCompletar(Long idPedido) {
        PedidoResponseDTO pedido = obtenerPedido(idPedido);

        PagoDTO pago = pagoClient.obtenerPagoPorPedido(idPedido);

        if (pago != null
                && "APROBADO".equalsIgnoreCase(pago.getEstadoPago())
                && pago.getMontoPagado() != null
                && pago.getMontoPagado().doubleValue() == pedido.getMontoTotalCalculado().doubleValue()) {
            pedido.setEstadoPedido("COMPLETADO");
        }

        return pedido;
    }


    public ResumenConsolidadoClienteDTO consolidadoCliente(Long clienteId) {
        List<PedidoResponseDTO> pedidosDelCliente = pedidos.values().stream()
                .filter(p -> p.getClienteId().equals(clienteId))
                .collect(Collectors.toList());

        List<PagoDTO> pagosDelCliente = pagoClient.obtenerPagosPorCliente(clienteId);
        if (pagosDelCliente == null) {
            pagosDelCliente = new ArrayList<>();
        }

        double totalPedidos = pedidosDelCliente.stream()
                .mapToDouble(p -> calcularMontoTotal(p.getListaDetalles()))
                .sum();

        double totalPagado = pagosDelCliente.stream()
                .filter(p -> "APROBADO".equalsIgnoreCase(p.getEstadoPago()))
                .mapToDouble(PagoDTO::getMontoPagado)
                .sum();

        ResumenConsolidadoClienteDTO resumen = new ResumenConsolidadoClienteDTO();
        resumen.setClienteId(clienteId);
        resumen.setNombreCliente("Cliente " + clienteId); // dato de ejemplo
        resumen.setTotalPedidosRealizados(pedidosDelCliente.size());
        resumen.setMontoTotalAcumulado(totalPagado);
        resumen.setHistorialPedidos(pedidosDelCliente);
        resumen.setHistorialPagos(pagosDelCliente);
        resumen.setEstadoCuenta(totalPagado >= totalPedidos ? "AL_DIA" : "CON_DEUDAS");

        return resumen;
    }
}
