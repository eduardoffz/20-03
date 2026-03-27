package app;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class CaixaScreen extends JFrame {

    private JLabel saldoLabel;
    private JTextField descField;
    private JTextField valorField;
    private DefaultTableModel tableModel;
    private JTable tabela;
    private String operador;

    private static final Color COR_FUNDO     = new Color(236, 240, 241);
    private static final Color COR_PAINEL    = Color.WHITE;
    private static final Color COR_VENDA     = new Color(39, 174, 96);
    private static final Color COR_DESPESA   = new Color(192, 57, 43);
    private static final Color COR_EXCLUIR   = new Color(243, 156, 18);
    private static final Color COR_LOGOUT    = new Color(149, 165, 166);
    private static final Color COR_SALDO_POS = new Color(39, 174, 96);
    private static final Color COR_SALDO_NEG = new Color(192, 57, 43);
    private static final Color COR_HEADER    = new Color(44, 62, 80);

    public CaixaScreen(String operador) {
        this.operador = operador;
        setTitle("Simulador de Caixa - " + operador);
        setSize(720, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(0, 0));

        add(criarHeader(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);

        carregarMovimentacoes();
        atualizarSaldo();
    }

    private JPanel criarHeader() {
        JPanel header = new JPanel(null);
        header.setBackground(COR_HEADER);
        header.setPreferredSize(new Dimension(720, 65));

        JLabel icone = new JLabel("\uD83D\uDCB0");
        icone.setFont(new Font("SansSerif", Font.PLAIN, 28));
        icone.setBounds(18, 17, 40, 35);
        header.add(icone);

        JLabel titulo = new JLabel("Simulador de Caixa");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(60, 10, 280, 30);
        header.add(titulo);

        JLabel sub = new JLabel("Operador: " + operador);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(new Color(189, 195, 199));
        sub.setBounds(62, 38, 200, 18);
        header.add(sub);

        saldoLabel = new JLabel("Saldo: R$ 0,00");
        saldoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        saldoLabel.setForeground(Color.WHITE);
        saldoLabel.setBounds(440, 20, 240, 30);
        header.add(saldoLabel);

        return header;
    }

    private JPanel criarPainelCentral() {
        JPanel centro = new JPanel(null);
        centro.setBackground(COR_FUNDO);

        JPanel painelForm = new JPanel(null);
        painelForm.setBackground(COR_PAINEL);
        painelForm.setBounds(15, 12, 320, 130);
        painelForm.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            new EmptyBorder(10, 14, 10, 14)
        ));

        JLabel lblTitForm = new JLabel("Nova Movimentação");
        lblTitForm.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTitForm.setForeground(COR_HEADER);
        lblTitForm.setBounds(14, 10, 200, 22);
        painelForm.add(lblTitForm);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblDesc.setBounds(14, 40, 80, 22);
        painelForm.add(lblDesc);

        descField = new JTextField();
        descField.setBounds(95, 40, 205, 26);
        descField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        painelForm.add(descField);

        JLabel lblValor = new JLabel("Valor (R$):");
        lblValor.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblValor.setBounds(14, 76, 80, 22);
        painelForm.add(lblValor);

        valorField = new JTextField();
        valorField.setBounds(95, 76, 205, 26);
        valorField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        painelForm.add(valorField);

        centro.add(painelForm);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 2, 8, 8));
        painelBotoes.setBackground(COR_FUNDO);
        painelBotoes.setBounds(15, 155, 320, 80);

        JButton btnVenda   = criarBotao("+ Venda",    COR_VENDA);
        JButton btnDespesa = criarBotao("- Despesa",  COR_DESPESA);
        JButton btnExcluir = criarBotao("Excluir",    COR_EXCLUIR);
        JButton btnLogout  = criarBotao("Logout",     COR_LOGOUT);

        painelBotoes.add(btnVenda);
        painelBotoes.add(btnDespesa);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLogout);
        centro.add(painelBotoes);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(COR_PAINEL);
        painelTabela.setBounds(350, 12, 350, 390);
        painelTabela.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            new EmptyBorder(8, 8, 8, 8)
        ));

        JLabel lblHistorico = new JLabel("Histórico de Movimentações");
        lblHistorico.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblHistorico.setForeground(COR_HEADER);
        lblHistorico.setBorder(new EmptyBorder(0, 0, 6, 0));
        painelTabela.add(lblHistorico, BorderLayout.NORTH);

        String[] colunas = {"ID", "Descrição", "Valor", "Tipo", "Data/Hora"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabela = new JTable(tableModel);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 11));
        tabela.setRowHeight(22);
        tabela.setSelectionBackground(new Color(173, 216, 230));
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        tabela.getTableHeader().setBackground(COR_HEADER);
        tabela.getTableHeader().setForeground(Color.WHITE);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(55);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(110);

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                if (!sel) {
                    String tipo = (String) t.getValueAt(row, 3);
                    if ("VENDA".equals(tipo)) {
                        c.setForeground(new Color(27, 120, 65));
                    } else {
                        c.setForeground(new Color(150, 30, 20));
                    }
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        painelTabela.add(scroll, BorderLayout.CENTER);
        centro.add(painelTabela);

        btnVenda.addActionListener(e -> registrar("VENDA"));
        btnDespesa.addActionListener(e -> registrar("DESPESA"));
        btnExcluir.addActionListener(e -> excluir());
        btnLogout.addActionListener(e -> logout());

        return centro;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(null);
        rodape.setBackground(new Color(220, 223, 225));
        rodape.setPreferredSize(new Dimension(720, 28));

        JLabel info = new JLabel("Sistema de Caixa  |  Banco: caixa_db  |  Porta: 3337");
        info.setFont(new Font("SansSerif", Font.PLAIN, 10));
        info.setForeground(new Color(100, 100, 100));
        info.setBounds(14, 7, 400, 16);
        rodape.add(info);

        return rodape;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(new EmptyBorder(6, 10, 6, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void registrar(String tipo) {
        String desc = descField.getText().trim();
        String valorStr = valorField.getText().trim().replace(",", ".");

        if (desc.isEmpty() || valorStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha descrição e valor!");
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
            if (valor <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido! Use números positivos.");
            return;
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO movimentacoes (descricao, valor, tipo) VALUES (?, ?, ?)")) {

            ps.setString(1, desc);
            ps.setDouble(2, valor);
            ps.setString(3, tipo);
            ps.executeUpdate();

            descField.setText("");
            valorField.setText("");
            carregarMovimentacoes();
            atualizarSaldo();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar: " + e.getMessage());
        }
    }

    private void excluir() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma movimentação para excluir.");
            return;
        }

        int id = (int) tableModel.getValueAt(linha, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Excluir a movimentação selecionada?", "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "DELETE FROM movimentacoes WHERE id = ?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
            carregarMovimentacoes();
            atualizarSaldo();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja fechar o caixa e sair?", "Logout",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            new LoginScreen().setVisible(true);
            dispose();
        }
    }

    private void carregarMovimentacoes() {
        tableModel.setRowCount(0);

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT id, descricao, valor, tipo, DATE_FORMAT(data_hora, '%d/%m/%Y %H:%i') " +
                 "FROM movimentacoes ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String valorFmt = String.format("R$ %.2f", rs.getDouble(3));
                tableModel.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    valorFmt,
                    rs.getString(4),
                    rs.getString(5)
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void atualizarSaldo() {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT " +
                 "COALESCE(SUM(CASE WHEN tipo='VENDA' THEN valor ELSE 0 END), 0) - " +
                 "COALESCE(SUM(CASE WHEN tipo='DESPESA' THEN valor ELSE 0 END), 0) " +
                 "FROM movimentacoes");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                double saldo = rs.getDouble(1);
                saldoLabel.setText(String.format("Saldo: R$ %.2f", saldo));
                saldoLabel.setForeground(saldo >= 0 ? COR_SALDO_POS : COR_SALDO_NEG);
            }

        } catch (SQLException e) {
            saldoLabel.setText("Saldo: erro");
        }
    }
}
