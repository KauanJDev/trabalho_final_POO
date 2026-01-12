import java.util.ArrayList;
public class Ladino extends Jogador {
   public boolean esquiva;

    public Ladino(String nome)  {
       super(nome);
       vidaMaxima = 60;
       velocidade = 5;
       ataque = 5;
       defesa = 3;
       armadura = 5;
       esquiva = false;
    }

    public Ladino(String nome, int vidaMaxima, int velocidade, int ataque, int defesa, int armadura) {
        super(nome, vidaMaxima, velocidade, ataque, defesa, armadura);
        esquiva = false;
    }

    public String sabotar(Entidade e) {

        int reducaoDefesa = getNivel()*10;

        e.defesa = Math.max(0, e.defesa - reducaoDefesa);

        return nome + " usou Sabotar em " + e.nome + ", reduzindo a defesa em " + reducaoDefesa + ".";
    }

    public String agredir(Entidade e) {
        return super.agredir(e);
    }

    public boolean subirDeNivel() {
       int nivelAtual = getNivel();
        while (getExperiencia() >= getExperienciaNecessaria()) {

            reduzirExperiencia(getExperienciaNecessaria());
            if(getNivel() == 10)  {
               return false;
            }
            aumentarNivel();

            ataque += 4;
            defesa += 2;
            velocidade += 2;
            vidaMaxima += 6;
            vidaAtual = vidaMaxima;
        }
        return nivelAtual != getNivel();
    }
}
