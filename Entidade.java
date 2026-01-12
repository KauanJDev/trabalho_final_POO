public abstract class Entidade {
   public String nome;
   public int vidaAtual;
   public int vidaMaxima;
   public int velocidade;
   public int ataque;
   public int defesa;
   public int armadura;

   public Entidade()
   {
      vidaMaxima = 10;
      vidaAtual = vidaMaxima;
      velocidade = 1;
      ataque = 1;
      armadura = 0;
   }
   public Entidade(String nome, int vidaMaxima, int velocidade, int ataque, int defesa, int armadura)
   {
      this.nome = nome;
      this.vidaMaxima = vidaMaxima;
      this.vidaAtual = vidaMaxima;
      this.velocidade = velocidade;
      this.ataque = ataque;
      this.defesa = defesa;
      this.armadura = armadura;
   }

   public abstract String agredir(Entidade e);
   
   public String recuperarArmadura()
   {
      armadura += 3;
      return "Armadura recuperada";
   }

   public boolean morrer()
   {
      if(vidaAtual<=0)
      {
         return true;
      }
      else 
      {
         return false;
      }
   }
}
