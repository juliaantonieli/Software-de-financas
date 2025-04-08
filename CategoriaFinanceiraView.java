import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import java.awt.*;




public class CategoriaFinanceiraView extends JFrame {
private JTextField txtNome;
 private JButton btnAdicionar, btnExcluir;
 private JTable table;
   private DefaultTableModel model;




 public CategoriaFinanceiraView() {



  UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));

  UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));


  UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14));


  UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 14));



  UIManager.put("TableHeader.font", new Font("Arial", Font.BOLD, 14));





  setTitle("Categorias Financeiras");

   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    setSize(650, 300);

    setLocationRelativeTo(null);

 setLayout(new BorderLayout());



JPanel panelTop = new JPanel();

  panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));


  JLabel titulo = new JLabel("Gerenciar Categoria");

   titulo.setFont(new Font("Arial", Font.BOLD, 16));

   titulo.setAlignmentX (Component.CENTER_ALIGNMENT);

   titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


  panelTop.add(titulo);



JPanel linhaFormulario = new JPanel();

    linhaFormulario.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));


    txtNome = new JTextField(20);
    btnAdicionar = new JButton("Adicionar");
     btnExcluir = new JButton("Deletar");


     estilizarBotao(btnAdicionar, false);
    estilizarBotao(btnExcluir, true);

     linhaFormulario.add(new JLabel("Nome:"));
     linhaFormulario.add(txtNome);
      linhaFormulario.add(btnAdicionar);
      linhaFormulario.add(btnExcluir);

        panelTop.add(linhaFormulario);
        add(panelTop, BorderLayout.NORTH);


        model = new DefaultTableModel(new String[]{"Categoria"}, 0);
        table = new JTable(model);
        table.setRowHeight(25);




        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);


        btnAdicionar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            if (!nome.isEmpty()) {
              CategoriaFinanceira.adicionar(nome);
             carregarTabela();
              txtNome.setText("");
              JOptionPane.showMessageDialog(this, "Salvo com sucesso!");

            } else {
            JOptionPane.showMessageDialog(this, "Nome não pode ser vazio.");
            }
            });

        btnExcluir.addActionListener(e -> {
         int linha = table.getSelectedRow();
         if (linha >= 0) {
              CategoriaFinanceira.excluir(linha);
              carregarTabela();
              JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
            } else {
              JOptionPane.showMessageDialog(this, "Selecione uma categoria.");
            }
            });





    model.addTableModelListener(e -> {
      if (e.getType() == TableModelEvent.UPDATE) {
          int row = e.getFirstRow();
                 String novoNome = (String) model.getValueAt(row, 0);
                 CategoriaFinanceira.editar(row, novoNome);
     JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
            }
    });


        carregarTabela();
    }

    private void estilizarBotao(JButton botao, boolean isDelete) {
        Color corPadrao = isDelete ? new Color(0, 0, 0) : new Color(0, 0, 0);
        Color corHover = isDelete ? new Color(0, 0, 0) : new Color(0, 0, 0);
botao.setBackground(corPadrao);
        botao.setForeground(Color.WHITE);
        botao.setPreferredSize(new Dimension(100, 30));


    }

    private void carregarTabela() {
    model.setRowCount(0);
     for (CategoriaFinanceira cat : CategoriaFinanceira.listar()) {
         model.addRow(new Object[]{cat.getNome()});
        }
        }


}
