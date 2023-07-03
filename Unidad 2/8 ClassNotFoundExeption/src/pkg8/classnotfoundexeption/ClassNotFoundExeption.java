package pkg8.classnotfoundexeption;

public class ClassNotFoundExeption {

    public static void main(String[] args) {
       
          try {
            
            Class.forName("David Garcia Reynoso");
            
        } catch (ClassNotFoundException e) {
            
            System.out.println("Se produjo una excepción ClassNotFoundException: " + e.getMessage());
            
            e.printStackTrace();
        }
    }
}