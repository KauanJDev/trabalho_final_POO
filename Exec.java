import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Exec extends JFrame{
   public Exec(String titulo)  {
      super(titulo);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(300, 200);
       
      mudarPanel(telaInicial());

      setVisible(true);
   }
   
   public void mudarPanel(JPanel panel)  {
      getContentPane().removeAll();
      setContentPane(panel);
      revalidate();
      repaint();
   }
   
   public JPanel telaInicial() {
      JPanel panel = new JPanel();
      GameListener listener = new GameListener(this);

      GameButton personagem = new GameButton("Criar Personagem", 1);
      personagem.addActionListener(listener);
      panel.add(personagem);

      GameButton campanha = new GameButton("Jogar Campanha", 2);
      campanha.addActionListener(listener);
      panel.add(campanha);

      GameButton salvar = new GameButton("Salvar Jogo", 3);
      salvar.addActionListener(listener);
      panel.add(salvar);

      GameButton carregar = new GameButton("Carregar Jogo", 4);
      carregar.addActionListener(listener);
      panel.add(carregar);

      return panel;
   }

   public ArrayList<String[]> lerClasses(String arquivo) {
    ArrayList<String[]> classes = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            classes.add(linha.split(";"));
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Erro ao ler classes.txt");
    }

    return classes;
}


   public JPanel telaPersonagem() {
    JPanel panel = new JPanel(new BorderLayout());

    JPanel topo = new JPanel(new GridLayout(2, 1));
    JLabel lblNome = new JLabel("Nome do personagem:");
    JTextField txtNome = new JTextField();

    topo.add(lblNome);
    topo.add(txtNome);

    panel.add(topo, BorderLayout.NORTH);

    JPanel centro = new JPanel(new GridLayout(0, 1));
    ButtonGroup grupo = new ButtonGroup();

    ArrayList<String[]> classes = lerClasses("classes.txt");

    for (String[] c : classes) {
        int id = Integer.parseInt(c[0]);
        String nomeClasse = c[1];

        JRadioButton radio = new JRadioButton(nomeClasse);
        radio.setActionCommand(String.valueOf(id));

        grupo.add(radio);
        centro.add(radio);
    }

    panel.add(centro, BorderLayout.CENTER);

    JButton criar = new JButton("Criar Personagem");

    criar.addActionListener(e -> {
        String nome = txtNome.getText();

        if (nome.isEmpty() || grupo.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Preencha nome e classe");
            return;
        }

        int classeId = Integer.parseInt(
            grupo.getSelection().getActionCommand()
        );

        Jogador jogador = criarJogador(nome, classeId);

        JOptionPane.showMessageDialog(
            this,
            "Personagem criado: " + jogador.nome +
            "\nClasse: " + jogador.getClass().getSimpleName()
        );

        mudarPanel(telaInicial());
    });

    panel.add(criar, BorderLayout.SOUTH);

    return panel;
}

   public void salvarJogo(Jogador jogador) {

    if (jogador == null) {
        JOptionPane.showMessageDialog(this, "Nenhum jogador para salvar");
        return;
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("save.txt"))) {

        bw.write("Classe=" + jogador.getClass().getSimpleName());
        bw.newLine();

        bw.write("Nome=" + jogador.nome);
        bw.newLine();

        bw.write("Nivel=" + jogador.getNivel());
        bw.newLine();

        bw.write("Experiencia=" + jogador.getExperiencia());
        bw.newLine();

        bw.write("VidaAtual=" + jogador.vidaAtual);
        bw.newLine();

        bw.write("VidaMaxima=" + jogador.vidaMaxima);
        bw.newLine();

        bw.write("Velocidade=" + jogador.velocidade);
        bw.newLine();

        bw.write("Ataque=" + jogador.ataque);
        bw.newLine();

        bw.write("Defesa=" + jogador.defesa);
        bw.newLine();

        bw.write("Armadura=" + jogador.armadura);
        bw.newLine();

        // Atributos específicos
        if (jogador instanceof Guerreiro) {
            Guerreiro g = (Guerreiro) jogador;
            bw.write("Extra=Stamina:" + g.stamina);
        } 
        else if (jogador instanceof Clerigo) {
            Clerigo c = (Clerigo) jogador;
            bw.write("Extra=Fe:" + c.fe);
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar jogo");
    }
}



   public Jogador criarJogador(String nome, int classe)  { 
      switch (classe) {
         case 1:
            return new Guerreiro(nome);
         case 2:
            return new Mago(nome);
         case 3:
            return new Ladino(nome);
         case 4:
            return new Clerigo(nome);
         default:
            return new Guerreiro(nome);
      }
   }

   public static void main(String[] args) {
      new Exec("Jogo Divertido");
   }
}
