public class Inimigo extends Entidade {

    public Inimigo(String nome, int vidaMaxima, int velocidade, int ataque, int defesa, int armadura) {
        super(nome, vidaMaxima, velocidade, ataque, defesa, armadura);
    }

    public String agredir(Entidade e) {
        int danoArmadura = Math.max(0, ataque - e.defesa);
        if (e.armadura >= danoArmadura) {
            e.armadura -= danoArmadura;
            return String.format("%s atacou %s e causou %d de dano na armadura. Armadura restante: %d",
                    nome, e.nome, danoArmadura, e.armadura);
        } else {
            int excesso = danoArmadura - e.armadura;
            e.armadura = 0;
            e.vidaAtual -= excesso;
            return String.format("%s atacou %s e causou %d de dano na vida. Vida restante: %d",
                    nome, e.nome, excesso, e.vidaAtual);
        }
    }
}
