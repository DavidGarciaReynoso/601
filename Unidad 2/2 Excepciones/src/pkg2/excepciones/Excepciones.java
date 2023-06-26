package pkg2.excepciones;

import javax.swing.JOptionPane;

public class Excepciones {
    public static void main(String[] args) {
        int x,y;
        
        try {
            x= Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese númer 1:","Solicito datos",3));
            y= Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese númer 2:","Solicito datos",3));
            JOptionPane.showInputDialog(null,"La División de "+ x +"/"+ y +"es "+ (x/y),"Mostrando resultado",1);
        } catch (NumberFormatException n) {
            System.out.println(n.getMessage() + "No es número ENTERO");
        }catch (ArithmeticException a){
            System.out.println(a.getMessage() + "No se puede dividir entre cero");
            
        }
    }   
    
    
}
