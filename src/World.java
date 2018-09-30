import java.awt.*;

public class World {
    int gen = 0;

    Population population;

    int worldBestScore = 0;
    Snake worldBestSnake, snakeBeingWatched;

    World(int populationSize){
        population = new Population(populationSize);

        worldBestSnake = population.globalBestSnake;
        snakeBeingWatched = population.globalBestSnake;
        if(worldBestSnake == null){
            System.out.println("population.globalBestSnake returned null");
        }
    }

    void update(){
        population.updateAlive();
        if(!snakeBeingWatched.alive){
            snakeBeingWatched = population.getFirstAliveSnake();
        }
    }

    void render(Graphics g){
        snakeBeingWatched.render(g);
    }
}
