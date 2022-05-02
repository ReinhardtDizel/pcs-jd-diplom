import exception.ClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
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
                    throw new ClientException("Работа завершена пользователем");
                }

                System.out.print("Введите слово для поиска: ");
                String word = scanner.nextLine();
                if (word.isEmpty()) {
                    throw new ClientException("Вы ввели  пустую строку!");
                }
                out.println(word);

                String currentLine;
                while (true) {
                    currentLine = in.readLine();
                    if (currentLine == null) {
                        break;
                    }
                    System.out.println(currentLine);
                }

            } catch (ConnectException connectException) {
                System.err.println("Соединение с сервером потеряно");
                connectException.printStackTrace();
                break;
            } catch (IOException e) {
                System.err.println("Ошибка клиента");
                e.printStackTrace();
                break;
            } catch (ClientException clientException) {
                System.err.println(clientException.getMessage());
                break;
            }
        }
    }
}
