import java.util.ArrayList;
public class Encontro {
   public Jogador jogador;
   public Inimigo inimigo;
   public ArrayList<Item> loot;
   public int expAdquirida;
   public int turnoAtual;
   public boolean jogadorComeca;
   public ArrayList<Integer> inimigoAcoes;
   private int contadorAcoes;

   private String escolherAcao(Entidade e, int opc) {
      return e.executarAcao(opc, e == jogador ? inimigo : jogador);
   }

   public void iniciarEncontro() {
      jogador.vidaAtual = jogador.vidaMaxima;
      turnoAtual = 1;
      jogadorComeca = jogador.velocidade >= inimigo.velocidade;
   }

   public String mensagemInicio() {
      return "Apareceu um " + inimigo.nome + "!";
   }

   public String passarTurno(int opc) {
      StringBuilder resultado = new StringBuilder();

      if (jogadorComeca) {
         resultado.append(escolherAcao(jogador, opc)).append("\n");

         resultado.append(
            escolherAcao(inimigo, inimigoAcoes.get(contadorAcoes))
         ).append("\n");

      } else {
         resultado.append(
            escolherAcao(inimigo, inimigoAcoes.get(contadorAcoes))
         ).append("\n");

         if (jogador.morrer()) {
            resultado.append(jogador.nome).append(" morreu!\n");
            resultado.append(encerrarEncontro());
            return resultado.toString();
         }

         resultado.append(escolherAcao(jogador, opc)).append("\n");
      }

      resultado.append(String.format(
         "Sua Vida: %d/%d\nVida do %s: %d/%d\n",
         jogador.vidaAtual, jogador.vidaMaxima,
         inimigo.nome, inimigo.vidaAtual, inimigo.vidaMaxima
      ));

      if (contadorAcoes >= inimigoAcoes.size()) {
       contadorAcoes = 0;
      }
      return resultado.toString();
   }


   private Item gerarDrop() {
      Item item = new Item();

      if (Math.random() < 0.5) {
         item.nome = "Poção de Vida";
         item.descricao = "Recupera 30 de vida";
         item.recuperacaoVida = 30;
         item.dano = 0;
      } else {
         item.nome = "Bomba";
         item.descricao = "Causa 20 de dano";
         item.dano = 20;
         item.recuperacaoVida = 0;
      }

      return item;
   }

   public String encerrarEncontro() {
      StringBuilder sb = new StringBuilder();
      int nivelAtual = jogador.getNivel();

      if (!jogador.morrer()) {
         jogador.ganharExperiencia(expAdquirida);
         jogador.pontos += expAdquirida;
         jogador.vidaAtual = jogador.vidaMaxima;

         sb.append(String.format("%s morreu! \n", inimigo.nome))
           .append(jogador.nome)
           .append(" ganhou ")
           .append(expAdquirida)
           .append(" XP.\n");

         if (nivelAtual != jogador.getNivel()) {
            sb.append(jogador.nome)
              .append(" subiu de nível! Agora é nível ")
              .append(jogador.getNivel())
              .append("\n");
         }

         if (loot == null) {
            loot = new ArrayList<>();
         }
         loot.add(gerarDrop());

         if (loot != null && !loot.isEmpty()) {
            sb.append("Itens obtidos: ");
            for (Item item : loot) {
               jogador.inventario.add(item);
               sb.append(item.nome).append(" ");
            }
            sb.append("\n");
         }
      } else {
         sb.append(jogador.nome).append(" morreu!\n");
      }

      return sb.toString();
   }
}
