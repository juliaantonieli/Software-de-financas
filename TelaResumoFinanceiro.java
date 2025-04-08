import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;



public class TelaResumoFinanceiro extends JFrame {


    private JTextField campoDataInicio;

    private JTextField campoDataFim;

    private JLabel labelReceitas;

    private JLabel labelDespesas;

    private JLabel labelSaldo;

    private String emailUsuario;


    //




 public TelaResumoFinanceiro(String emailUsuario) {


    this.emailUsuario = emailUsuario;


        setTitle("Resumo Financeiro");

        setSize(800, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));


        getContentPane().setBackground(Color.WHITE);


JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelFiltros.setBackground(Color.WHITE);


     campoDataInicio = new JTextField(8);
      campoDataFim = new JTextField(8);



    painelFiltros.add(new JLabel("Data Início (dd/MM/yyyy):"));
   painelFiltros.add(campoDataInicio);
    painelFiltros.add(new JLabel("Data Fim (dd/MM/yyyy):"));
     painelFiltros.add(campoDataFim);




    JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(Color.BLACK);



        btnFiltrar.setForeground(Color.WHITE);

        btnFiltrar.addActionListener(e -> atualizarResumo());

        painelFiltros.add(btnFiltrar);




  JPanel painelResultados = new JPanel(new GridLayout(3, 1, 10, 10));
        painelResultados.setBackground(Color.WHITE);



   labelReceitas = new JLabel("Total de Receitas: R$ 0,00", SwingConstants.CENTER);
  labelDespesas = new JLabel("Total de Despesas: R$ 0,00", SwingConstants.CENTER);
    labelSaldo = new JLabel("Saldo Final: R$ 0,00", SwingConstants.CENTER);



 painelResultados.add(labelReceitas);

        painelResultados.add(labelDespesas);

        painelResultados.add(labelSaldo);




        add(painelFiltros, BorderLayout.NORTH);
        add(painelResultados, BorderLayout.CENTER);




        setVisible(true);
        SwingUtilities.invokeLater(() -> atualizarResumo());




 }





private void atualizarResumo() {

     double totalReceitas = 0.0;

     double totalDespesas = 0.0;

     String dataInicio = campoDataInicio.getText().trim();
     String dataFim = campoDataFim.getText().trim();




Path caminho = Paths.get("usuarios", emailUsuario, "financas.txt");
if (Files.exists(caminho)) {


    try {
        List<String> linhas = Files.readAllLines(caminho);

 for (String linha : linhas) {
            String[] partes = linha.split(",");


   if (partes.length == 5) {


       String tipo = partes[0].trim().toLowerCase();

       String categoria = partes[1].trim();

       String descricao = partes[2].trim();

       double valor = Double.parseDouble(partes[3].trim().replace(",", "."));

       String data = partes[4].trim();


       boolean dentroDoPeriodo = true;

       if (!dataInicio.isEmpty() && data.compareTo(dataInicio) < 0) dentroDoPeriodo = false;

             if (!dataFim.isEmpty() && data.compareTo(dataFim) > 0) dentroDoPeriodo = false;


          if (dentroDoPeriodo) {

              if (tipo.equals("receita")) totalReceitas += valor;


              else if (tipo.equals("despesa")) totalDespesas += valor;


          }
   }
 }


    } catch (IOException | NumberFormatException e) {


        JOptionPane.showMessageDialog(this, "Erro ao ler transações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }


}

   double saldo = totalReceitas - totalDespesas;
      labelReceitas.setText("Total de Receitas: R$ " + String.format("%.2f", totalReceitas));

        labelDespesas.setText("Total de Despesas: R$ " + String.format("%.2f", totalDespesas));

        labelSaldo.setText("Saldo Final: R$ " + String.format("%.2f", saldo));




 }
}