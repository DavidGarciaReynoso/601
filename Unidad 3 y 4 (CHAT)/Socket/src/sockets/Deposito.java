package sockets;
/**
 * Servidor del chat que gestiona la comunicación entre los clientes.
 * Escucha conexiones de clientes, almacena mensajes en la base de datos y envía mensajes a todos los clientes conectados.
 * @author David Garcia Reynoso
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Deposito extends JFrame implements Runnable {

        // Lista de sockets de clientes conectados
        private List<Socket> listaClientes = new ArrayList<>();

        // Área de texto para mostrar mensajes y eventos del servidor
        private JTextArea areatexto;

        // Mapa para asociar sockets de clientes con sus nombres
        private Map<Socket, String> clientesMap = new HashMap<>();

        // Conexión a la base de datos
        private Connection cn;

    public static void main(String[] args) {
        // Crear instancia del servidor y mostrar la interfaz de usuario
        Deposito deposito = new Deposito();
        deposito.iniciarUI();
    }

    public void iniciarUI() {
        setBounds(1200, 300, 280, 350); // Establecer posición y tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel lamina = new JPanel();
        lamina.setLayout(new BorderLayout());

        areatexto = new JTextArea(); // Área de texto para mostrar mensajes y eventos del servidor
        lamina.add(new JScrollPane(areatexto), BorderLayout.CENTER);

        // Crear una nueva lámina para los componentes de búsqueda por fecha
        JPanel laminaBusqueda = new JPanel();
        JButton botonBuscarFecha = new JButton("Buscar por Fecha");
        JTextField campoFecha = new JTextField(10);
        laminaBusqueda.add(campoFecha);
        laminaBusqueda.add(botonBuscarFecha);
        lamina.add(laminaBusqueda, BorderLayout.SOUTH); // Agregar la lámina de búsqueda debajo del área de texto

        add(lamina); // Agregar la lámina principal a la ventana del servidor

        setVisible(true); // Hacer visible la ventana del servidor

        conectarBD(); // Conectar a la base de datos

        Thread hilo = new Thread(this);
        hilo.start(); // Iniciar el hilo para escuchar conexiones de clientes

        // Recuperar mensajes de la base de datos y mostrarlos en el área de texto
        try {
            PreparedStatement ps = cn.prepareStatement("SELECT cliente, mensaje FROM chat");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String cliente = rs.getString("cliente");
                String mensaje = rs.getString("mensaje");
                areatexto.append("\n" + cliente + ": " + mensaje);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar mensajes de la base de datos: " + ex.getMessage());
        }

        // Agregar el ActionListener al botón de búsqueda
        botonBuscarFecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fechaBusqueda = campoFecha.getText();
                buscarMensajesPorFecha(fechaBusqueda); // Método para buscar mensajes por fecha
            }
        });
    }

    private void buscarMensajesPorFecha(String fecha) {
        try {
            // Preparar la consulta SQL para buscar mensajes por fecha
            String consulta = "SELECT cliente, mensaje FROM chat WHERE fecha = ?";
            PreparedStatement ps = cn.prepareStatement(consulta);
            ps.setString(1, fecha);

            // Ejecutar la consulta
            ResultSet rs = ps.executeQuery();

            // Mostrar los mensajes encontrados en el área de texto del servidor
            areatexto.setText("Mensajes encontrados para la fecha " + fecha + ":\n");
            while (rs.next()) {
                String cliente = rs.getString("cliente");
                String mensaje = rs.getString("mensaje");
                areatexto.append(cliente + ": " + mensaje + "\n");
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar mensajes por fecha: " + ex.getMessage());
        }
    }

    private void conectarBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el controlador de MySQL
            cn = DriverManager.getConnection("jdbc:mysql://localhost/bd_chat", "root", ""); // Establecer la conexión con la base de datos
            System.out.println("Conectado a la base de datos");
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        iniciarServidor(); // Iniciar el servidor para escuchar conexiones de clientes
    }

    private synchronized void enviarMensajeATodos(String mensaje, String remitente) {
        // Enviar un mensaje a todos los clientes conectados
        try {
            for (Socket clienteSocket : listaClientes) {
                DataOutputStream flujo_salida = new DataOutputStream(clienteSocket.getOutputStream());
                flujo_salida.writeUTF(remitente + ": " + mensaje);
                flujo_salida.flush();
            }
        } catch (IOException ex) {
            System.out.println("Error al enviar el mensaje a todos los clientes: " + ex.getMessage());
        }
    }

    private void eliminarCliente(Socket clienteSocket) {
        // Eliminar a un cliente de la lista cuando se desconecta y cerrar su conexión
        listaClientes.remove(clienteSocket);
        clientesMap.remove(clienteSocket);
        try {
            clienteSocket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión del cliente: " + e.getMessage());
        }
    }

    private void iniciarServidor() {
        // Iniciar el servidor y escuchar conexiones de clientes
        try {
            ServerSocket deposito = new ServerSocket(9999); // Crear el socket del servidor en el puerto 9999

            while (true) {
                Socket misocket = deposito.accept(); // Aceptar la conexión de un nuevo cliente
                listaClientes.add(misocket); // Agregar el socket del nuevo cliente a la lista

                // Crear un hilo separado para manejar las solicitudes del cliente
                Thread clienteThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        recibirMensajes(misocket); // Método para recibir mensajes del cliente
                    }
                });
                clienteThread.start(); // Iniciar el hilo del cliente
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void recibirMensajes(Socket clienteSocket) {
        // Método para recibir mensajes del cliente y procesarlos
        try {
            DataInputStream flujo_entrada = new DataInputStream(clienteSocket.getInputStream());

            // Leer el nombre del cliente enviado por el cliente
            String nombreCliente = flujo_entrada.readUTF();
            clientesMap.put(clienteSocket, nombreCliente); // Guardar el nombre del cliente en el mapa

            while (true) {
                String mensajeCompleto = flujo_entrada.readUTF(); // Leer el mensaje enviado por el cliente
                if (mensajeCompleto != null) {
                    // Obtener el nombre del cliente del mapa
                    String remitente = clientesMap.get(clienteSocket);

                    // Dividir el mensaje en partes (cliente, servidor, mensaje) usando ":" como separador
                    String[] partesMensaje = mensajeCompleto.split(":");
                    String cliente = partesMensaje[0];
                    String servidor = partesMensaje[1];
                    String mensaje = partesMensaje[2];

                    // Insertar los datos en la tabla de la base de datos con la fecha actual
                    PreparedStatement ps = cn.prepareStatement(
                            "INSERT INTO chat (cliente, servidor, mensaje, fecha) VALUES (?, ?, ?, NOW())");
                    ps.setString(1, cliente);
                    ps.setString(2, servidor);
                    ps.setString(3, mensaje);
                    ps.executeUpdate();

                    areatexto.append("\n" + cliente + ": " + mensaje); // Mostrar el mensaje en el área de texto del servidor

                    enviarMensajeATodos(mensaje, remitente); // Enviar mensaje a todos los clientes conectados
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al recibir el mensaje: " + ex.getMessage());
            // En caso de error, se cierra la conexión del cliente y finalizar el hilo
            eliminarCliente(clienteSocket);
        } catch (SQLException ex) {
            System.out.println("Error al insertar en la base de datos: " + ex.getMessage());
        }
    }
}
