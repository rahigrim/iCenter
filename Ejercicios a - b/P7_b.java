import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;


public class P7_b {

    public static int total_equipos= 0;
    public static double total_bs= 0.0;
    public static String cedula;

    public static BufferedReader crearLector(String file) {
        try {
          return new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error!");
            e.printStackTrace();
            return null;
        }
      }

      public static String obtenerNombreArchivo() {
        File inventario = new File("inventario.txt");
        return inventario.getName();
      }

    public static void crearInventario(){
        File inventario = new File(obtenerNombreArchivo());

        try {
            
            if (inventario.createNewFile()) {
                System.out.println("Se ha creado exitosamente un inventario: " + inventario.getName());
            } else {
                System.out.println("El Centro tiene un inventario existente: " + inventario.getName() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Lo sentimos; ha ocurrido un error creando o accediendo al archivo.");
        }
    }

    public static void time(){

        try{
            Thread.sleep(4000); // 4 segundos
        } catch (InterruptedException e){
            System.out.println("Ha ocurrido un error en el control del tiempo.");
        }
    }
                
        public static ArrayList<String> lector(String lector){

            BufferedReader reader = crearLector(obtenerNombreArchivo());
            
            try {
                String linea;
                ArrayList<String> registros_encontrados = new ArrayList<String>();
                if(lector == "individual"){
                    Scanner get = new Scanner(System.in);
                    System.out.println("Introduzca número de C.I. del responsable: ");
                    cedula = get.nextLine();
                    while ((linea = reader.readLine()) != null) {
                    if (linea.contains(cedula)) {
                        registros_encontrados.add(linea);
                    }
                }
                reader.close();
                return registros_encontrados;
                } else if (lector == "general"){
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

    public static void registrarData(){
        // Método para capturar dato por cónsola y registrarla en archivo .txt

        Scanner get = new Scanner(System.in);
        System.out.println("Ingrese datos a registrar: ");
        String cadenaDatos = get.nextLine();

        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter(obtenerNombreArchivo(), true));
            escritor.write(cadenaDatos);
            escritor.newLine();
            escritor.close();
            System.out.println("Registro exitoso.\n");

          } catch (IOException e) {
            System.out.println("Ha ocurrido un error. \n");
            e.printStackTrace();
          } 
    }

    public static void generarReporteIndividual(){
        // Método para generar un reporte individual
       
             ArrayList<String> registros = lector("individual");
             if(registros.size() != 0){
                System.out.println("\nSe han hallado registros para cédula " + cedula + "\n");
                for (String registro : registros) {
                    String[] partes = registro.split("#");
                    total_equipos += Integer.parseInt(partes[1]);
                    total_bs += Double.parseDouble(partes[2].replace(",", "."));
                }
                System.out.println("Totalización:");
                System.out.println(total_equipos + " equipos. ");
                System.out.println(total_bs + " Bs.\n" );

                total_equipos= 0;
                total_bs= 0.0;
                cedula="";
            } else {
                System.out.println("No se hallaron registros para la cédula: " + registros.get(5) + "\n");
            }
        } 
    
    public static void generarReporteGeneral(){
        
        ArrayList<String> registros = lector("general");
        Map<String, ArrayList<String>> mapResponsables = new HashMap<>();
        
        for (String registro : registros) {
                // Desglosar cadena de datos segun formato de origen. 
                String[] datos_partes = registro.split("#");

                String cedula = datos_partes[5];
            
                // Crear un Arraylist solo con los valores de interés para el reporte
                ArrayList<String> datos_lista = new ArrayList<>(Arrays.asList(datos_partes[1], datos_partes[2]));

                // Variabilizar valores y convertirlos de String a data types respectivos según enunciado. 
                int cant_equipos = Integer.parseInt(datos_lista.get(0));
                double cant_bs = Double.parseDouble(datos_lista.get(1).replace(",", "."));

                // Verificar existencia del Responsable en el HashMap y actualiza los registros de equipos y Bs. del Responsable.
                if(mapResponsables.containsKey(cedula)){

                    int cant_equipos_actual = Integer.parseInt(mapResponsables.get(cedula).get(0));
                    Double cant_bs_actual = Double.parseDouble(mapResponsables.get(cedula).get(1).replace(",", "."));
                    mapResponsables.get(cedula).set(0, Integer.toString(cant_equipos + cant_equipos_actual ));
                    mapResponsables.get(cedula).set(1, Double.toString(cant_bs + cant_bs_actual));
                } else {
                    // Incluye al nuevo Responsable en el HashMap. 
                    mapResponsables.put(cedula, datos_lista);
                }
            }
            System.out.printf("%-10s %-20s %-15s%n", "C.I. Responsable", "Cantidad Equipos", "Monto Total");

            for (String i : mapResponsables.keySet()) {
                // Actualización de valores para el responsable i
                total_equipos += Integer.parseInt(mapResponsables.get(i).get(0));
                total_bs += Double.parseDouble(mapResponsables.get(i).get(1).replace(",", "."));

                System.out.printf("%-20s %-20s %-15s%n", i , mapResponsables.get(i).get(0) , mapResponsables.get(i).get(1));
              }

            System.out.println("\n Totalización:");
            System.out.println(total_equipos + " equipos. ");
            System.out.println(total_bs + " Bs.\n" );

            // Reiniciar variables
            total_equipos= 0;
            total_bs= 0.0;
            cedula="";
        
            }

    public static void crearReporte() throws IOException{

        try{
            File file = new File("inventario.txt");
            Scanner myReader = new Scanner(file);
            System.out.println("El reporte se hará con el registro: " + file.getName());
            Scanner get = new Scanner(System.in);
            int opcionReporte;
            System.out.println(" (1) Reporte Individual" + "\n" + " (2) Reporte General" + "\n" + " (3) Volver a Menú Principal" + "\n");
            System.out.println("Indique su opción> 1, 2 o 3: ");
            opcionReporte = get.nextInt();
            
            switch(opcionReporte){
                case 1:
                generarReporteIndividual();
                break;

                case 2:
                generarReporteGeneral();
                break;

                case 3:
                mostrarMenu();
                break;
        }
    }catch (FileNotFoundException e) {
      System.out.println("No se ha detectado un archivo de inventario para realizar reporte. \n Debe REGISTRAR DATA.");
    }
} 

    public static void mostrarMenu() throws IOException{

        Scanner get = new Scanner(System.in);
        String menu = "Menú Principal" + "\n"+"(1) REGISTRAR DATA  " + "\n" + "(2) GENERAR REPORTE" + "\n" + "(3) SALIR" + "\n"; 
        int opcion;
        System.out.println(menu);
        System.out.println("Por favor, indique... ¿Qué desea hacer? " + "\n" + "Ingrese 1, 2 o 3: " );
        
        try{
            opcion = get.nextInt();
            switch(opcion){
                case 1:
                System.out.println("\nUsted ha elegido REGISTRAR DATA");
                crearInventario();
                registrarData();
                time();
                mostrarMenu();
                break;

                case 2:
                System.out.println("\nUsted ha elegido GENERAR REPORTE");
                crearReporte();
                time();
                mostrarMenu();
                break;

                case 3:
                System.out.println("\nUsted ha elegido SALIR DEL SISTEMA. ");
                System.out.println("Saliendo en 4s ...");
                time();
                break;
        }
            if (opcion < 1 || opcion > 3) {
                System.out.println("La opción debe ser 1, 2, o 3. Vuelva a iniciar. \n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Opción inválida. Por favor, ingrese un valor numérico. \n" );
            time();
            mostrarMenu();
         }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("\nREGISTRO Y CONTROL DE EQUIPOS CENTRO DE INVESTIGACIÓN \n");
        mostrarMenu();
    }
}