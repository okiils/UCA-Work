//Owen Kiilsgaard
//Software Engineering, Dr Mark Smith
//September 9th 2024, Lab 2 In
//Create and test two classes: ChatServer, and TestChatServer
package lab2out;

public class TestChatServer {

    // Data field: A ChatServer instance
    private ChatServer server;

    // Constructor that accepts port and timeout as input parameters
    public TestChatServer(int port, int timeout) {
        //Instantiate a ChatServer
        server = new ChatServer(port);

        //Set the timeout
        server.setTimeout(timeout);  // If ChatServer or AbstractServer has this method

        //Invoke the listen method
        try {
            server.listen();
        } catch (Exception e) {
            System.out.println("Error while starting the server: " + e.getMessage());
        }
    }
    //Main method
    public static void main(String[] args) {
        //Checking for 2 command line arguments
        if (args.length != 2) {
            System.out.println("Usage: java TestChatServer <port> <timeout>");
            return;
        }
        //Parsing Command Line arguments
        try {
            int port = Integer.parseInt(args[0]);  // Port Number
            int timeout = Integer.parseInt(args[1]);  // Timeout

            // Create a TestChatServer instance with the provided port and timeout
            TestChatServer testServer = new TestChatServer(port, timeout);
            System.out.println("Server started at port " + port + " and timeout " + timeout);

        } catch (NumberFormatException e) {
            System.out.println("Error: Both port and timeout must be valid integers.");
        }
    }
}
