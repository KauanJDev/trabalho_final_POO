import java.util.ArrayList;
public abstract class Jogador extends Entidade {
   private int experiencia;
   private int nivel;
   public ArrayList<Item> inventario;
   public String habilidades;
   private int classe;

   public Jogador() {
      super();
      experiencia = 0;
      nivel = 1;
      habilidades = "";
      inventario = new ArrayList<Item>();
   }
   public Jogador(String nome) {
      this.nome = nome;
      experiencia = 0;
      nivel = 1;
      habilidades = "";
      inventario = new ArrayList<Item>();
   }

   public Jogador(String nome, int vidaMaxima, int velocidade, int ataque, int defesa, int armadura) {
      super(nome, vidaMaxima, velocidade, ataque, defesa, armadura);
      vidaAtual = vidaMaxima;
      experiencia = 0;
      nivel = 1;
      habilidades = "";
      inventario = new ArrayList<Item>();
   }

   public int getClasse()  {
      return classe;
   }

   public String agredir(Entidade e) {
      int danoArmadura = Math.max(0, ataque - e.defesa);

      if (e.armadura >= danoArmadura) {
         e.armadura -= danoArmadura;
         return String.format("%d de dano causado a armadura do oponente, armadura do oponente", danoArmadura, e.armadura);
      } else {
         int excesso = danoArmadura - e.armadura;
         e.armadura = 0;
         e.vidaAtual -= excesso;
         return String.format("%d de dano causado ao oponente, vida do oponente: %d", excesso, e.vidaAtual);
      }

   }

   public void adicionarItem(Item item)  {
      inventario.add(item);
   }

   

   private String agredirComItem(Entidade e, Item item) {

    int danoArmadura = Math.max(0, item.dano - e.defesa);

    if (e.armadura >= danoArmadura) {
        e.armadura -= danoArmadura;
         return String.format("%s causou %d de dano a armadura do oponente", item, danoArmadura);
    } else {
        int excesso = danoArmadura - e.armadura;
        e.armadura = 0;
        e.vidaAtual -= excesso;
         return String.format("%s causou %d de dano ao do oponente", item, danoArmadura);
    }
   }

   public void ganharExperiencia(int xp) {
      experiencia += xp;
      subirDeNivel();
   }

   public int getNivel() {
      return nivel;
   }

   public int getExperiencia() {
      return experiencia;
   }

   public int getExperienciaNecessaria() {
      return nivel * 100;
   }
  public void reduzirExperiencia(int xp) {
      experiencia -= xp;
   }
   public void aumentarNivel() {
      nivel++;
   }

   public void usarItem(Item item, Entidade e) {
      if (item.dano > 0) {
         agredirComItem(e, item);
      } else if (item.recuperacaoVida > 0) {
         vidaAtual = Math.min(
            vidaAtual + item.recuperacaoVida,
            vidaMaxima
         );
      }
   }

   public abstract boolean subirDeNivel();
}
