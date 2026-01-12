import java.awt.event.*;

import javax.swing.JOptionPane;

public class GameListener implements ActionListener {
    private Exec exec;

    public GameListener(Exec exec) {
        this.exec = exec;
    }

    public void actionPerformed(ActionEvent e) {
        GameButton botao = (GameButton) e.getSource();
        int acao = botao.acao;

        switch (acao) {
            case 1:
                exec.mudarPanel(exec.telaPersonagem());
                break;

            case 2:
                if (exec.jogadorAtual == null) {
                    JOptionPane.showMessageDialog(exec, "Crie um personagem antes!");
                    return;
                }
                exec.iniciarCampanha();
                break;
            case 3:
                exec.salvarJogo(exec.jogadorAtual);
                break;

            case 4:
                exec.carregarJogo();
                break;
        }
    }
}
