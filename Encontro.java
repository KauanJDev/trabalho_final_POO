import java.util.ArrayList;
public class Encontro {
   public Jogador jogador;
   public Entidade inimigo;
   public ArrayList<Item> loot;
   public int expAdquirida;
   public int turnoAtual;
   public boolean jogadorComeca;
   // Ações do inimigo
   private ArrayList<Integer> inimigoAcoes;
   private int numeroAcoes;
   private int contadorAcoes;

   private void escolherAcao(Entidade e, int opc)  {
   }

   public void iniciarEncontro()  {
      turnoAtual = 1;

      jogadorComeca = jogador.velocidade >= inimigo.velocidade;
   }

   public void passarTurno(int opc)  {
      if(jogadorComeca)  {
         escolherAcao(jogador, opc);
         escolherAcao(inimigo, inimigoAcoes.get(contadorAcoes));
      } else  {
         escolherAcao(inimigo, inimigoAcoes.get(contadorAcoes));
         escolherAcao(jogador, opc);
      }

      if(jogador.morrer() || inimigo.morrer())  {
         encerrarEncontro();
      }

      contadorAcoes ++;
   }

   public void encerrarEncontro()  {
   
   }
}
