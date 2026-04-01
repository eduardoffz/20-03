
package com.mercado;
import javax.swing.*;
import java.awt.*;

public class CadastroPanel extends JPanel {

    private FramePrincipal frame;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public CadastroPanel(FramePrincipal frame) {
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(new Color(70,40,40));

        JLabel titulo = new JLabel("CADASTRO DE USUÁRIO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBounds(300, 60, 350, 30);
        add(titulo);

        JLabel lblUser = new JLabel("Novo Usuário:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setBounds(320,150,150,25);
        add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(320,180,250,30);
        add(txtUsuario);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setBounds(320,220,100,25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(320,250,250,30);
        add(txtSenha);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(320,300,120,35);
        add(btnSalvar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(450,300,120,35);
        add(btnVoltar);

        btnSalvar.addActionListener(e -> salvar());
        btnVoltar.addActionListener(e -> frame.mostrarTela("login"));
    }

    private void salvar() {
        String user = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        if(user.isEmpty() || senha.isEmpty()){
            JOptionPane.showMessageDialog(this,"Preencha todos os campos!");
            return;
        }

        UsuarioMemoria.usuarios.put(user, senha);
        JOptionPane.showMessageDialog(this,"Usuário cadastrado com sucesso!");
        frame.mostrarTela("login");
    }
}
