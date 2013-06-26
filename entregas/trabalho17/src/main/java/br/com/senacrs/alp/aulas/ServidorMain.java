package br.com.senacrs.alp.aulas;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMain {

	private static final Factory factory = Factory.getInstancia();
	private static final String PWD = System.getProperty("user.dir");
	private static final String CONFIG = PWD 
			+ File.separator + "config"
			+ File.separator + "config.txt";

	public static void main(String[] args) {

		ServerSocket server = null;
		ArquivoConfiguracao config = null;
		ResponderRequisicaoGet logica = null;
		String arquivoConfiguracao = null;

		arquivoConfiguracao = CONFIG;
		try {
			config = factory.criarConfiguracao(arquivoConfiguracao);
			logica = factory.criarValidacao(config);
			System.out.println("Aceitando conexoes na porta: " + config.getPort());
			server = new ServerSocket(config.getPort());
			lacoServidor(server, logica);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} finally {
			closeServer(server);
			System.out.println("Servidor Finalizado " );
		}
		
		
	}

	private static void lacoServidor(ServerSocket server,
			ResponderRequisicaoGet logica) throws IOException {

		Socket socket = null;
		ManipuladorSocket manipulador = null;
		Reader reader = null;
		Writer writer = null;

		try {
			while (true) {
				socket = server.accept();
				manipulador = factory.criarManipulador(socket);				
				reader = manipulador.criarReader();
				writer = manipulador.criarWriter();
				logica.responderRequisicao(reader, writer);
				writer.flush();
				socket.close();
			}
		} catch (InterruptedException e) {
			manipulador.finalizar();
			socket.close();
		}
	}

	private static void closeServer(ServerSocket server) {

		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}
}
