
package Gestion_Contratos;

import Gestion_Cliente.Cliente;
import Gestion_Vehiculos.Vehiculo;
import Gestion_Vehiculos.VehiculosHashMap;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ContratoAlquiler {

    private String numeroContrato;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double monto;
    private String estado; // Activo, Finalizado, Cancelado

    private static ArrayList<ContratoAlquiler> contratos = new ArrayList<>();

    private ContratoAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.numeroContrato = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = "Activo";

        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (dias == 0) dias = 1;
        this.monto = dias * 50.0;
    }

    public static String crearContrato(String cedulaCliente, String placaVehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            if (cedulaCliente == null || cedulaCliente.trim().isEmpty()) {
                return "Cédula del cliente no proporcionada.";
            }

            if (placaVehiculo == null || placaVehiculo.trim().isEmpty()) {
                return "Placa del vehículo no proporcionada.";
            }

            if (fechaInicio == null || fechaFin == null) {
                return "Fechas inválidas: no pueden ser nulas.";
            }

            Cliente cliente = Cliente.buscarClientePorCedula(cedulaCliente);
            if (cliente == null) return "Cliente no encontrado.";

            Vehiculo vehiculo;
            try {
                vehiculo = VehiculosHashMap.buscarVehiculo(placaVehiculo);
            } catch (NoSuchElementException e) {
                return "Vehículo no encontrado.";
            } catch (Exception e) {
                return "Error al buscar vehículo: " + e.getMessage();
            }

            if (fechaInicio.isBefore(LocalDate.now())) {
                return "La fecha de inicio no puede ser anterior a hoy.";
            }

            if (!fechaFin.isAfter(fechaInicio)) {
                return "La fecha de fin debe ser posterior a la de inicio.";
            }

            for (ContratoAlquiler c : contratos) {
                if (c.getVehiculo().getPlaca().equals(placaVehiculo) && c.getEstado().equals("Activo")) {
                    if (!(fechaFin.isBefore(c.getFechaInicio()) || fechaInicio.isAfter(c.getFechaFin()))) {
                        return "El vehículo ya tiene un contrato activo en ese rango de fechas.";
                    }
                }
            }

            ContratoAlquiler nuevoContrato = new ContratoAlquiler(cliente, vehiculo, fechaInicio, fechaFin);
            contratos.add(nuevoContrato);
            vehiculo.setEstado("En alquiler");

            return "Contrato creado exitosamente. Número: " + nuevoContrato.getNumeroContrato();
        } catch (Exception e) {
            return "Error al crear contrato: " + e.getMessage();
        }
    }

    public static String finalizarContrato(String numeroContrato) {
        try {
            if (numeroContrato == null || numeroContrato.trim().isEmpty()) {
                return "Número de contrato no proporcionado.";
            }

            for (ContratoAlquiler c : contratos) {
                if (c.getNumeroContrato().equals(numeroContrato)) {
                    if (!c.getEstado().equals("Activo")) {
                        return "Solo los contratos activos pueden finalizarse.";
                    }
                    c.setEstado("Finalizado");
                    c.getVehiculo().setEstado("Disponible");
                    return "Contrato finalizado correctamente.";
                }
            }

            return "Contrato no encontrado.";
        } catch (Exception e) {
            return "Error al finalizar contrato: " + e.getMessage();
        }
    }

    public static String cancelarContrato(String numeroContrato) {
        try {
            if (numeroContrato == null || numeroContrato.trim().isEmpty()) {
                return "Número de contrato no proporcionado.";
            }

            for (ContratoAlquiler c : contratos) {
                if (c.getNumeroContrato().equals(numeroContrato)) {
                    if (c.getEstado().equals("Finalizado")) {
                        return "No se puede cancelar un contrato ya finalizado.";
                    }
                    c.setEstado("Cancelado");
                    c.getVehiculo().setEstado("Disponible");
                    return "Contrato cancelado correctamente.";
                }
            }

            return "Contrato no encontrado.";
        } catch (Exception e) {
            return "Error al cancelar contrato: " + e.getMessage();
        }
    }

    public static ContratoAlquiler buscarPorNumero(String numeroContrato) {
        for (ContratoAlquiler c : contratos) {
            if (c.getNumeroContrato().equals(numeroContrato)) return c;
        }
        return null;
    }

    public static ArrayList<ContratoAlquiler> buscarPorCedulaCliente(String cedula) {
        ArrayList<ContratoAlquiler> resultado = new ArrayList<>();
        for (ContratoAlquiler c : contratos) {
            if (c.getCliente().getCedula().equals(cedula)) resultado.add(c);
        }
        return resultado;
    }

    public static ArrayList<ContratoAlquiler> buscarPorPlacaVehiculo(String placa) {
        ArrayList<ContratoAlquiler> resultado = new ArrayList<>();
        for (ContratoAlquiler c : contratos) {
            if (c.getVehiculo().getPlaca().equals(placa)) resultado.add(c);
        }
        return resultado;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getMonto() {
        return monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return 
                "=== CONTRATO DE ALQUILER ===\n" +
                "Número: " + numeroContrato +
                "\nCliente: " + cliente.getNombre() + " (" + cliente.getCedula() + ")" +
                "\nVehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo() + " (" + vehiculo.getPlaca() + ")" +
                "\nFecha Inicio: " + fechaInicio +
                "\nFecha Fin: " + fechaFin +
                "\nMonto: $" + monto +
                "\nEstado: " + estado +
                "\n============================";
    }
}