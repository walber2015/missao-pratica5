/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioOperadorJpaController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;
import model.UsuarioOperador;

public class CadastroThread extends Thread {

    private final ProdutoJpaController ctrl;
    private final UsuarioOperadorJpaController ctrlUsu;
    private final Socket s1;

    public CadastroThread(ProdutoJpaController ctrl, UsuarioOperadorJpaController ctrlUsu, Socket s1) {
        this.ctrl = ctrl;
        this.ctrlUsu = ctrlUsu;
        this.s1 = s1;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
            String login = (String) in.readObject();
            String senha = (String) in.readObject();

            UsuarioOperador usuario = ctrlUsu.findUsuario(login, senha);

            if (usuario == null) {
                out.writeObject("Credenciais inválidas. Conexão encerrada.");
                s1.close();
                return;
            }

            out.writeObject("Usuário logado com sucesso");

            while (true) {
                String command = (String) in.readObject();

                if ("L".equals(command)) {
                    List<Produto> produtos = ctrl.findProdutos();
                    out.writeObject(produtos);
                } else {
                    out.writeObject("Comando desconhecido");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
        }
    }

}
