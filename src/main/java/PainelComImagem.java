import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PainelComImagem extends JPanel {
    private Image imagem;

    public PainelComImagem(String caminhoImg) {
        this.imagem = new ImageIcon(getClass().getResource(caminhoImg))
                .getImage()
                .getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagem != null) {
            int larguraPainel = getWidth();
            int alturaPainel = getHeight();

            int larguraImagem = imagem.getWidth(this);
            int alturaImagem = imagem.getHeight(this);

            // Desenhar perfeitamente no canto inferior direito
            int x = larguraPainel - larguraImagem;
            int y = alturaPainel - alturaImagem - 30;

            g.drawImage(imagem, x, y, this);
        }
    }
}

