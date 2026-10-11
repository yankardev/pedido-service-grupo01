package pe.edu.cibertec.pedidoservice.dto;

import java.util.List;

public class ResumenConsolidadoClienteDTO {

    private Long clienteId;
    private String nombreCliente;
    private Integer totalPedidosRealizados;
    private Double montoTotalAcumulado;
    private List<PedidoResponseDTO> historialPedidos;
    private List<PagoDTO> historialPagos;
    private String estadoCuenta; // 'AL_DIA', 'CON_DEUDAS'

    public ResumenConsolidadoClienteDTO() {
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getTotalPedidosRealizados() {
        return totalPedidosRealizados;
    }

    public void setTotalPedidosRealizados(Integer totalPedidosRealizados) {
        this.totalPedidosRealizados = totalPedidosRealizados;
    }

    public Double getMontoTotalAcumulado() {
        return montoTotalAcumulado;
    }

    public void setMontoTotalAcumulado(Double montoTotalAcumulado) {
        this.montoTotalAcumulado = montoTotalAcumulado;
    }

    public List<PedidoResponseDTO> getHistorialPedidos() {
        return historialPedidos;
    }

    public void setHistorialPedidos(List<PedidoResponseDTO> historialPedidos) {
        this.historialPedidos = historialPedidos;
    }

    public List<PagoDTO> getHistorialPagos() {
        return historialPagos;
    }

    public void setHistorialPagos(List<PagoDTO> historialPagos) {
        this.historialPagos = historialPagos;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
}
