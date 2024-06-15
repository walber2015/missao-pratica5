/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroclient;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import model.Produto;

public class ThreadClient extends Thread {

    private ObjectInputStream entrada;
    private JTextArea textArea;

    public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {

            while (true) {

                Object obj = entrada.readObject();

                if (obj instanceof String) {
                    String message = (String) obj;
                    SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
                } else if (obj instanceof List) {
                    List<?> list = (List<?>) obj;
                    SwingUtilities.invokeLater(() -> {
                        for (Object item : list) {
                            if (item instanceof Produto) {
                                Produto produto = (Produto) item;
                                textArea.append("Nome: " + produto.getNome() + ", Quantidade: " + produto.getQuantidade() + "\n");
                            }
                        }
                    });
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
