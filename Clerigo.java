import java.util.ArrayList;
public class Clerigo extends Jogador {
    public int fe;

    public Clerigo(String nome)  {
       super(nome);
       vidaMaxima = 90;
       velocidade = 3;
       ataque = 5;
       defesa = 7;
       armadura = 3;
       fe = 100;
    }

    public Clerigo(String nome, int vidaMaxima, int velocidade, int ataque, int defesa, int armadura) {
        super(nome, vidaMaxima, velocidade, ataque, defesa, armadura);
        fe = 100;
    }

    public String agredir(Entidade e) {
        return super.agredir(e);
    }

    public String Rezar(){
        fe += 5 * getNivel();
        if(fe > 100) fe = 100;
        return nome + " rezou e recuperou fé. Fé atual: " + fe;
    }

    public String LuzDivina(){
        int cura = 30;
        vidaAtual += cura;
        fe -= 25;
        return nome + " usou Luz Divina, recuperando " + cura + " de vida.";
    }
    
    public String ArmaduraDivina() {
        int armaduraRecuperada = 15;
        armadura += armaduraRecuperada;
        fe -= 20;
        return nome + " usou Armadura Divina e recuperou " + armaduraRecuperada + " de armadura.";
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
