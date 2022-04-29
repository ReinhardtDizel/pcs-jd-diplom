import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String... args) {

        while (true) {
            try (Socket socket = new Socket("localhost", 8989);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                Scanner scanner = new Scanner(System.in);

                System.out.println("\n" + "1 Поиск" + "\n"
                        + "0 Выход" + "\n");
                System.out.print("Выберите действие: ");
                String action = scanner.nextLine();
                if (action.equals("0")) {
                    out.println("0");
                    break;
                }

                System.out.print("Введите слово для поиска: ");
                String word = scanner.nextLine();
                out.println(word);

                String currentLine;
                while (true) {
                    currentLine = in.readLine();
                    if (currentLine == null) break;

                    System.out.println(currentLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
