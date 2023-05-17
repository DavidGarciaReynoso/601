/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg2.v1.trifiv;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author TODOS
 */
public class V1TRIFIV {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        BufferedReader bufEntrada = new BufferedReader(new InputStreamReader(System.in));
        
        int numero;
        float mult3;
        float mult5;
        
        System.out.println("Dame el numero");
        numero = Integer.parseInt(bufEntrada.readLine());
        
        mult3 = numero % 3;
        mult5 = numero % 5;
        
        if(mult3==0){
            System.out.println("TRI");
        }
        if(mult5==0){
            System.out.println("FIV");
        }
        if(mult3==0 && mult5==9){
            System.out.println("TRIFIV");
            
        }
        
    
    }
    
}
