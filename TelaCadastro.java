import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.util.regex.*;
import javax.swing.text.*;



// esse vai ser a tela em que o usuario vai fazer o cadastro, vou tentar colocar o campo email, senha,confirmarsenha,nome,data de nascimento, etc...



public class TelaCadastro extends JFrame {



  private JTextField campoNome, campoEmail;


    private JPasswordField campoSenha, campoConfirmarSenha;


 private JFormattedTextField campoTelefone, campoCpf, campoDataNascimento;


    private JButton botaoCadastrar, botaoLogin;


    private JLabel avisoSenha;


 public TelaCadastro() {


   setTitle("Cadastro");

        setSize(800, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

     setLayout(new GridBagLayout());

   getContentPane().setBackground(Color.WHITE);




   GridBagConstraints gbc = new GridBagConstraints();


    gbc.insets = new Insets(5, 10, 5, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonte = new Font("Arial", Font.BOLD, 16);


 JLabel labelEmail = new JLabel("Email:");

        campoEmail = new JTextField(25);

  JLabel labelSenha = new JLabel("Senha:");

        campoSenha = new JPasswordField(25);

     JLabel labelConfirmarSenha = new JLabel("Confirmar Senha:");


        campoConfirmarSenha = new JPasswordField(25);

     JLabel labelNome = new JLabel("Nome:");

        campoNome = new JTextField(25);

    JLabel labelTelefone = new JLabel("Telefone:");

        campoTelefone = new JFormattedTextField(createMaskFormatter("(##) #####-####"));
        JLabel labelCpf = new JLabel("CPF:");


    campoCpf = new JFormattedTextField(createMaskFormatter("###.###.###-##"));

        JLabel labelDataNascimento = new JLabel("Data de Nascimento:");


    campoDataNascimento = new JFormattedTextField(createMaskFormatter("##/##/####"));


        botaoCadastrar = new JButton("Cadastrar");

        botaoLogin = new JButton("Login");



        //  usuario errou a senha



  avisoSenha = new JLabel("");

        avisoSenha.setForeground(Color.RED);
        avisoSenha.setFont(new Font("Arial", Font.BOLD, 14));


configurarBotao(botaoCadastrar);
  configurarBotao(botaoLogin);


        // validacao de senha

        campoConfirmarSenha.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                verificarSenha();
            }
        });


        //

        gbc.gridx = 0;

        gbc.gridy = 0;

 add(labelEmail, gbc);

        gbc.gridx = 1;

        gbc.gridy = 0;

 add(campoEmail, gbc);

        gbc.gridx = 0;

        gbc.gridy = 1;

   add(labelSenha, gbc);

        gbc.gridx = 1;

        gbc.gridy = 1;

   add(campoSenha, gbc);

     gbc.gridx = 0;

   gbc.gridy = 2;

        add(labelConfirmarSenha, gbc);

        gbc.gridx = 1;

        gbc.gridy = 2;

 add(campoConfirmarSenha, gbc);

        gbc.gridx = 1;

        gbc.gridy = 3;

     add(avisoSenha, gbc);

        gbc.gridx = 0;

        gbc.gridy = 4;

        add(labelNome, gbc);

    gbc.gridx = 1;

        gbc.gridy = 4;

        add(campoNome, gbc);

    gbc.gridx = 0;

        gbc.gridy = 5;


   add(labelTelefone, gbc);

        gbc.gridx = 1;

        gbc.gridy = 5;

     add(campoTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;

    add(labelCpf, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;


    add(campoCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;

  add(labelDataNascimento, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(campoDataNascimento, gbc);
        gbc.gridx = 0;
   gbc.gridy = 8;
        add(botaoCadastrar, gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        add(botaoLogin, gbc);





    botaoCadastrar.addActionListener(e -> cadastrarUsuario());


     botaoLogin.addActionListener(e ->

        {



            new TelaLogin();
            dispose();


        });



 setVisible(true);


    }




  private void configurarBotao(JButton botao) {

        botao.setBackground(Color.BLACK);

   botao.setForeground(Color.WHITE);

   botao.setFocusPainted(false);

  botao.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        botao.setPreferredSize(new Dimension(180, 40));

    }






 private MaskFormatter createMaskFormatter(String format) {

        try {


 MaskFormatter formatter = new MaskFormatter(format);
    formatter.setValidCharacters("0123456789");


 return formatter;


     } catch (ParseException e) {

            e.printStackTrace();

 return null;
        }
    }







 private void verificarSenha() {

   String senha = new String(campoSenha.getPassword());
        String confirmarSenha = new String(campoConfirmarSenha.getPassword());


  if (!senha.equals(confirmarSenha)) {
            avisoSenha.setText("⚠ As senhas não são iguais!");

  } else {

            avisoSenha.setText(""); // Apaga a mensagem se as senhas forem iguais
        }
    }


    private void cadastrarUsuario() {
   String email = campoEmail.getText();

        String senha = new String(campoSenha.getPassword());

  String confirmarSenha = new String(campoConfirmarSenha.getPassword());

        String nome = campoNome.getText();

        String telefone = campoTelefone.getText();

        String cpf = campoCpf.getText();

        String dataNascimento = campoDataNascimento.getText();


        if (!validarEmail(email)) {

    JOptionPane.showMessageDialog(null, "Por favor, insira um email válido.", "Erro", JOptionPane.ERROR_MESSAGE);


            return;
        }


      if (!senha.equals(confirmarSenha)) {


          JOptionPane.showMessageDialog(null, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);


            return;
        }


        if (usuarioJaExiste(email)) {
            JOptionPane.showMessageDialog(null, "Esse usuário já está cadastrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        salvarCadastro(email, senha, nome, telefone, cpf, dataNascimento);



        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");


        new TelaLogin();


        dispose();
    }






    private boolean validarEmail(String email) {



   String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

   Pattern pattern = Pattern.compile(regex);

 Matcher matcher = pattern.matcher(email);


 return matcher.matches();



    }




    private boolean usuarioJaExiste(String email) {

     String pastaUsuario = "usuarios/" + email.replace("@", "_at_").replace(".", "_");

      File pasta = new File(pastaUsuario);


  return pasta.exists();        // se o usuario ja for cadastrado
    }




 private void salvarCadastro(String email, String senha, String nome, String telefone, String cpf, String dataNascimento) {
     String dados = email + ";" + senha + ";" + nome + ";" + telefone + ";" + cpf + ";" + dataNascimento;





   try (BufferedWriter writer = new BufferedWriter(new FileWriter("cadastros.txt", true))) {


  writer.write(dados);

    writer.newLine();

    } catch (IOException e) {


  JOptionPane.showMessageDialog(null, "Erro ao salvar o cadastro geral.", "Erro", JOptionPane.ERROR_MESSAGE);



        }


        criarPastaDoUsuario(email, dados);
    }






private void criarPastaDoUsuario(String email, String conteudoCadastro) {


    String pastaUsuario = "usuarios/" + email.replace("@", "_at_").replace(".", "_");
    File pasta = new File(pastaUsuario);



    if (!pasta.exists()) {


    if (pasta.mkdirs()) {


    try (BufferedWriter writer = new BufferedWriter(new FileWriter(pastaUsuario + "/cadastro.txt"))) {

     writer.write(conteudoCadastro);

      writer.newLine();


       } catch (IOException e) {


       JOptionPane.showMessageDialog(null, "Erro ao salvar dados do usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
 }


  } else {

  JOptionPane.showMessageDialog(null, "Erro ao criar pasta do usuário.", "Erro", JOptionPane.ERROR_MESSAGE);


    }



} else {
  JOptionPane.showMessageDialog(null, "Usuário já cadastrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}




public static void main(String[] args) {



        new TelaCadastro();


    }







}

