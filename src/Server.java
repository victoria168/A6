/******************************************************************************************************************
* File: Server
* Course: 17630
* Project: Assignment A6
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*   1.0 November 2018 - Initial write of assignment 6 (ajl).
*
* Description: This class illustrates how to use sockets to receive job request from the client job submission 
* simulator (JobSim.java). You can use the class to create your application. You may not deviate from the 
* communication protocol used here.
*
* Parameters: None
*
* Internal Methods:
*   none
*
* External Dependencies: 
*   none
******************************************************************************************************************/


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
 
public class Server 
{

    public static void main(String[] args) throws IOException 
    {   
        // We create a listener socket and wait for the client.
        ServerSocket listener = new ServerSocket(9090);

        System.out.println("Waiting for client...");

        try 
        {
            // Here we accept a client and the print a message that we have connected.
            Socket s = listener.accept();
            System.out.println("Client connected...");

            while (true) 
            {

                try 
                {
                    // Here we read the job. You will get one of four strings from the client job 
                    // simulator: ProductA, ProductB, ProductC, ProductD for each of the possible products.
                    // Once received, I print out the strings.

                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String msg = input.readLine();
                    System.out.println("Received Job: " + msg);

                } catch(Exception e) {

                    System.out.println("Error reading socket:: " + e);

                }

            } // while

        } catch(Exception e) {

            System.out.println("Error connecting to client:: " + e);
    
        } // try
    } // main
} // class