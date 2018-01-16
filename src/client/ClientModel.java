package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ClientModel implements Runnable {
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Boolean running;
	
	public ClientModel(OutputStream out, InputStream in) {
		running = true;
		
		try {
			this.out = new ObjectOutputStream(out);
			this.in = new ObjectInputStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void transmitCommand(String command) {
		try {
			out.writeObject(command);
			out.flush();
		} catch(IOException ioe) {
			running = false;
			closeStreams();
		}
	}
	
	private void receiveObject(Object rxData) {
		String receivedMsg = (String) rxData;
		System.out.println(rxData);
	}
	
	@Override
	public void run() {
		System.out.println(this.running);
		while(running) {

			try {
				Thread.sleep(250);
				receiveObject(in.readObject());
			} catch (ClassNotFoundException e) {
				closeStreams();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				closeStreams();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				closeStreams();
				// TODO Auto-generated catch block
				e.printStackTrace();
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

