/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Cliente;

import Persona.Persona;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import utils.UtilDate;
import Gestion_Reservas.ReservaMetodos;
import Gestion_Reservas.Reserva;

public class Cliente extends Persona {
    
    private String licenciaConducir;
    private static ArrayList<Cliente> clientes = new ArrayList<>();
   
 public Cliente(String cedula, String nombre, LocalDate fechaNacimiento, String telefono, String correo, String licenciaConducir) {
        super(cedula, nombre, fechaNacimiento, telefono, correo);
        this.licenciaConducir = licenciaConducir;
    }
 
 public static ArrayList<Cliente> getClientes() {
      return new ArrayList<>(clientes);
    }
  
 public String getLicenciaConducir() {
        return licenciaConducir;
    }
    
 public void setLicenciaConducir(String licenciaConducir) {
        this.licenciaConducir = licenciaConducir;
    }
    

 public int calcularEdad() {
        return Period.between(this.getFechaNacimiento(), LocalDate.now()).getYears();
    }
    
public String mostrarInformacionCompleta() {
     return "=== INFORMACIÓN DEL CLIENTE ===\n" +
             "Cédula: " + getCedula() + 
             "\nNombre: " + getNombre() +
             "\nEdad: " + calcularEdad() + " años" +
              "\nFecha de Nacimiento: " + getFechaNacimiento() +
              "\nTeléfono: " + getTelefono() +
               "\nCorreo: " + getCorreo() +
              "\nLicencia de Conducir: " + licenciaConducir +
               "\n==============================";
    }
    
 public boolean agregarCliente(String cedula, String nombre, String fechaNacimientoStr, String telefono, String correo, String licenciaConducir) {
      for(Cliente cli : clientes) {
            if(cli.getCedula().equals(cedula)) {
                return false;
            }
        }
    
    if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$")) {
           return false;
        }
    
       if (!telefono.matches("^\\d{8}$")) {
           return false;
        }
    
      if (licenciaConducir == null || licenciaConducir.trim().isEmpty()) {
            return false;
        }try {
           LocalDate fechaNacimiento = UtilDate.toLocalDate(fechaNacimientoStr);
        
       if (!UtilDate.isLegalAge(fechaNacimiento)) {
            return false;
            }
      
          if (!UtilDate.isNotFutureDate(fechaNacimiento)) {
              return false;
            }
        
Cliente cliente = new Cliente(cedula, nombre, fechaNacimiento, telefono, correo, licenciaConducir);
       clientes.add(cliente);
           return true;
         } catch (Exception e) {
            return false; 
        }
    }
    
public boolean actualizarCliente(String cedula, String nuevoTelefono, String nuevoCorreo, String nuevaLicencia) {
        if (!nuevoTelefono.matches("\\d{8}")) {
            return false;
        }
    
       if (!nuevoCorreo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return false;
        }
    
      if (nuevaLicencia == null || nuevaLicencia.trim().isEmpty()) {
            return false;
        }
  
       for (Cliente cli : clientes) {
           if (cli.getCedula().equals(cedula)) {
              cli.setTelefono(nuevoTelefono);
               cli.setCorreo(nuevoCorreo);
              cli.setLicenciaConducir(nuevaLicencia);
                return true;
            }
        }
        return false; 
    }
    
public static Cliente buscarClientePorCedula(String cedula) {
        for (Cliente cli : clientes) {
            if (cli.getCedula().equals(cedula)) {
                return cli;
            }
        }
        return null;
    }
    
public static String buscarYMostrarCliente(String cedula) {
     Cliente cliente = buscarClientePorCedula(cedula);
       if (cliente != null) {
          return cliente.mostrarInformacionCompleta();
        } else {
            return "Cliente con cédula " + cedula + " no encontrado.";
        }
    }

public static boolean eliminarCliente(String cedula) throws Exception {
     Cliente cliente = buscarClientePorCedula(cedula);
       if (cliente == null) {
            return false;
        }
 List<Reserva> reservasCliente = ReservaMetodos.buscarPorCliente(cedula);
        for (Reserva reserva : reservasCliente) {
            if (!reserva.getEstado().equals("Cancelada") && 
                !reserva.getFin().isBefore(LocalDate.now())) {
                throw new Exception("No se puede eliminar el cliente porque tiene reservas activas o pendientes");
            }
        }
        return clientes.removeIf(cli -> cli.getCedula().equals(cedula));
    }

    @Override
    public String toString() {
        return "Cliente{" + "licenciaConducir=" + licenciaConducir + '}';
    }
}