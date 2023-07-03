package pkg6.arrayindexoutofboundexepcion;

public class ArrayIndexOutOfBoundExepcion {
    
        public static void main(String[] args) {

          try {
                int[] numbers = {1, 2, 3};
                int index = 4; // Índice fuera del rango válido
                int value = numbers[index]; // Línea que lanza la excepción ArrayIndexOutOfBoundsException
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Se produjo una excepción ArrayIndexOutOfBoundsException: " + e.getMessage());
                e.printStackTrace();
        }
    }
}




    
    

