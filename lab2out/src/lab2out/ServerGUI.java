//Owen Kiilsgaard
//Software Engineering-Dr Smith
//Lab 2 Out
//This is the ServerGUI class:
//This class opens a window with text field options, and server connection status indicator
//Interacts with ChatServer class to create a server and establish a connection
//Server Log prints important events that occur based on user input
//Usage:Enter Port Number and Timeout value into respective fields
//      Use buttons at bottom of window for labeled actions

package lab2out;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//ServerGUI Class
public class ServerGUI extends JFrame {
    private ChatServer server;
    private JLabel status;
    private String[] labels = {"Port #", "Timeout"};
    private JTextField[] textFields = new JTextField[labels.length];
    private JTextArea log;

    //Constructor, takes window name as argument, passed in as a command line argument
    public ServerGUI(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Status JLabel
        status = new JLabel("Not Connected");
        status.setForeground(Color.RED);

        // North Panel with Jlabel
        JPanel north = new JPanel(new FlowLayout());
        north.add(status);

        // Center panel with labels, text fields, and text area
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding labels and text fields
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel label = new JLabel(labels[i]);
            center.add(label, gbc);

            gbc.gridx = 1;
            textFields[i] = new JTextField(15);
            center.add(textFields[i], gbc);
        }

        // TextArea for server log
        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        center.add(new JLabel("Server Log Below"), gbc);

        gbc.gridy++;
        log = new JTextArea(10, 30);
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        center.add(logScrollPane, gbc);

        // South panel with buttons
        JPanel south = new JPanel(new FlowLayout());
        JButton listen = new JButton("Listen");
        JButton close = new JButton("Close");
        JButton stop = new JButton("Stop");
        JButton quit = new JButton("Quit");
        south.add(listen);
        south.add(close);
        south.add(stop);
        south.add(quit);

        // Set the layout of the JFrame
        this.setLayout(new BorderLayout());
        this.add(north, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);

        // Register the EventHandler with buttons
        EventHandler handler = new EventHandler();
        listen.addActionListener(handler);
        close.addActionListener(handler);
        stop.addActionListener(handler);
        quit.addActionListener(handler);

        // Jframe sizing
        this.setSize(400, 400);
        this.setVisible(true);
    }

    // Inner class to handle button events
    private class EventHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "Listen":
                    startServer();
                    break;
                case "Close":
                    try {
                        closeServer();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Stop":
                    stopServer();
                    break;
                case "Quit":
                    dispose();
                    break;
            }
        }

        // Starts the server
        private void startServer() {
            try {
                String portText = textFields[0].getText();
                String timeoutText = textFields[1].getText();

                if (portText.isEmpty()) {
                    log.append("Port number must be entered before pressing Listen\n");
                    return;
                }

                int port = Integer.parseInt(portText);
                int timeout = timeoutText.isEmpty() ? 0 : Integer.parseInt(timeoutText);

                if (server == null || !server.isListening()) {
                    // Create the ChatServer with the specified port
                    server = new ChatServer(port);
                    server.setLog(log);  // Server Class will handle the logging
                    server.setStatus(status);  // Server Class will update the status

                    if (timeout > 0) {
                        server.setTimeout(timeout);  // Set timeout if specified
                    }

                    server.listen();  // Start listening for clients
                } else {
                    log.append("Server is already running\n");
                }
            } catch (NumberFormatException ex) {
                log.append("Invalid port or timeout value entered. Please enter valid numbers.\n");
            } catch (Exception ex) {
                log.append("Error: " + ex.getMessage() + "\n");
            }
        }

        // Stops the server
        private void stopServer() {
            if (server != null && server.isListening()) {
                server.stopListening();  // Server handles the logging
            } else {
                log.append("Server not currently started\n");
            }
        }

        // Closes the server
        private void closeServer() throws IOException {
            if (server != null && server.isListening()) {
                server.close();  // Server handles the logging
            } else {
                log.append("Server not currently started\n");
            }
        }
    }

    public static void main(String[] args) {
        new ServerGUI(args.length > 0 ? args[0] : "Server GUI");
    }
}


