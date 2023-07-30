// Clase "Conectar" para la conexión a la base de datos

package conectar;
/**
 *
 * @author David Garcia Reynoso
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class conectar {
    private Connection cn; // Objeto de tipo Connection para almacenar la conexión

    // Método para establecer la conexión a la base de datos y retornarla
    public Connection conexion() {
        try {
            // Cargar el controlador del driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            
            // Establecer la conexión con la base de datos
            cn = DriverManager.getConnection("jdbc:mysql://localhost/bd_chat", "root", "");
            System.out.println("Conectado a la base de datos");
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }

        return cn; // Retornar la conexión establecida
    }
}