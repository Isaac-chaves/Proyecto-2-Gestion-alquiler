/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Reservas;


import java.time.LocalDate;

public class Reserva {
   private  String id;
    private String clienteCedula;
    private String vehiculoPlaca;
    private  LocalDate inicio;
    private  LocalDate fin;
    private String estado;
     

        public String getId() {
            return id;
        }

        public String getClienteCedula() {
            return clienteCedula;
        }

        public String getVehiculoPlaca() {
            return vehiculoPlaca;
        }

        public LocalDate getInicio() {
            return inicio;
        }

        public LocalDate getFin() {
            return fin;
        }

        public String getEstado() {
            return estado;
        }

        public void setVehiculoPlaca(String vehiculoPlaca) {
            this.vehiculoPlaca = vehiculoPlaca;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
   public Reserva(String id, String clienteCedula, String vehiculoPlaca, LocalDate inicio, LocalDate fin) {
            this.id = id;
            this.clienteCedula = clienteCedula;
            this.vehiculoPlaca = vehiculoPlaca;
            this.inicio = inicio;
            this.fin = fin;
            this.estado = "Pendiente";
        }
}