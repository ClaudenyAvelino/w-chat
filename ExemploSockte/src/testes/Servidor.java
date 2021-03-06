package testes;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Servidor {

	public static void main(String[] args) {
try {			
			ServerSocket servidor = new ServerSocket(12345);
			System.out.println("Servidor ativo, aguardando conex?o ....");
			
			while (true) {
				Socket cliente =  servidor.accept();
				System.out.println("IP do cliente: "+ cliente.getInetAddress().getHostAddress());
				
				ObjectInputStream dadosDoCliente =
						new ObjectInputStream(cliente.getInputStream());
				String nome = (String) dadosDoCliente.readObject();
				System.out.println("Nome do usuario conectado: "+ nome );
				
				ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
				saida.flush();
				saida.writeObject(new Date());
				
				saida.close();
				cliente.close();
				
			}
		} catch (Exception e) {
			System.out.println("Erro no servidor: "+ e.getMessage());
			e.printStackTrace();
		}

	}

}
