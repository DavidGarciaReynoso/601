package pkg11.filenotfoundexeption;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileNotFoundExeption {

    public static void main(String[] args) {
        
    try {
            // Intentar leer un archivo inexistente
            File file = new File("archivo.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Se produjo una excepción FileNotFoundException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}