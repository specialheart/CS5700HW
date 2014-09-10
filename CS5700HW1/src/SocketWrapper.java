import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketWrapper {
	private Socket socket;
	private BufferedWriter socketWriter;
	private BufferedReader socketReader;
	private String hostName = "localhost";
	private int port = 30000;
	private boolean ssl = false;

	public SocketWrapper(String portNum, String hostName, boolean ssl) {
		if (portNum != null)
			this.setPort(Integer.parseInt(portNum));
		if (hostName != null)
			this.setHostName(hostName);
		if (ssl)
			this.ssl = ssl;
		createSocket();
	}

	public boolean createSocket() {
		try {
			socket = new Socket(getHostName(), getPort());
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			socketWriter = new BufferedWriter(osw);
			socketReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public void closeSocket() {
		if (this.socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Connection closed failed!");
			}
		}
	}

	public void sendMessage(String message) throws IOException {
		getSocketWriter().write(message);
		getSocketWriter().flush();
	}

	public String readMessage() throws IOException {
		return getSocketReader().readLine();
	}

	public String[] getResponse() throws IOException {
		String message = readMessage();
		return message.split("\\s+");
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public BufferedWriter getSocketWriter() {
		return socketWriter;
	}

	public void setSocketWriter(BufferedWriter socketWriter) {
		this.socketWriter = socketWriter;
	}

	public BufferedReader getSocketReader() {
		return socketReader;
	}

	public void setSocketReader(BufferedReader socketReader) {
		this.socketReader = socketReader;
	}
}