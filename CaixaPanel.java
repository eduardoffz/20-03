
package com.mercado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CaixaPanel extends JPanel {

    private FramePrincipal frame;
    private DefaultTableModel model;
    private JLabel lblSaldo;
    private double saldo = 0;

    public CaixaPanel(FramePrincipal frame) {
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        model = new DefaultTableModel(
            new String[]{"Tipo","Valor"},0);

        JTable tabela = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel top = new JPanel();
        JTextField txtValor = new JTextField(10);
        JButton btnVenda = new JButton("Venda");
        JButton btnDespesa = new JButton("Despesa");
        JButton btnExcluir = new JButton("Excluir Selecionado");
        JButton btnLogout = new JButton("Logout");

        top.add(new JLabel("Valor:"));
        top.add(txtValor);
        top.add(btnVenda);
        top.add(btnDespesa);
        top.add(btnExcluir);
        top.add(btnLogout);

        lblSaldo = new JLabel("Saldo: R$ 0.00");
        lblSaldo.setFont(new Font("Arial",Font.BOLD,18));

        add(top,BorderLayout.NORTH);
        add(scroll,BorderLayout.CENTER);
        add(lblSaldo,BorderLayout.SOUTH);

        btnVenda.addActionListener(e -> {
            double v = Double.parseDouble(txtValor.getText());
            saldo += v;
            model.addRow(new Object[]{"VENDA",v});
            atualizarSaldo();
        });

        btnDespesa.addActionListener(e -> {
            double v = Double.parseDouble(txtValor.getText());
            saldo -= v;
            model.addRow(new Object[]{"DESPESA",v});
            atualizarSaldo();
        });

        btnExcluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if(row >= 0){
                String tipo = model.getValueAt(row,0).toString();
                double valor = Double.parseDouble(model.getValueAt(row,1).toString());

                if(tipo.equals("VENDA")) saldo -= valor;
                else saldo += valor;

                model.removeRow(row);
                atualizarSaldo();
            }
        });

        btnLogout.addActionListener(e -> frame.mostrarTela("login"));
    }

    private void atualizarSaldo() {
        lblSaldo.setText("Saldo: R$ " + String.format("%.2f", saldo));
    }
}
