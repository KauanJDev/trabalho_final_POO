import javax.swing.*;

public class GameButton extends JButton{
   public int acao;

   public GameButton(String nome, int acao)  {
      super(nome);
      this.acao =  acao;
   }
}
