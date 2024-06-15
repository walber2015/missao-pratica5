/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroserver;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioOperadorJpaController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
   public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");
    ProdutoJpaController produtoController = new ProdutoJpaController(emf);
    UsuarioOperadorJpaController usuarioController = new UsuarioOperadorJpaController(emf);
       MovimentoJpaController movimentoController = new MovimentoJpaController(emf);
       PessoaJpaController pessoaController = new PessoaJpaController(emf);

    try (ServerSocket serverSocket = new ServerSocket(4321)) {
        System.out.println("Servidor iniciado na porta 4321");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

            CadastroThread thread = new CadastroThread(produtoController, usuarioController, movimentoController, pessoaController, clientSocket);
            thread.start();
        }
    } catch (IOException e) {
        System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
    } finally {
        emf.close();
    }
}

}
