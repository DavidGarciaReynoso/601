package sockets;

import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Usuario {
    // Clase "Usuario" para iniciar múltiples instancias del cliente

    public static void main(String[] args) {
        // Método principal donde inicia la aplicación
        // Crear dos instancias del cliente
        InstanciaUsuario cliente1 = new InstanciaUsuario();
        cliente1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InstanciaUsuario cliente2 = new InstanciaUsuario();
        cliente2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class InstanciaUsuario extends JFrame {
    // Clase "InstanciaUsuario" que representa cada ventana del cliente

    // Componentes de la interfaz gráfica
    private JTextField campoCliente; // Campo de texto para el nombre del cliente
    private JTextField campoServidor; // Campo de texto para el servidor
    private JTextField campoMensaje; // Campo de texto para el mensaje
    private JButton miboton; // Botón para enviar el mensaje
    private JTextArea areaMensajes; // Área de texto para mostrar mensajes recibidos
    private String clienteNombre; // Nombre del cliente
    private DataOutputStream flujo_salida; // Flujo de salida para enviar mensajes al servidor
    private Socket miSocket; // Socket para la conexión con el servidor

    public InstanciaUsuario() {
        // Constructor de la clase, inicializa la ventana y sus componentes
        setBounds(600, 300, 280, 350); // Establecer posición y tamaño de la ventana

        LaminaInstanciaUsuario lamina = new LaminaInstanciaUsuario(); // Crear la lámina para la ventana

        add(lamina); // Agregar la lámina a la ventana

        setVisible(true); // Hacer visible la ventana 
    }

    class LaminaInstanciaUsuario extends JPanel {
        // Clase interna "LaminaInstanciaUsuario" que representa la interfaz gráfica de cada cliente

        public LaminaInstanciaUsuario() {
            // Constructor de la lámina, crea y organiza los componentes de la interfaz gráfica

            // Etiqueta y campo de texto para "Cliente"
            JLabel textoCliente = new JLabel("Cliente");
            add(textoCliente);
            campoCliente = new JTextField(20);
            add(campoCliente);

            // Etiqueta y campo de texto para "Servidor"
            JLabel textoServidor = new JLabel("Servidor");
            add(textoServidor);
            campoServidor = new JTextField(20);
            add(campoServidor);

            // Etiqueta y campo de texto para "Mensaje"
            JLabel textoMensaje = new JLabel("Mensaje");
            add(textoMensaje);
            campoMensaje = new JTextField(20);
            add(campoMensaje);

            // Botón "Enviar" para enviar mensajes
            miboton = new JButton("Enviar");
            EnviaTexto mievento = new EnviaTexto();
            miboton.addActionListener(mievento);
            add(miboton);

            // Área de texto para mostrar los mensajes recibidos
            areaMensajes = new JTextArea(10, 20);
            areaMensajes.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaMensajes);
            add(scroll);

            // Botón "Desconectar" para desconectarse del servidor
            JButton botonDesconectar = new JButton("Desconectar");
            botonDesconectar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    desconectar();
                }
            });
            add(botonDesconectar);
        }

        private class EnviaTexto implements ActionListener {
            // Clase interna "EnviaTexto" para manejar el evento de enviar mensajes

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Verificar si el flujo de salida aún no ha sido inicializado (primera vez enviando un mensaje)
                    if (flujo_salida == null) {
                        miSocket = new Socket("localhost", 9999); // Conectar con el servidor en "localhost" y puerto 9999
                        flujo_salida = new DataOutputStream(miSocket.getOutputStream()); // Inicializar el flujo de salida

                        clienteNombre = campoCliente.getText(); // Obtener el nombre del cliente del campo de texto
                        flujo_salida.writeUTF(clienteNombre); // Enviar el nombre del cliente al servidor
                        flujo_salida.flush();

                        // Iniciar un hilo para recibir mensajes del servidor
                        Thread hiloRecepcion = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                recibirMensajes();
                            }
                        });
                        hiloRecepcion.start();
                    }

                    String servidor = campoServidor.getText(); // Obtener el nombre del servidor del campo de texto
                    String mensaje = campoMensaje.getText(); // Obtener el mensaje del campo de texto

                    flujo_salida.writeUTF(clienteNombre + ":" + servidor + ":" + mensaje); // Enviar mensaje al servidor
                    flujo_salida.flush(); // Asegurarse de enviar el mensaje inmediatamente

                    campoMensaje.setText(""); // Borrar el campo del mensaje después de enviarlo
                } catch (IOException ex) {
                    System.out.println("Error al enviar el mensaje: " + ex.getMessage());
                }
            }
        }

        private void recibirMensajes() {
            // Método para recibir mensajes del servidor en un hilo separado
            try {
                DataInputStream flujo_entrada = new DataInputStream(miSocket.getInputStream());

                // Mantenerse en un bucle infinito para recibir mensajes continuamente
                while (true) {
                    String mensajeRecibido = flujo_entrada.readUTF();
                    if (mensajeRecibido != null) {
                        areaMensajes.append("\n" + mensajeRecibido); // Mostrar el mensaje recibido en el área de texto
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error al recibir el mensaje: " + ex.getMessage());
                // No cerramos la conexión del cliente, simplemente mostramos el error.
            }
        }

        private void desconectar() {
            // Método para desconectar del servidor y cerrar la conexión
            try {
                flujo_salida.writeUTF(clienteNombre + ":SERVIDOR:DESCONECTAR"); // Enviar mensaje de desconexión al servidor
                flujo_salida.flush();

                miSocket.close(); // Cerrar la conexión con el servidor
            } catch (IOException ex) {
                System.out.println("Error al desconectar: " + ex.getMessage());
            }
        }
    }
}
