public class Coordinate {
    int x, y;

    Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    void add(int x, int y){
        this.x += x;
        this.y += y;
    }

    void add(Coordinate c){
        this.x += c.x;
        this.y += c.y;
    }
}
