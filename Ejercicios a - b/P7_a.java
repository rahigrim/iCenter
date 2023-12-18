/* Ejercicio a - Práctica_07 "Introducción a Java" */

import java.util.Scanner;

public class P7_a{

    public static boolean isElementOf(Integer[]xArray, int numZ){

        boolean esParteDe = false; 
        for(int i = 0; i < xArray.length; i++){
            if(xArray[i] == numZ){ // Excepción Null: El arreglo contiene un valor null en la posición i.
                esParteDe = true;
            }
            i++;
        }
        return esParteDe;
    } 

    public static void main(String[] args) {
      
        Scanner get = new Scanner(System.in);

        /* El enunciado pide "Dimensión" pero parece referirse a la longitud de la dimensión por
        que se asume que el arreglo X es unidimensional. 
        */

        try {
            System.out.println("Introduzca dimensión (longitud) del arreglo: ");
            String longitud_array = get.nextLine(); // Uso intencional del método nextLine() en vez de nextInt()
            int longitud = Integer.parseInt(longitud_array);
            
            Integer valor;
            Integer[] X; // Declaración del arreglo X como clase Integer y no como primitivo int. 
            if(longitud <=0){
                X = null; // Arreglo X como null. 
            } else{
                X = new Integer[longitud]; // Asignación dinámica de la longitud del arreglo. 
            }

            for(int i = 0; i < X.length; i++){ // Excepción al intentar recorrer un Array Null.
                System.out.println("Ingrese el elemento " + i + " del arreglo X:");
                String x = get.nextLine();
                if ("null".equals(x)) {
                    valor = null;
                    X[i] = valor; // Array clase Integer permite almacenar valores null.
                }
                else{
                    X[i] = Integer.parseInt(x);
                } 
            }

            System.out.println("Introduzca un número entero: ");
            Integer z = get.nextInt();
            boolean esParteDe = isElementOf(X, z);
            if(esParteDe){
                System.out.println("¡Pertenece! El entero " + z +" es un elemento del arreglo X.");
            } else {
                System.out.println("¡NO pertecenece! El entero " + z +" NO es un elemento del arreglo X.");
            } 

        }catch (NullPointerException e){
            System.out.println("Ha ocurrido una Excepción: NullPointerException. \nEl arreglo X contiene un valor de tipo null o X tiene una longitud menor o igual a 0.");
            // e.printStackTrace(); Para imprimir la descripción técnica de la excepción ocurrida. 
        }
    }
}