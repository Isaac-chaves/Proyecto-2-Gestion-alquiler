/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Vehiculos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
/**
 *
 * @author isaac
 */
public class VehiculosHashMap {
    
    private static final HashMap<String, Vehiculo> vehiculos = new HashMap<>();
    
    private static final Set<String> TIPOS_VALIDOS = Set.of("Sedán", "SUV", "Pick-up");
    
    private static final Set<String> ESTADOS_VALIDOS = Set.of("Disponible", "En alquiler", "En mantenimiento");
    

    public static void agregarVehiculo(String placa, String marca, String modelo, int año, String tipo, String estado)
            throws IllegalArgumentException {
        if (vehiculos.containsKey(placa)) {
            throw new IllegalArgumentException("Ya existe un vehículo con esa placa.");
        }

        int añoActual = Calendar.getInstance().get(Calendar.YEAR);
        if (año > añoActual || año < (añoActual - 20)) {
            throw new IllegalArgumentException("Año no valido. Debe estar entre " + (añoActual - 20) + " y " + añoActual);
        }

        if (!TIPOS_VALIDOS.contains(tipo)) {
            throw new IllegalArgumentException("Tipo no valido. Tipos permitidos: " + TIPOS_VALIDOS);
        }

        if (!ESTADOS_VALIDOS.contains(estado)) {
            throw new IllegalArgumentException("Estado no valido. Estados permitidos: " + ESTADOS_VALIDOS);
        }

        Vehiculo v = new Vehiculo(placa, marca, modelo, año, tipo, estado);
        vehiculos.put(placa, v);
    }

    public static void actualizarVehiculo(String placa, String nuevoModelo, String nuevoTipo, String nuevoEstado)
            throws IllegalArgumentException, NoSuchElementException {
        Vehiculo v = vehiculos.get(placa);
        if (v == null) {
            throw new NoSuchElementException("Vehiculo no encontrado.");
        }
        if (!TIPOS_VALIDOS.contains(nuevoTipo)) {
        }

        if (!ESTADOS_VALIDOS.contains(nuevoEstado)) {
        }

        v.setModelo(nuevoModelo);
        v.setTipo(nuevoTipo);
        v.setEstado(nuevoEstado);
    }

    public static void eliminarVehiculo(String placa)
            throws NoSuchElementException, IllegalStateException {
        Vehiculo v = vehiculos.get(placa);
        if (v == null) {
            throw new NoSuchElementException("Vehiculo no encontrado.");
        }

        if ("En alquiler".equals(v.getEstado())) {
            throw new IllegalStateException("No se puede eliminar un vehiculo que está en alquiler.");
        }
        vehiculos.remove(placa);
    }

    public static Vehiculo buscarVehiculo(String placa)
            throws NoSuchElementException {
        Vehiculo v = vehiculos.get(placa);
        if (v == null) {
            throw new NoSuchElementException("Vehículo no encontrado.");
        }
        return v;
    }

    public static Set<String> getTiposValidos() {
        return TIPOS_VALIDOS;
    }

    public static Set<String> getEstadosValidos() {
        return ESTADOS_VALIDOS;
    }

    public static List<Vehiculo> listarVehiculos() {
        return new ArrayList<>(vehiculos.values());
    }
}