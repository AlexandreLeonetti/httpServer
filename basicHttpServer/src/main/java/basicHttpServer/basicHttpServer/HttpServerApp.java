package basicHttpServer.basicHttpServer ;
/* 7 min 10 
 * https://www.youtube.com/watch?v=sw01vBKCiNg
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public  class HttpServerApp{
	public static void main(String[] args) throws Exception {
		var serverSocket = new ServerSocket(8080);
		
		System.out.println("server listenning on 8080");
		while(true) {
			var connection = serverSocket.accept();
			var request    = readRequest(connection);
			System.out.println("we have a new request : "+request+"\n");

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

	private static HttpReq readRequest(Socket connection) throws Exception {//7m49
		    var r = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
			var line = r.readLine();
			var methodUrl = line.split(" ");
			var method	=methodUrl[0];
			var url =  methodUrl[1];
			var protocol = methodUrl[2];
			System.out.println("request");
			
			var headers = readHeaders(r);
			
			return new HttpReq(method, url, protocol,  headers,null);
	}

	/* http server III 3min14 */
    private static Map<String, List<String>> readHeaders(BufferedReader reader) throws Exception{
    	var line = reader.readLine();
        var headers = new HashMap<String, List<String>>();
        
        /*
        for (int i = 1; i < lines.length; i++) {
            var line = lines[i];
            if (line.isEmpty()) {
                break;
            }

            var keyValue = line.split(":", 2);
            var key = keyValue[0].toLowerCase().strip();
            var value = keyValue[1].strip();

            headers.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        */
        
        while (line != null && !line.isEmpty()) {
            var keyValue = line.split(":", 2);
            var key = keyValue[0];
            var value= keyValue[1];
            headers.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            line= reader.readLine();
        }

        return headers;
    }
	
	private record HttpReq(String method,
			String url,
			String protocol,
			Map<String, List<String>> headers,
			Byte[] body) {
		
	}
}
