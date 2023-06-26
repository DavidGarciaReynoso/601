package excepciones1;

import javax.swing.JOptionPane;

public class Excepciones1 {
    public static void main(String[] args) {
        int x,y;
        
        try{
        x= Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese un numero 1:","Solicite datos",3));
        }catch (NumberFormatException n){
            System.out.println(n.getMessage() + " No es un numero ENTERO");
        }

    }
    
    
}
