import java.io.*;
import java.nio.file.*;
import java.util.*;




public class Financeiro {


  private String emailUsuario;

    private Path caminhoArquivo;

    private List<Transacao> transacoes;



    public Financeiro(String emailUsuario) {



    this.emailUsuario = emailUsuario;
       this.caminhoArquivo = Paths.get("usuarios", emailUsuario, "financas.txt");
        this.transacoes = new ArrayList<>();
        carregarTransacoes();



    }



 public void adicionarTransacao(Transacao transacao) {

    transacoes.add(transacao);

        salvarTransacoes();



    }




   private void salvarTransacoes() {


      try {

          Files.createDirectories(caminhoArquivo.getParent());

            BufferedWriter writer = Files.newBufferedWriter(caminhoArquivo);

      for (Transacao t : transacoes) {


            writer.write(t.toString());
                writer.newLine();


            }


            writer.close();


      } catch (IOException e) {


            e.printStackTrace();


        }

    }

    private void carregarTransacoes() {

        try {


            if (!Files.exists(caminhoArquivo)) return;



            BufferedReader reader = Files.newBufferedReader(caminhoArquivo);

            String linha;



     while ((linha = reader.readLine()) != null) {


        String[] dados = linha.split(",");


          if (dados.length == 5) {


              String tipo = dados[0].trim();

               String categoria = dados[1].trim();

                String descricao = dados[2].trim();

                  double valor = Double.parseDouble(dados[3].trim().replace(",", "."));

                 String data = dados[4].trim();




             transacoes.add(new Transacao(tipo, categoria, descricao, valor, data));


           }


            }


            reader.close();


     } catch (IOException | NumberFormatException e) {


            e.printStackTrace();


   }
 }
}