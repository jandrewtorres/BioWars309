package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ServerCommunicator implements Runnable {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Boolean running;
	
	public ServerCommunicator(OutputStream out, InputStream in) {
		running = true;
		
		try {
			this.out = new ObjectOutputStream(out);
			this.in = new ObjectInputStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void transmitMessage(Object message) {
		try {
			out.writeObject(message);
			out.flush();
		} catch(IOException ioe) {
			running = false;
			closeStreams();
		}
	}
	
	private void receiveMessage(Object rxData) {

	}
	
	@Override
	public void run() {
		System.out.println(this.running);
		while(running) {

			try {
				Thread.sleep(250);
				receiveMessage(in.readObject());
			} catch (ClassNotFoundException e) {
				closeStreams();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Exception in reading object from input stream");
				running = false;
				closeStreams();
			}
		}
	}
	
    private void closeStreams() {
        
        try {
            out.close();
        }
        catch (Exception e) {
        		e.printStackTrace();
        }
        
        try {
            in.close();
        }
        catch (Exception e) {
        		e.printStackTrace();
        }
        
    }
}    

