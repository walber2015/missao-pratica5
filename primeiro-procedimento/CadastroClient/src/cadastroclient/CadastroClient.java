/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastroclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;

public class CadastroClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Enviar login e senha
            String login = "op1";
            String senha = "op1";
            out.writeObject(login);
            out.writeObject(senha);

            // Ler resposta do servidor
            String response = (String) in.readObject();
            System.out.println(response);

            if ("Usuário logado com sucesso".equals(response)) {
                // Enviar comando para listar produtos
                out.writeObject("L");

                // Receber e imprimir a lista de produtos
                List<Produto> produtos = (List<Produto>) in.readObject();
                for (Produto produto : produtos) {
                    System.out.println("Produto: " + produto.getNome() + ", Preço: " + produto.getPrecoVenda());
                }
            } else {
                System.out.println("Falha na autenticação.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro na comunicação com o servidor: " + e.getMessage());
        }
    }
}
