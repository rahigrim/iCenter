package iCenter.Controller;
import iCenter.Model.Responsable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class generateReportController {


    public static int total_equipos= 0;
    // Se usa en reemplazo del Double para mayor precisión en el cálculo de montos.
    // Se comprende: el uso de Double pudo ser suficiente para esta práctica. 
    public static BigDecimal total_bs = new BigDecimal("0.0"); 
    public static String cedula;

    public static BufferedReader createReader(String file) {
        try {
          return new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error!");
            e.printStackTrace();
            return null;
        }
      }

    public static ArrayList<String> getRegisters(String cedula, String tipoReporte){

            BufferedReader reader = createReader("inventario.txt");
            try {
                String linea;
                ArrayList<String> registros_encontrados = new ArrayList<String>();
                if(tipoReporte == "individual"){
                    while ((linea = reader.readLine()) != null) {
                    if (linea.contains(cedula)) {
                        registros_encontrados.add(linea);
                    }
                }
                reader.close();
                return registros_encontrados;
                } else if (tipoReporte == "general"){
                    while ((linea = reader.readLine()) != null) {
                        registros_encontrados.add(linea);
                    }
                }
                reader.close();
                return registros_encontrados;

             } catch (IOException e) {
                System.out.println("Ha ocurrido un error!");
                e.printStackTrace();
                return null;
        }
    }

    public Object[] generateIndividualReport(String cedula){

        
        // Método para generar un reporte individual
       
             ArrayList<String> registros = getRegisters(cedula, "individual");
             if(registros.size() != 0 && cedula !=""){
                System.out.println("\nSe han hallado registros para cédula " + cedula + "\n");
                for (String registro : registros) {
                    String[] partes = registro.split("#");
                    total_equipos += Integer.parseInt(partes[1]);
                    // Manipulación de variables BigDecimal - variables immutables.
                    total_bs = total_bs.add(new BigDecimal(partes[2].replace(",", ".")));
                }
                Object[] totales_individuales = new Object[2];
                totales_individuales[0] = total_equipos;
                totales_individuales[1] = total_bs;
                cedula="";
                total_equipos = 0;
                total_bs = new BigDecimal("0.0"); 
                return totales_individuales;

            } else {
                System.out.println("No se hallaron registros para la cédula: " + cedula + "\n");
                return null;
            }
        } 

    public String[][] generateGeneralReport(){
        Map<String, Responsable> responsables = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("inventario.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("#"); 
                int cant_equipos = Integer.parseInt(data[1]);
                BigDecimal cant_bs = new BigDecimal(data[2]);
                String cedula = data[5];
                if (!responsables.containsKey(cedula)) {
                    Responsable responsable = new Responsable(cedula, cant_equipos, cant_bs);
                    responsables.put(cedula, responsable);
                } else {
                    responsables.get(cedula).addCantidadBs(cant_bs);
                    responsables.get(cedula).addCantidadEquipos(cant_equipos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] registros_computados = new String[responsables.size()][];
        int i = 0;
        for (Map.Entry<String, Responsable> entry : responsables.entrySet()) {
            Responsable responsable = entry.getValue();
            String[] responsableValues = {responsable.getCedula(), Integer.toString(responsable.getCantidadEquipos()), (responsable.getCantidadBs().toString())};
            registros_computados[i++] = responsableValues;
        }
        return registros_computados;
    }
}