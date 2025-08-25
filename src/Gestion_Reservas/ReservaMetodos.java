/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Reservas;

import Gestion_Cliente.Cliente;
import Gestion_Vehiculos.Vehiculo;
import Gestion_Vehiculos.VehiculosHashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;

public class ReservaMetodos {
    private static final List<Reserva> reservas = new ArrayList<>();
    private static final Queue<Reserva> colaEspera = new ArrayDeque<>();
    
public static boolean crear(String cedula, String tipo, LocalDate inicio, LocalDate fin) {
   if (Cliente.buscarClientePorCedula(cedula) == null || !validarFechas(inicio, fin)) 
          return false;
        
 Vehiculo vehiculo = buscarVehiculoDisponible(tipo, inicio, fin);
      if (vehiculo == null) {
           Reserva r = new Reserva(generarId(), cedula, null, inicio, fin);
           r.setEstado("Espera");
          colaEspera.add(r);
           reservas.add(r);
          return false;
        }
        
 reservas.add(new Reserva(generarId(), cedula, vehiculo.getPlaca(), inicio, fin));
        return true;
    }

public static boolean modificar(String id, String nuevaPlaca) {
    Reserva r = buscarPorId(id);
       if (r == null || !r.getEstado().equals("Pendiente")) return false;
        
      try { VehiculosHashMap.buscarVehiculo(nuevaPlaca); } 
        catch (NoSuchElementException e) {
            return false;
        }
        
      if (!disponible(nuevaPlaca, r.getInicio(), r.getFin(), id))
          return false;
        
   r.setVehiculoPlaca(nuevaPlaca);
        return true;
    }
    
public static boolean cancelar(String id) {
    Reserva r = buscarPorId(id);
       if (r == null || r.getInicio().isBefore(LocalDate.now())) return false;
       r.setEstado("Cancelada");
       return true;
    }
    
public static List<Reserva> buscarPorCliente(String cedula) {
    List<Reserva> resultado = new ArrayList<>();
       for (Reserva r : reservas) 
           if (r.getClienteCedula().equals(cedula)) resultado.add(r);
        return resultado;
    }
    
public static List<Reserva> buscarPorFechas(LocalDate inicio, LocalDate fin) {
    List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) 
            if (!r.getInicio().isBefore(inicio) && !r.getFin().isAfter(fin)) resultado.add(r);
        return resultado;
    }
    
public static boolean confirmar(String id) {
     Reserva r = buscarPorId(id);
      if (r == null || !r.getEstado().equals("Pendiente")) return false;
       if (!disponible(r.getVehiculoPlaca(), r.getInicio(), r.getFin(), id)) return false;
      r.setEstado("Confirmada");
        return true;
    }
    
private static Reserva buscarPorId(String id) {
      for (Reserva r : reservas) 
           if (r.getId().equals(id)) return r;
       return null;
    }
    
private static boolean validarFechas(LocalDate inicio, LocalDate fin) {
       return !inicio.isBefore(LocalDate.now()) && fin.isAfter(inicio) && inicio.until(fin).getDays() <= 30;
    }
    
private static Vehiculo buscarVehiculoDisponible(String tipo, LocalDate inicio, LocalDate fin) {
     for (Vehiculo v : VehiculosHashMap.listarVehiculos()) 
          if (v.getTipo().equals(tipo) && v.getEstado().equals("Disponible") && disponible(v.getPlaca(), inicio, fin, null)) 
                return v;
        return null;
    }
    
private static boolean disponible(String placa, LocalDate inicio, LocalDate fin, String excluirId) {
      for (Reserva r : reservas) {
           if (r.getId().equals(excluirId) || r.getEstado().equals("Cancelada")) continue;
           if (r.getVehiculoPlaca() != null && r.getVehiculoPlaca().equals(placa) && 
              !inicio.isAfter(r.getFin()) && !fin.isBefore(r.getInicio())) 
                return false;
        }
        return true;
    }
    
private static String generarId() {
        return "R" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
    
 public static List<Reserva> getReservas() { return new ArrayList<>(reservas); }
 public static Queue<Reserva> getColaEspera() { return new ArrayDeque<>(colaEspera); }
}