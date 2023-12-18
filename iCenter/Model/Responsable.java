package iCenter.Model;
import java.math.BigDecimal;

public class Responsable {

   private int cantidadEquipos;
   private BigDecimal cantidadBs;
   private String cedula, descripcion, fecha, numFactura;


   // Aplicación del concepto --Overloading-- Dos constructores con mismo nombre pero diferentes argumentos.

   public Responsable(String cedula, int equipos, BigDecimal cant_bs) {
      this.cedula = cedula;
      this.cantidadEquipos = equipos;
      this.cantidadBs = cant_bs;
   }

   public Responsable(String cedula, int equipos, BigDecimal cant_bs, String descripcion, String fecha, String numFactura ) {
      this.cedula = cedula;
      this.cantidadEquipos = equipos;
      this.cantidadBs = cant_bs;
      this.fecha = fecha;
      this.descripcion = descripcion;
      this.numFactura = numFactura;
   }
    
   public String getCedula() {
      return this.cedula;
   }

   public int getCantidadEquipos() {
      return this.cantidadEquipos;
   }

   public BigDecimal getCantidadBs() {
      return this.cantidadBs;
   }

   public void addCantidadEquipos(int cant_equipos) {
      this.cantidadEquipos += cant_equipos;
   }

   public void addCantidadBs(BigDecimal cant_bs) {
      this.cantidadBs = this.cantidadBs.add(cant_bs);
   }

   public String buildFormato(){
         StringBuilder formato = new StringBuilder(descripcion +"#");
         formato.append(cantidadEquipos).append("#").append(cantidadBs).append("#").append(fecha).append("#").append(numFactura).append("#").append(cedula);
         String registro_cadena = formato.toString();
         return registro_cadena;
      }
}