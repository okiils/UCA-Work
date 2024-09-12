//Owen Kiilsgaard
//Software Engineering-Dr Smith
//Lab 2 Out
//This is the ChatServer class
package lab2out;

import javax.swing.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import java.awt.*;

public class ChatServer extends AbstractServer {
    private JTextArea log;
    private JLabel status;

    // Constructor to accept port number
    public ChatServer(int port) {
        super(port);  // Use the provided port
    }

    public void setLog(JTextArea log) {
        this.log = log;
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }

    @Override
    protected void serverStarted() {
        if (log != null) {
            log.append("Server Started\n");
        }
        if (status != null) {
            status.setText("Listening");
            status.setForeground(Color.GREEN);
        }
    }

    @Override
    protected void serverStopped() {
        if (log != null) {
            log.append("Server Stopped Accepting New Clients\n");
        }
        if (status != null) {
            status.setText("Stopped");
            status.setForeground(Color.RED);
        }
    }

    @Override
    protected void serverClosed() {
        if (log != null) {
            log.append("Server and all clients closed\n");
        }
        if (status != null) {
            status.setText("Closed");
            status.setForeground(Color.RED);
        }
    }

    @Override
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (log != null) {
            log.append("Message from client received: " + msg + "\n");
        }
    }

    @Override
    protected void listeningException(Throwable exception) {
        if (log != null) {
            // Log the exception message in the JTextArea
            log.append("Listening Exception: " + exception.getMessage() + "\n");
            log.append("Press Listen to Restart Server\n");
        }
        if (status != null) {
            // Update the status label to indicate an exception occurred
            status.setText("Exception Occurred when Listening");
            status.setForeground(Color.RED);
        }
    }


    @Override
    protected void clientConnected(ConnectionToClient client) {
        if (log != null) {
            log.append("Client connected\n");
        }
    }
}

