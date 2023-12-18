package iCenter.Controller;
import iCenter.Model.Responsable;
import iCenter.View.ICenterView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import javax.swing.JOptionPane;

public class dataRegisterController {


    public void startIcenter() {
        ICenterView view = new ICenterView();
        view.displayIcenterView(true);
    }

    public static String getStock(){ // Obtener Inventario 
        File inventario = new File("inventario.txt");
        try {
            if (inventario.createNewFile()) {
                return inventario.getName();
            } else {
                return inventario.getName();
            }
        } catch (IOException e) {
            return null;
        }
    }
    
    public static void createRegistro(String cedula, int equipos, BigDecimal cant_bs, String descripcion, String fecha, String numFactura){
        String inventario = getStock();

        Responsable nuevoResponsable = new Responsable(cedula, equipos, cant_bs, descripcion, fecha, numFactura);
        String cadenaRegistro = nuevoResponsable.buildFormato();

         try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter(inventario, true));
            escritor.write(cadenaRegistro);
            escritor.newLine();
            escritor.close();
            JOptionPane.showMessageDialog(null, "Registro exitoso.");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error. \n");
            e.printStackTrace();
        } 
    } 
}
