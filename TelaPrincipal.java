import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;






public class TelaPrincipal extends JFrame {



 private JLabel displayValor;

private double valorDisponivel = 0.0;

  private DefaultTableModel tabelaModel;


    private String emailUsuario;




 public TelaPrincipal(String emailUsuario) {


   this.emailUsuario = emailUsuario;


   CategoriaFinanceira.inicializarParaUsuario(emailUsuario);




     setTitle("Bem Vindo ao Sistema");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        setSize(900, 650);
        setLocationRelativeTo(null);



   getContentPane().setBackground(Color.WHITE);


        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"Valor (R$)", "Tipo", "Categoria", "Descrição", "Data"};



        tabelaModel = new DefaultTableModel(colunas, 0) {


            @Override
            public boolean isCellEditable(int row, int column) {
                return false;



   }
        };






     JTable tabela = new JTable(tabelaModel);



    tabela.setRowHeight(30);
     tabela.setFillsViewportHeight(true);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));




  JScrollPane scrollPane = new JScrollPane(tabela);

    scrollPane.setPreferredSize(new Dimension(700, 200));

        scrollPane.setBorder(BorderFactory.createTitledBorder(


          BorderFactory.createLineBorder(Color.GRAY, 1),



          "Movimentações recentes",

           TitledBorder.LEFT,

            TitledBorder.TOP,


           new Font("Arial", Font.BOLD, 14)



        ));







    JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBackground(Color.WHITE);
        painelCentral.add(scrollPane);



     JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setBackground(Color.WHITE);



    JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        painelBotoes.setBackground(Color.WHITE);




  JButton btnVerHistorico = criarBotao("Histórico Financeiro");
        btnVerHistorico.addActionListener(e -> new TelaVerHistorico(emailUsuario));



  JButton btnGerenciar = criarBotao("Gerenciador Financeiro");
        btnGerenciar.addActionListener(e -> new Interface(this, emailUsuario));



   JButton btnResumoFinanceiro = criarBotao("Resumo Financeiro");
        btnResumoFinanceiro.addActionListener(e -> new TelaResumoFinanceiro(emailUsuario));




    painelBotoes.add(btnVerHistorico);
   painelBotoes.add(btnGerenciar);
    painelBotoes.add(btnResumoFinanceiro);






     JPanel painelDisplay = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelDisplay.setBackground(Color.WHITE);




        displayValor = new JLabel("R$ 0,00");
        displayValor.setFont(new Font("Arial", Font.BOLD, 20));
        displayValor.setForeground(Color.BLACK);



     JPanel caixaDisplay = new JPanel();
        caixaDisplay.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        caixaDisplay.setPreferredSize(new Dimension(180, 45));

        caixaDisplay.setBackground(Color.WHITE);

        caixaDisplay.add(displayValor);



    painelDisplay.add(caixaDisplay);

     painelInferior.add(painelBotoes, BorderLayout.WEST);

     painelInferior.add(painelDisplay, BorderLayout.EAST);





     JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(Color.WHITE);





  JButton btnSair = new JButton("Sair");


   btnSair.setFont(new Font("Arial", Font.BOLD, 14));

     btnSair.setBackground(Color.BLACK);

      btnSair.setForeground(Color.WHITE);

     btnSair.setFocusPainted(false);

     btnSair.setPreferredSize(new Dimension(80, 30));


     btnSair.addActionListener(e -> {


   int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Confirmar saída", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {

      for (Window janela : Window.getWindows()) {


            if (janela instanceof JFrame) {


                        janela.dispose();



                    }
                }


        new telaInicial();



  }
 });




 JPanel painelDireitaTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelDireitaTopo.setBackground(Color.WHITE);
        painelDireitaTopo.add(btnSair);





  JLabel labelUsuario = new JLabel("Usuário: " + emailUsuario);

        labelUsuario.setFont(new Font("Arial", Font.PLAIN, 14));

        labelUsuario.setForeground(Color.DARK_GRAY);


        labelUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



    painelTopo.add(labelUsuario, BorderLayout.WEST);

     painelTopo.add(painelDireitaTopo, BorderLayout.EAST);

     add(painelTopo, BorderLayout.NORTH);

   add(painelCentral, BorderLayout.CENTER);

    add(painelInferior, BorderLayout.SOUTH);



     atualizarTabelaUltimosRegistros();
        carregarSaldoEDisplay();
        setVisible(true);



    }







private void carregarSaldoEDisplay() {


        double receitas = 0.0;

        double despesas = 0.0;

        Path caminho = Paths.get("usuarios", emailUsuario, "financas.txt");



   if (Files.exists(caminho)) {

     try {


        List<String> linhas = Files.readAllLines(caminho);


        for (String linha : linhas) {


         String[] partes = linha.split(",");


         if (partes.length == 5) {


         String tipo = partes[0].trim().toLowerCase();

           double valor = Double.parseDouble(partes[3].trim().replace(",", "."));

            if (tipo.equals("receita")) {


             receitas += valor;


              } else if (tipo.equals("despesa")) {
                            despesas += valor;


  }
          }
                }



} catch (IOException | NumberFormatException e) {


                System.out.println("Erro ao carregar transações: " + e.getMessage());



            }
        }



        this.valorDisponivel = receitas - despesas;
        atualizarCorDisplay();
    }






 private JButton criarBotao(String texto) {


        JButton botao = new JButton(texto);

        botao.setFont(new Font("Arial", Font.BOLD, 14));


        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);



   return botao;




 }






public void atualizarValorDisplay(double valor) {


        this.valorDisponivel += valor;
        atualizarCorDisplay();



    }




 public void registrarGasto(double valor, String categoria, String descricao, String data) {

        tabelaModel.insertRow(0, new Object[]{

                String.format("R$ %.2f", valor), categoria, descricao, data


        });


        this.valorDisponivel -= valor;
        atualizarCorDisplay();



        if (tabelaModel.getRowCount() > 5) {
            tabelaModel.removeRow(5);



    }
   }




 private void atualizarCorDisplay() {


    if (valorDisponivel < 0) {


            displayValor.setForeground(Color.RED);



     } else {


   displayValor.setForeground(Color.BLACK);




        }



        displayValor.setText(String.format("R$ %.2f", valorDisponivel));





    }




 public void recarregarSaldoEDisplay() {


  carregarSaldoEDisplay();



    }




public void atualizarTabelaUltimosRegistros() {



   try {

       Path caminho = Paths.get("usuarios", emailUsuario, "financas.txt");


   if (Files.exists(caminho)) {

       List<String> linhas = Files.readAllLines(caminho);

       tabelaModel.setRowCount(0);


       int inicio = Math.max(0, linhas.size() - 5);


       for (int i = linhas.size() - 1; i >= inicio; i--) {

           String[] partes = linhas.get(i).split(",");


           if (partes.length == 5) {

               String tipo = partes[0].trim();

               String categoria = partes[1].trim();


               String descricao = partes[2].trim();

               String valor = String.format("R$ %.2f", Double.parseDouble(partes[3].trim().replace(",", ".")));
               String data = partes[4].trim();



               tabelaModel.addRow(new String[]{valor, tipo, categoria, descricao, data});
           }

       }
   }


   } catch (IOException e) {
            System.out.println("Erro ao ler transações: " + e.getMessage());




   }
 }


}
