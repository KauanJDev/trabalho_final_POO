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
      if (e instanceof Jogador j) {
         switch (opc) {
            case 1: return j.agredir(inimigo);
            case 2: return j.acao2(inimigo);
            case 3: return j.acao3();
         }
      } else if (e instanceof Inimigo i) {
         return i.agredir(jogador);
      }
      return "";
   }

   public void iniciarEncontro() {
      turnoAtual = 1;
      jogadorComeca = jogador.velocidade >= inimigo.velocidade;
   }

   public String passarTurno(int opc) {
      StringBuilder resultado = new StringBuilder();

      if (jogadorComeca) {
         resultado.append(escolherAcao(jogador, opc)).append("\n");
         resultado.append(escolherAcao(inimigo, inimigoAcoes.get(contadorAcoes))).append("\n");
      } else {
         resultado.append(escolherAcao(inimigo, inimigoAcoes.get(contadorAcoes))).append("\n");
         resultado.append(escolherAcao(jogador, opc)).append("\n");
      }

      resultado.append(String.format("Sua Vida: %d/%d \n Vida do %s %d/%d\n", jogador.vidaAtual, jogador.vidaMaxima, inimigo.nome, inimigo.vidaAtual, inimigo.vidaMaxima));

      contadorAcoes++;
      return resultado.toString();
   }

   public String encerrarEncontro() {
      StringBuilder sb = new StringBuilder();
      int nivelAtual = jogador.getNivel();

      if (!jogador.morrer()) {
         jogador.ganharExperiencia(expAdquirida);
         jogador.vidaAtual = jogador.vidaMaxima;
         sb.append(jogador.nome).append(" ganhou ").append(expAdquirida).append(" XP.\n");

         if (nivelAtual != jogador.getNivel()) {
            sb.append(jogador.nome).append(" subiu de nível! Agora é nível ").append(jogador.getNivel()).append("\n");
         }

         if (loot != null && !loot.isEmpty()) {
            sb.append("Itens obtidos: ");
            for (Item item : loot) {
               sb.append(item.nome).append(" ");
            }
            sb.append("\n");
         }
      } else {
         jogador.vidaAtual = jogador.vidaMaxima;
         sb.append(jogador.nome).append(" morreu!\n");
      }

      return sb.toString();
   }
}
