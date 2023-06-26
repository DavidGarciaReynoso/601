package propagaexcepcion;

public class PropagaExcepcion {

    public static void main(String[] args) {
        
        try {
            int division = miMetodo(10, 0);
            System.out.println(division);
        } catch (ArithmeticException e) {
            System.out.println("Excepcion aritmetica arrojada");
        }
    }
    public static int miMetodo(int a, int b) throws ArithmeticException{
        int c = a / b;
        return c;
    }
    
}
