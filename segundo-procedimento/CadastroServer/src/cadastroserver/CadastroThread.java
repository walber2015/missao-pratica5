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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produto;
import model.Movimento;
import model.UsuarioOperador;
import model.Pessoa;

public class CadastroThread extends Thread {

    private final ProdutoJpaController ctrlProd;
    private final UsuarioOperadorJpaController ctrlUsu;
    private final MovimentoJpaController ctrlMov;
    private final PessoaJpaController ctrlPessoa;
    private final Socket s1;

    public CadastroThread(ProdutoJpaController ctrlProd, UsuarioOperadorJpaController ctrlUsu, MovimentoJpaController ctrlMov, PessoaJpaController ctrlPessoa, Socket s1) {
        this.ctrlProd = ctrlProd;
        this.ctrlUsu = ctrlUsu;
        this.ctrlMov = ctrlMov;
        this.ctrlPessoa = ctrlPessoa;
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
                
                if("X".equals(command)){
                    out.close();
                    in.close();
                    s1.close();
                }
                
                if ("L".equals(command)) {
                    List<Produto> produtos = ctrlProd.findProdutos();
                    out.writeObject(produtos);
                } else if ("E".equals(command) || "S".equals(command)) {

                    Integer idPessoa = (Integer) in.readObject();
                    Pessoa pessoa = ctrlPessoa.findPessoa(idPessoa);

                    Integer idProduto = (Integer) in.readObject();
                    Produto produto = ctrlProd.findProduto(idProduto);

                    Integer quantidade = (Integer) in.readObject();

                    double valorUnitario = (in.readDouble());

                    Movimento movimento = new Movimento(quantidade, command, valorUnitario, pessoa, produto, usuario);
                    ctrlMov.create(movimento);

                    if ("E".equals(command)) {
                        produto.setQuantidade(produto.getQuantidade() + quantidade);
                    } else if ("S".equals(command)) {
                        produto.setQuantidade(produto.getQuantidade() - quantidade);
                    }
                    ctrlProd.edit(produto);

                    out.writeObject("Movimento registrado com sucesso.");
                } else {

                    out.writeObject("Comando desconhecido");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
