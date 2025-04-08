import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaFinanceira {

    private String nome;
    private static List<CategoriaFinanceira> categorias = new ArrayList<>();
    private static Path caminhoArquivo;





 public CategoriaFinanceira(String nome) {


        this.nome = nome;


    }

public String getNome() {


        return nome;


    }



 public void setNome(String nome) {


        this.nome = nome;


    }



 public static void inicializarParaUsuario(String emailUsuario) {


        caminhoArquivo = Paths.get("usuarios", emailUsuario, "categorias.txt");

        carregarArquivo();


    }



 public static List<CategoriaFinanceira> listar() {


        return new ArrayList<>(categorias);


 }





  public static void adicionar(String nome) {

     categorias.add(new CategoriaFinanceira(nome));
          salvarArquivo();




    }




public static void editar(int index, String novoNome) {


        if (index >= 0 && index < categorias.size()) {


            categorias.get(index).setNome(novoNome);

            salvarArquivo();


 }
    }





 public static void excluir(int index) {


  if (index >= 0 && index < categorias.size()) {


         categorias.remove(index);

            salvarArquivo();



     }
 }




  public static void carregarArquivo() {



    categorias.clear();
      if (caminhoArquivo == null || !Files.exists(caminhoArquivo)) return;



      try (BufferedReader leitor = Files.newBufferedReader(caminhoArquivo)) {


         String linha;
        while ((linha = leitor.readLine()) != null) {



       if (!linha.trim().isEmpty()) {


                    categorias.add(new CategoriaFinanceira(linha.trim()));



        }


 }



     } catch (IOException e) {



      e.printStackTrace();





   }
    }




    public static void salvarArquivo() {
     try {

        Files.createDirectories(caminhoArquivo.getParent());

            BufferedWriter escritor = Files.newBufferedWriter(caminhoArquivo);


       for (CategoriaFinanceira cat : categorias) {

            escritor.write(cat.getNome());

                escritor.newLine();



            }

        escritor.close();


        } catch (IOException e) {


            e.printStackTrace();



        }



    }
}
