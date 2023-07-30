package sockets;
/**
 *
 * @author David Garcia Reynoso
 */
import javax.swing.JFrame;

// Clase principal que inicia el servidor y los clientes en hilos separados
public class sockets {

    public static void main(String[] args) {
        // Iniciar el servidor en un hilo separado
        Thread servidorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Deposito deposito = new Deposito();
                deposito.iniciarUI();
            }
        });

        // Iniciar el cliente 1 en un hilo separado
        Thread cliente1Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InstanciaUsuario cliente1 = new InstanciaUsuario();
                cliente1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        // Iniciar el cliente 2 en un hilo separado
        Thread cliente2Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InstanciaUsuario cliente2 = new InstanciaUsuario();
                cliente2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        // Iniciar el servidor
        servidorThread.start();

        // Esperar un poco para asegurarnos de que el servidor esté en ejecución antes de iniciar los clientes
        try {
            Thread.sleep(1000); // Esperar 1 segundo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Iniciar los clientes
        cliente1Thread.start();
        cliente2Thread.start();
    }
}