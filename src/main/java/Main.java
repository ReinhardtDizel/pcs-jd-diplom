import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import search.BooleanSearchEngine;
import search.PageEntry;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));

        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("New connection accepted");
                    String word = URLDecoder.decode(in.readLine(), StandardCharsets.UTF_8.name());

                    List<PageEntry> searchResult = engine.search(word);
                    if (searchResult == null) {
                        break;
                    }
                    GsonBuilder builder = new GsonBuilder();
                    builder.setPrettyPrinting();
                    Gson gson = builder.create();

                    out.println("{" + word + "} ->");
                    for (PageEntry pe : searchResult) {
                        out.println(gson.toJson(pe));
                    }

                } catch (IOException exception) {
                    exception.printStackTrace();
                    break;
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}