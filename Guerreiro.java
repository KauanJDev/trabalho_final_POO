import java.util.ArrayList;
public class Guerreiro extends Jogador {

    private int staminaMaxima;
    public int stamina;

    public Guerreiro(String nome)  {
       super(nome);
       vidaMaxima = 120;
       velocidade = 1;
       ataque = 10;
       defesa = 5;
       armadura = 10;
       staminaMaxima = 80;
    }

    public Guerreiro(String nome, int vidaMaxima, int velocidade, int ataque, int defesa, int armadura) {
        super(nome, vidaMaxima, velocidade, ataque, defesa, armadura);
        this.stamina = 100;
    }

    public String agredir(Entidade e) {
        return super.agredir(e);
    }

    public String ataqueEspecial(Entidade e) {

        if (stamina < 20) {
            return "Stamina Insuficiente";
        }

        stamina -= 20;

        int poder = ataque * 2;

        int danoArmadura = Math.max(0, poder - e.defesa);

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

    public String descasar() {
        int recuperacao = 30;
        stamina = Math.min(100, stamina + recuperacao);
        return nome + " descansou e recuperou " + recuperacao + " de stamina.";
    }

    public boolean subirDeNivel() {
       int nivelAtual = getNivel();
        while (getExperiencia() >= getExperienciaNecessaria()) {

            reduzirExperiencia(getExperienciaNecessaria());
            if(getNivel() == 10)  {
               return false;
            }
            aumentarNivel();

            ataque += 5;
            defesa += 3;
            staminaMaxima += 10;
            vidaMaxima += 10;
            armadura = 10+getNivel();
            vidaAtual = vidaMaxima;
            stamina = staminaMaxima;
        }
        return nivelAtual != getNivel();
    }
}
