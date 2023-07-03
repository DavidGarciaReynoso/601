package pkg7.classcastexeption;

public class ClassCastExeption {
    
    public static void main(String[] args) {
      
        try {
            
            Object obj = new Integer(100);
            
            String str = (String) obj; 
            
        } catch (ClassCastException e) {
            
            System.out.println("Se produjo una excepción ClassCastException: " + e.getMessage());
            
            e.printStackTrace();
        }
    }
}