/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastroclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class CadastroClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 4321); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {

            out.writeObject("op1");
            out.writeObject("op1");

            SaidaFrame frame = new SaidaFrame(null);
            frame.setVisible(true);

            ThreadClient threadClient = new ThreadClient(in, frame.texto);
            threadClient.start();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Menu:");
                System.out.println("L – Listar");
                System.out.println("E – Entrada");
                System.out.println("S – Saída");
                System.out.println("X – Finalizar");
                System.out.print("Escolha uma opção: ");
                String command = scanner.nextLine();

                out.writeObject(command);
                if ("X".equalsIgnoreCase(command)) {
                    break;
                }
                if ("E".equalsIgnoreCase(command) || "S".equalsIgnoreCase(command)) {
                    System.out.print("Id da pessoa: ");
                    int idPessoa = Integer.parseInt(scanner.nextLine());
                    out.writeObject(idPessoa);

                    System.out.print("Id do produto: ");
                    int idProduto = Integer.parseInt(scanner.nextLine());
                    out.writeObject(idProduto);

                    System.out.print("Quantidade: ");
                    int quantidade = Integer.parseInt(scanner.nextLine());
                    out.writeObject(quantidade);

                    System.out.print("Valor unitário: ");
                    double valorUnitario = Double.parseDouble(scanner.nextLine());
                    out.writeDouble(valorUnitario);
                    out.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
