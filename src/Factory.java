
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.*;


public class Factory
{
	ArrayList<String> JobList = new ArrayList<String> ();
	Semaphore SemList = new Semaphore(1);

	public static void main(String[] args) throws IOException
    {
    	Factory MyFactory = new Factory();
    	MyFactory.startServer();

    	MyFactory.AllocateJob();
    }

    private void AllocateJob() {
	    try
        {
            while (true) {
                if (JobList.size() > 0) {
                    SemList.acquire();
                    String jobName = JobList.remove(0);
                    SemList.release();
                    System.out.println("Job removed " + jobName);
                }
                java.lang.Thread.sleep(1000);
            }

        } catch( InterruptedException Error ) {

            System.out.println( "Thread stopped.\n" );

        } // catch
    }

    public void startServer() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket listener = new ServerSocket(9090);
                    System.out.println("Waiting for clients to connect...");

                    try {
                    	Socket s = listener.accept();
                    	System.out.println("Client connected...");


	                    while (true) {
	                    	try {
	                    		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
	                    		String msg = input.readLine();
	                    		System.out.println("Received Job: " + msg);
	                    		SemList.acquire();
	                    		JobList.add(msg);
	                    		SemList.release();
	                    	} catch (Exception e) {
	                    		System.out.println("Error reading socket:: " + e);
	                    	}
	                    }
	                } catch (Exception e) {
            			System.out.println("Error connecting to client:: " + e);
	                }
                } catch (Exception e) {
            		System.out.println("Error connecting to client:: " + e);
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }
}