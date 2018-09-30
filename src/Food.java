import java.awt.*;

public class Food {
    Coordinate position;



    Food(Coordinate c){
        position = c;
    }

    void render(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(position.x, position.y, 10, 10);
    }
}