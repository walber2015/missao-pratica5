/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroclient;

import javax.swing.*;
import java.awt.*;

public class SaidaFrame extends JDialog {
    public JTextArea texto;

    public SaidaFrame(Frame owner) {
        super(owner, "Mensagens do Servidor", false);
        setBounds(100, 100, 400, 300);
        
        texto = new JTextArea();
        texto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(texto);
        
        add(scrollPane);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        SaidaFrame frame = new SaidaFrame(null);
        frame.setVisible(true);
        frame.texto.append("Teste de mensagem\n");
    }
}