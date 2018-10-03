import java.awt.*;

public class Food {
    Coordinate position;



    Food(Coordinate c){
        position = c;
    }

    void render(Graphics g, int colorAlpha){
        Color color = Color.RED;
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), colorAlpha));
        g.fillRect(position.x*GameEngine.UNIT_SIZE, position.y*GameEngine.UNIT_SIZE, GameEngine.UNIT_SIZE, GameEngine.UNIT_SIZE);
    }
}