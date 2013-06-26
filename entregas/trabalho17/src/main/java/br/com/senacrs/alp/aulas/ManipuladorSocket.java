package br.com.senacrs.alp.aulas;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;

public class ManipuladorSocket {

	private Socket socket = null;
	private Reader reader = null;
	private Writer writer = null;

	public ManipuladorSocket(Socket socket) {

		if (socket == null) {
			throw new IllegalArgumentException();
		}
		this.socket = socket;
		try {
			this.socket.setKeepAlive(true);
		} catch (SocketException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Writer criarWriter() throws IOException {

		OutputStream out = null;

		if (this.writer == null) {
			out = this.socket.getOutputStream();
			this.writer = new OutputStreamWriter(out);
		}

		return this.writer;
	}

	public Reader criarReader() throws IOException, InterruptedException {

		InputStream in = null;

		if (this.reader == null) {
			in = this.socket.getInputStream();
			this.reader = new InputStreamReader(in);
		}
		while (!this.reader.ready()) {
			Thread.sleep(1000);
		}

		return this.reader;
	}

	public void finalizar() {

		closeReader();
		closeWriter();
	}

	private void closeReader() {

		if (this.reader != null) {
			try {
				this.reader.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	private void closeWriter() {

		if (this.writer != null) {
			try {
				this.writer.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}
}
