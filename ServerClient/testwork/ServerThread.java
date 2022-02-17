package testwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class ServerThread extends Thread{
	Socket s;
	public ServerThread(Socket s) {
		this.s=s;
	}
}
