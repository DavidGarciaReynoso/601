package pkg5.v4trifiv;

import funciones.funcionMultiplo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *
 * @author Todos
 */
public class V4TRIFIV {
    public static void main(String[] args) throws IOException {

        //Creo objeto
            funcionMultiplo mult = new funcionMultiplo();

        //Variables
            BufferedReader bufEntrada= new BufferedReader(new InputStreamReader(System.in));
            int num;
        
        //Entrada de Datos
            System.out.println("Dame el numero");
            num= Integer.parseInt(bufEntrada.readLine());
    
        //Salida de Datos
            System.out.println(mult.retornaMultiplo(num));
    }
}