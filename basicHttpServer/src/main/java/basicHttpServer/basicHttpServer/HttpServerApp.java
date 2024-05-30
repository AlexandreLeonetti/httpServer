package basicHttpServer.basicHttpServer ;

import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

public  class HttpServerApp{
	public static void main(String[] args) throws Exception {
		var serverSocket = new ServerSocket(8080);
		
				System.out.println("server listenning on 8080");
		while(true) {
			var connection = serverSocket.accept();
			try( var os = connection.getOutputStream()){
				String body = """
						{
						"id":1,
						}
						""";
				var response = """
						HTTP/1.1 200 OK
						Content-Type: application/json
						Content-Length: %d

						%s
						""".formatted(body.getBytes(StandardCharsets.UTF_8).length, body);
				
				os.write(response.getBytes(StandardCharsets.UTF_8));

				System.out.println("server listenning on 8080");
			}
		}
		
		
	}
}
