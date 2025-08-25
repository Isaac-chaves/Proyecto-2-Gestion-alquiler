/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Empleado;

import Persona.Persona;
import java.util.ArrayList;
import java.time.LocalDate;
import utils.UtilDate;

public class Empleado extends Persona {
    private static final ArrayList<Empleado> empleados = new ArrayList<>();
    
    
    private String puesto;
    private double salario;

 public Empleado(String cedula, String nombre, LocalDate fechaNacimiento,String telefono, String correo, String puesto, double salario) {
     super(cedula, nombre, fechaNacimiento, telefono, correo);
        this.puesto = puesto;
        this.salario = salario;
    }

public static ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

 
    public String getPuesto() {
        return puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
public boolean agregarEmpleado(String cedula, String nombre, String fechaNacimientoStr,String telefono, String correo, String puesto, double salario) { 
        for(Empleado emp : empleados) {
             if(emp.getCedula().equals(cedula)) {
           return false;
           }
       }
       
       if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return false;
        }
      
      if (!telefono.matches("^\\d{8}$")) {
            return false;
        }try {
           
LocalDate fechaNacimiento = UtilDate.toLocalDate(fechaNacimientoStr);
         if (!UtilDate.isLegalAge(fechaNacimiento)) {
               return false;
           }
          if (!UtilDate.isNotFutureDate(fechaNacimiento)) {
                return false;
            }
            
  
Empleado empleado = new Empleado(cedula, nombre, fechaNacimiento, telefono, correo, puesto, salario);
      empleados.add(empleado);
          return true;
            
  } catch (Exception e) {
            return false; 
        }
    }
   
   
public boolean eliminarEmpleado(String cedula) {
    for (int i = 0; i < empleados.size(); i++) {
          if (empleados.get(i).getCedula().equals(cedula)) {
                empleados.remove(i);
         return true;
            }
        }
        return false;
    }
  
public Empleado buscarEmpleado(String cedula) {
     for (Empleado emp : empleados) {
           if (emp.getCedula().equals(cedula)) {
                 return emp;
            }
        }
        return null;
    }
   
public boolean actualizarEmpleado(String cedula, String nuevoTelefono, String nuevoCorreo, String nuevoPuesto) {
      
      if (!nuevoTelefono.matches("\\d{8}")) {
            return false;
        }
    if (!nuevoCorreo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return false;
        }
        for (Empleado emp : empleados) {
            if (emp.getCedula().equals(cedula)) {
                emp.setTelefono(nuevoTelefono);
                emp.setCorreo(nuevoCorreo);
                emp.setPuesto(nuevoPuesto);
                return true;
            }
        }
        return false; 
    }

    @Override
    public String toString() {
        return "Empleado{" + "puesto=" + puesto + ", salario=" + salario + '}';
    }
   

}


  
