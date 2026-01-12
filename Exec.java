import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Exec extends JFrame {
    public Jogador jogadorAtual;
    private ArrayList<Encontro> campanha;
    private int encontroAtual;
    private Encontro encontroEmAndamento;

    public Exec(String titulo) {
        super(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        mudarPanel(telaInicial());
        setVisible(true);
    }

    public void mudarPanel(JPanel panel) {
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

            jogadorAtual = criarJogador(nome, classeId);

            JOptionPane.showMessageDialog(
                this,
                "Personagem criado: " + jogadorAtual.nome +
                "\nClasse: " + jogadorAtual.getClass().getSimpleName()
            );

            mudarPanel(telaInicial());
        });

        panel.add(criar, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel telaDeCombate(Encontro encontro) {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea log = new JTextArea(10, 30);
        log.setEditable(false);
        JScrollPane scroll = new JScrollPane(log);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel();

        JButton acao1 = new JButton("Agredir");
        acao1.addActionListener(e -> {
            String resultado = encontro.passarTurno(1);
            log.append(resultado + "\n");
            if (encontro.jogador.morrer() || encontro.inimigo.morrer()) {
                resultado = encontro.encerrarEncontro();
                log.append(resultado + "\n");
            }
            verificarFimEncontro();
        });
        botoes.add(acao1);

        JButton acao2;
        JButton acao3;

        switch (jogadorAtual.classe) {
            case 1:
                acao2 = new JButton("Ataque Especial");
                acao3 = new JButton("Descansar");
                break;
            case 2:
                acao2 = new JButton("Bola de Fogo");
                acao3 = new JButton("Armadura Arcana");
                break;
            case 3:
                acao2 = new JButton("Sabotar");
                acao3 = new JButton("Esquivar");
                break;
            case 4:
                acao2 = new JButton("Luz Divina");
                acao3 = new JButton("Rezar");
                break;
            default:
                acao2 = new JButton("Ataque Especial");
                acao3 = new JButton("Descansar");
        }

        acao2.addActionListener(e -> {
            String resultado = encontro.passarTurno(2);
            log.append(resultado + "\n");
            if (encontro.jogador.morrer() || encontro.inimigo.morrer()) {
                resultado = encontro.encerrarEncontro();
                log.append(resultado + "\n");
            }
            verificarFimEncontro();
        });

        acao3.addActionListener(e -> {
            String resultado = encontro.passarTurno(3);
            log.append(resultado + "\n");
            if (encontro.jogador.morrer() || encontro.inimigo.morrer()) {
                resultado = encontro.encerrarEncontro();
                log.append(resultado + "\n");
            }
            verificarFimEncontro();
        });

        botoes.add(acao2);
        botoes.add(acao3);

        JButton acao4 = new JButton("Usar Item");
        acao4.addActionListener(e -> {
            log.append("Item usado!\n");
        });
        botoes.add(acao4);

        JButton acao5 = new JButton("Voltar para tela inicial");
        acao5.addActionListener(e -> {
            mudarPanel(telaInicial());
        });
        botoes.add(acao5);

        panel.add(botoes, BorderLayout.SOUTH);

        return panel;
    }

    private Encontro criarEncontro(Inimigo inimigo, int xp) {
        Encontro e = new Encontro();
        e.jogador = jogadorAtual;
        e.inimigo = inimigo;
        e.expAdquirida = xp;

        e.inimigoAcoes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            e.inimigoAcoes.add(1);
        }
        e.iniciarEncontro();
        return e;
    }

    private void iniciarProximoEncontro() {
        if (encontroAtual >= campanha.size()) {
            JOptionPane.showMessageDialog(this, "Campanha concluída!");
            mudarPanel(telaInicial());
            return;
        }

        encontroEmAndamento = campanha.get(encontroAtual);
        mudarPanel(telaDeCombate(encontroEmAndamento));
    }

    private void verificarFimEncontro() {
        if (encontroEmAndamento.jogador.morrer()) {
            JOptionPane.showMessageDialog(this, "Você perdeu!");
            mudarPanel(telaInicial());
            return;
        }

        if (encontroEmAndamento.inimigo.morrer()) {
            Timer timer = new Timer(3000, e -> {
                encontroAtual++;
                iniciarProximoEncontro();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void iniciarCampanha() {
        campanha = new ArrayList<>();
        encontroAtual = 0;

        campanha.add(criarEncontro(new Inimigo("Goblin", 30, 2, 5, 2, 0), 500));
        campanha.add(criarEncontro(new Inimigo("Goblin", 30, 2, 5, 2, 0), 50));
        campanha.add(criarEncontro(new Inimigo("Goblin", 30, 2, 5, 2, 0), 50));
        campanha.add(criarEncontro(new Inimigo("Goblin", 30, 2, 5, 2, 0), 50));
        campanha.add(criarEncontro(new Inimigo("Orc", 60, 1, 8, 4, 3), 80));
        campanha.add(criarEncontro(new Inimigo("Dragão", 150, 2, 15, 8, 10), 200));

        iniciarProximoEncontro();
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

            bw.write(jogador.salvarExtra());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar jogo");
        }
    }

    private Jogador carregarJogoDeArquivo(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String nome = null;
            String classeNome = null;
            int vidaAtual = 0;
            int experiencia = 0;

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("=");

                if (partes.length < 2) continue;

                switch (partes[0]) {
                    case "Nome":
                        nome = partes[1];
                        break;
                    case "Classe":
                        classeNome = partes[1];
                        break;
                    case "VidaAtual":
                        vidaAtual = Integer.parseInt(partes[1]);
                        break;
                    case "Experiencia":
                        experiencia = Integer.parseInt(partes[1]);
                        break;
                }
            }

            if (nome == null || classeNome == null) return null;

            Jogador j;

            switch (classeNome) {
                case "Guerreiro":
                    j = new Guerreiro(nome);
                    break;
                case "Mago":
                    j = new Mago(nome);
                    break;
                case "Ladino":
                    j = new Ladino(nome);
                    break;
                case "Clerigo":
                    j = new Clerigo(nome);
                    break;
                default:
                    return null;
            }

            j.vidaAtual = vidaAtual;
            j.ganharExperiencia(experiencia);

            return j;
        } catch (Exception e) {
            return null;
        }
    }

    public void carregarJogo() {
        jogadorAtual = carregarJogoDeArquivo("save.txt");

        if (jogadorAtual == null) {
            JOptionPane.showMessageDialog(this, "Falha ao carregar jogo");
            return;
        }

        JOptionPane.showMessageDialog(
            this,
            "Jogo carregado!\n" +
            "Nome: " + jogadorAtual.nome + "\n" +
            "Classe: " + jogadorAtual.getClass().getSimpleName()
        );
    }

    public Jogador criarJogador(String nome, int classe) {
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
