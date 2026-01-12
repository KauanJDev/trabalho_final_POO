import java.util.ArrayList;
public class Mago extends Jogador {

    private int manaMaxima;
    public int mana;

    public Mago(String nome)  {
       super(nome);
       vidaMaxima = 80;
       velocidade = 2;
       ataque = 1;
       defesa = 3;
       armadura = 7;
       mana = 100;
    }

    public Mago(String nome, int vidaMaxima, int velocidade, int ataque, int defesa, int armadura) {
        super(nome, vidaMaxima, velocidade, ataque, defesa, armadura);
        this.mana = 100;
    }

    public String agredir(Entidade e) {
        return super.agredir(e);
    }

    public String BolaDeFogo(Entidade e) {
        int dano = 20 * getNivel();
        e.vidaAtual -= dano;
        mana -= 30;
        return nome + " lançou Bola de Fogo em " + e.nome + ", causando " + dano + " de dano.";
    }

    public String ArmaduraArcana() {
        int armaduraRecuperada = 10;
        armadura += armaduraRecuperada;
        mana -= 20;
        return nome + " usou Armadura Arcana e recuperou " + armaduraRecuperada + " de armadura.";
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
            manaMaxima += 20;
            velocidade += 2;
            vidaMaxima += 6;
            vidaAtual = vidaMaxima;
            mana = manaMaxima;
        }
        return nivelAtual != getNivel();
    }
}
