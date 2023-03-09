//13-10-2022 update


// A first framework for an "action game" for 2 players
// communicating data (objects) trough a server
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

class Server extends JFrame
{
    JLabel label;
    static int port =8888;

    ServerSocket server;
    Socket socket1; // for client1
    Socket socket2; // for client2

    InputStream inputStream1; // for client1
    ArrayBlockingQueue<Data> dataPool = new ArrayBlockingQueue<Data>(100);

    ObjectInputStream objectinputStream1; // for client1
    InputStream inputStream2; // for client2
    ObjectInputStream objectinputStream2;// for client2

    OutputStream outputStream1; // for client1
    ObjectOutputStream objectOutputStream1; // for client1
    OutputStream outputStream2; // for client2
    ObjectOutputStream objectOutputStream2; // for client2

    public Server() throws IOException
    {
        setTitle("Server");
        getContentPane().setBackground(Color.ORANGE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200,100);
        setResizable(false);
        setVisible(true);
        serverConnection();
    }

    public  void serverConnection() throws IOException
    {
        server = new ServerSocket(port);
        System.out.println("ServerSocket awaiting connections...");
        label = new JLabel("Waiting for 2 clients");
        label.setFont(new Font("Verdana", Font.PLAIN, 18));
        add(label);

        socket1 = server.accept();
        outputStream1 = socket1.getOutputStream();
        objectOutputStream1 = new ObjectOutputStream(outputStream1);

        socket2 = server.accept();
        outputStream2 = socket2.getOutputStream();
        objectOutputStream2 = new ObjectOutputStream(outputStream2);

        // Here to clients(players) are connected to server
        label.setText("  2 clients connected");
        inputStream1 = socket1.getInputStream();
        objectinputStream1 = new ObjectInputStream(inputStream1);

        inputStream2 = socket2.getInputStream();
        objectinputStream2 = new ObjectInputStream(inputStream2);

        // send signal to clients once connected to
        // start their threads.

        Thread handleClients = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    try {
                        // read data from client1
                        Data d1 = (Data) objectinputStream1.readObject();
                        Data d2 = (Data) objectinputStream2.readObject();
                        // send data to client2
                        objectOutputStream2.writeObject(d1);
                        objectOutputStream1.writeObject(d2);
                        objectOutputStream1.flush();
                        objectOutputStream2.flush();

                        try {
                            Thread.sleep(10);

                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        // start the threads that run concurrently in Server
        handleClients.start();
    }


    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}