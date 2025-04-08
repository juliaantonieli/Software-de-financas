import javax.swing.*;
import java.awt.*;

public class telaInicial extends JFrame {

   public telaInicial() {

 setTitle("Tela");

     setSize(800, 600);

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

     setLocationRelativeTo(null);



     // Layout centro da tela
   setLayout(new GridBagLayout());

       // Fundo branco
        getContentPane().setBackground(Color.WHITE);

        //Organizando os botões na vertical


        JPanel painelBotoes = new JPanel();


        painelBotoes.setLayout(new GridLayout(2, 1, 10, 10));
        // Espacamento ntre botoes

       //colocar um fundo branquinho

        painelBotoes.setBackground(Color.WHITE);


      JButton botaoLogin = new JButton("Login");

     JButton botaoCadastro = new JButton("Cadastrar-se");


     // Estilização dos botões
       configurarBotao(botaoLogin);
        configurarBotao(botaoCadastro);



   botaoLogin.addActionListener(e -> {
   new TelaLogin();
            dispose();
        });


 botaoCadastro.addActionListener(e -> {
       new TelaCadastro();
            dispose();


        });

  painelBotoes.add(botaoLogin);
  painelBotoes.add(botaoCadastro);


        // tentar centralizar mais um pouquinho o painel ao centro


        add(painelBotoes);


      setVisible(true);
    }

    private void configurarBotao(JButton botao) {



    botao.setFont(new Font("Arial", Font.BOLD, 16));

   botao.setBackground(Color.BLACK);

     botao.setForeground(Color.WHITE);

     botao.setFocusPainted(false);
        botao.setBorderPainted(false);

        botao.setPreferredSize(new Dimension(150, 40)); // Botões menores
    }




 public static void main(String[] args) {

        new telaInicial();




    }







}
