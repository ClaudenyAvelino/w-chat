package chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;

public class ChatServer {
	static ArrayList<String> listaUsuarios = new ArrayList<String>();
	static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();

	public static void main(String[] args) throws Exception {
		System.out.println("Aguardando novos usuarios...");
		ServerSocket ss = new ServerSocket(9086);
		while (true) {
			Socket usuario = ss.accept();
			System.out.println("Usuario conectado!");	
			
			ManipuladorConversa conversa = new ManipuladorConversa(usuario);
			conversa.start();
		}

	}

}
	class ManipuladorConversa extends Thread{
		Socket usuarioConversa ;
		BufferedReader entrada;
		PrintWriter saida;
		String nome;
		
		public ManipuladorConversa (Socket usuarioConversa) throws IOException{
			this.usuarioConversa = usuarioConversa;
		}
		public void run() {
			try {
				entrada =  new BufferedReader(new InputStreamReader(usuarioConversa.getInputStream()));
				saida = new PrintWriter(usuarioConversa.getOutputStream(), true);
				
				int contador= 0;
				
				while (true) {
					if (contador > 0) {
						saida.println("NOME_EXISTENTE");
					}else {
						saida.println("NOME_REQUERIDO");
					}
					
					nome = entrada.readLine();
					
					if (nome == null) {
						return;
					}
					
					if (!ChatServer.listaUsuarios.contains(nome)) {
						ChatServer.listaUsuarios.add(nome);
						break;
//corri							
					}
					
					contador ++;
				}
				
				saida.println("NOME_ACEITO");
				ChatServer.printWriters.add(saida);
				
			} catch (Exception e) {
				System.out.println("Erro no servidor: "+ e.getMessage());
				e.printStackTrace();
			}
			
		}
	}
	
	
	class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ChatClient.saida.println(ChatClient.chatNovaMensagem.getText());
			ChatClient.chatNovaMensagem.setText("");
			
		}
		
	}