package br.com.casadocodigo.util;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/file/*") //Pegue pelo request. Tem como proposito buscar files salvos no server
public class FileServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String path = req.getRequestURI().split("/file")[1]; //Quando encontrar pego o que ficar a direta do file que é o caminho da imagem
		Path source = Paths.get(FileSaver.WAY_SERVER+ "/" + path); //pegando o contentType, busca o file no S.O
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String contentType = fileNameMap.getContentTypeFor("file:" + source); //protocolo de file. Pegando o content type p determinado file
		
		//Regras que o response deve seguir
		res.reset(); //Limpe o header, nas várias fases podem ficar coisas no response
		res.setContentType(contentType); //png, pdf. Navegador entende o contentType e funciona de forma diferente para cada tipo de file	
		res.setHeader("Content-Length", String.valueOf(Files.size(source))); // Pegando o tamanho do file
		res.setHeader("Content-Disposition", "filename=\"" + source.getFileName().toString() + "\""); //Nome do file. Usa p exibir o file
		//Transferencia de file
		FileSaver.transfer(source, res.getOutputStream());
	}

}
