import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;




public class TelaVerHistorico extends JFrame {




    private JLabel labelTotal;

    private DefaultTableModel tabelaModel;

    private String emailUsuario;


    private JTextField campoData;

    private JComboBox<String> comboCategoria;

    private JComboBox<String> comboTipo;




 public TelaVerHistorico(String emailUsuario) {

        this.emailUsuario = emailUsuario;



   setTitle("Histórico de Finanças");
   setSize(700, 450);


   setLocationRelativeTo(null);
   setLayout(new BorderLayout());



  JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFiltros.setBackground(Color.WHITE);


        campoData = new JTextField(10);

        comboCategoria = new JComboBox<>();

        carregarCategorias();


        comboTipo = new JComboBox<>(new String[]{"Todos", "Receita", "Despesa"});



   JButton btnFiltrar = new JButton("Filtrar");

        btnFiltrar.setBackground(Color.BLACK);

        btnFiltrar.setForeground(Color.WHITE);

        btnFiltrar.addActionListener(e -> carregarHistorico());




   painelFiltros.add(new JLabel("Data:"));
   painelFiltros.add(campoData);


   painelFiltros.add(new JLabel("Categoria:"));
   painelFiltros.add(comboCategoria);


   painelFiltros.add(new JLabel("Classificação:"));
   painelFiltros.add(comboTipo);

   painelFiltros.add(btnFiltrar);



        add(painelFiltros, BorderLayout.NORTH);




String[] colunas = {"Valor (R$)", "Tipo", "Categoria", "Descrição", "Data"};


   tabelaModel = new DefaultTableModel(colunas, 0) {


       @Override
            public boolean isCellEditable(int row, int column) {


                return false;

       }






   };

 JTable tabela = new JTable(tabelaModel);



 JScrollPane scrollPane = new JScrollPane(tabela);
 scrollPane.setBorder(new TitledBorder("Últimas Ações"));
 add(scrollPane, BorderLayout.CENTER);




 labelTotal = new JLabel("Total filtrado: R$ 0,00", SwingConstants.RIGHT);

 labelTotal.setFont(new Font("Arial", Font.BOLD, 14));

 labelTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

 add(labelTotal, BorderLayout.SOUTH);




        carregarHistorico();

        setVisible(true);



    }




 private void carregarCategorias() {


 comboCategoria.removeAllItems();


  comboCategoria.addItem("Todas");


   for (CategoriaFinanceira cat : CategoriaFinanceira.listar()) {
            comboCategoria.addItem(cat.getNome());



   }
 }





 private void carregarHistorico() {
        try {


        tabelaModel.setRowCount(0);




    Path caminho = Paths.get("usuarios", emailUsuario, "financas.txt");

    if (Files.exists(caminho)) {

        List<String> linhas = Files.readAllLines(caminho);



    for (String linha : linhas) {

        String[] partes = linha.split(",");


       if (partes.length == 5) {
    String tipo = partes[0].trim();
    String categoria = partes[1].trim();


       String descricao = partes[2].trim();
       String valor = partes[3].trim().replace(",", ".");
       String data = partes[4].trim();




       boolean passaData = campoData.getText().isEmpty() || data.equals(campoData.getText().trim());

       boolean passaTipo = comboTipo.getSelectedItem().equals("Todos") ||

               comboTipo.getSelectedItem().toString().equalsIgnoreCase(tipo);

       boolean passaCategoria = comboCategoria.getSelectedItem().equals("Todas") ||



               comboCategoria.getSelectedItem().toString().equalsIgnoreCase(categoria);



          if (passaData && passaTipo && passaCategoria) {

              String valorFormatado = String.format("R$ %.2f", Double.parseDouble(valor));

              String tipoFormatado = tipo.substring(0, 1).toUpperCase() + tipo.substring(1);

              tabelaModel.addRow(new String[]{valorFormatado, tipoFormatado, categoria, descricao, data});



          }
       }

    }
    } else {


        JOptionPane.showMessageDialog(this, "Nenhum histórico encontrado para este usuário.", "Aviso", JOptionPane.INFORMATION_MESSAGE);

    }

    atualizarTotalTabela();



 } catch (IOException e) {


            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);


 }
 }




 private void atualizarTotalTabela() {
        double total = 0.0;


     for (int i = 0; i < tabelaModel.getRowCount(); i++) {
         String valorTexto = tabelaModel.getValueAt(i, 0).toString()


           .replace("R$", "")

             .replace(",", ".")

             .trim();



   try {

   total += Double.parseDouble(valorTexto);


  } catch (NumberFormatException e) {


       System.out.println("Valor inválido na linha " + i + ": " + valorTexto);


   }
     }

     labelTotal.setText(String.format("Total filtrado: R$ %.2f", total));


    }




}
