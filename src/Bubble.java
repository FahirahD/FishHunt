import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bubble extends Entity {

    private double r;

    public Bubble(double x, double y, double r, double vy) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vy = vy;
    }

    /**
     * Met a jour la position et la vitesse de la balle
     *
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        super.update(dt);
    }

    /**
     * Permet dafficher la bulle
     *
     * @param context
     */
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.rgb(0, 0, 255, 0.4));
        context.fillOval(this.getX(), this.getY(),
                this.getW(), this.getH());
    }

    /**
     * Getter de la position x
     *
     * @return double
     */
    public double getX() {
        return x;
    }


    /**
     * Getter de la position y
     *
     * @return double
     */
    public double getY() {
        return y;
    }


    /**
     * Getter de la largeur
     *
     * @return double
     */
    public double getW() {
        return 2 * r;
    }

    /**
     * Getter de la hauteur
     *
     * @return double
     */
    public double getH() {
        return 2 * r;
    }
}
