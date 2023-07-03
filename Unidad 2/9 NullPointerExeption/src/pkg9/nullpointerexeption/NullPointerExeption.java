package pkg9.nullpointerexeption;

public class NullPointerExeption {

    public static void main(String[] args) {
       
      try {
            String str = null;
            
            int length = str.length(); // Línea que lanza la excepción NullPointerException
        
        } catch (NullPointerException e) {
        
            System.out.println("Se produjo una excepción NullPointerException: " + e.getMessage());
            
            e.printStackTrace();
        }
    }
}