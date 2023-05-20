package pkg3.v2trifiv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *
 * @author Todos
 */
public class V2TRIFIV {

    public static void main(String[] args) throws IOException {
    
    //Variables
        BufferedReader bufEntrada= new BufferedReader(new InputStreamReader(System.in));
        int num, mult3, mult5;
    
    //Entrada de Datos
        System.out.println("Dame el numero");
        num= Integer.parseInt(bufEntrada.readLine());
    
    //Proceso de Datos
        mult3=num%3;
        mult5=num%5;
    
    //Salida de Datos
    if(mult3==0 && mult5==0){
        System.out.println("TRIFV");
    }
    else if(mult5==0){
        System.out.println("FIV");
    }
    
    else if(mult3==0){
        System.out.println("TRI");
    }
    
    else{System.out.println("No es valido");
        }
    }
}