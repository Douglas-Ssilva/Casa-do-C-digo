package br.com.casadocodigo.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;

import javax.servlet.http.Part;

public class FileSaver {

	private static final String SEPARATOR = "/";
	public static final String WAY_SERVER = "/casadocodigo"; // colocar / antes informa p salvar na raiz do system

	//Grava a imagem em disco
	public String write(Part file, String way) {
		String relativePath = way + SEPARATOR + file.getSubmittedFileName();
		try {
			file.write(WAY_SERVER  + SEPARATOR + relativePath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return relativePath;
	}

	// No Java 9, todos eles agora retornam ByteBuffer, enquanto os métodos que eles substituem retornam Buffer,
	//resultando em exceções como esta ao executar no Java 8 e inferior:
	//java.lang.NoSuchMethodError: java.nio.ByteBuffer.limit (I) Ljava / nio / ByteBuffer
	
	public static void transfer(Path source, OutputStream outputStream) {
		// primeiro temos que pegar o file de algum lugar, por isso usamos o inputStream, e depois mandamos p output
		// Sempre que manipulamos files, temos que fechar o mesmo
		
		try {
			FileInputStream input = new FileInputStream(source.toFile());       //file de entrada
			try (ReadableByteChannel inputChannel = Channels.newChannel(input); // Abrindo um canal de entrada 
				WritableByteChannel outputChannel = Channels.newChannel(outputStream)) {
				Buffer buffer = (Buffer) ByteBuffer.allocateDirect(1024 * 10);      // Capacidade que queremos alocar(allocateDirect) uns 10kb, se ficar mto grande pode estourar o server
				while (inputChannel.read((ByteBuffer)buffer) != -1) {				       // -1 significa que já leu tudo. Lendo do buffer
					buffer.flip(); 											  // seta os bytes p 0 novamente
					outputChannel.write((ByteBuffer)buffer);     						   // Escrevendo no buffer
					buffer.clear(); 										   // Acabou de ler, limpe do buffer p ele ler novamente mais informações de bytes no file
				}
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
