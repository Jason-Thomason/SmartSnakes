import java.awt.*;

public class World {
    int gen = 0;
    int populationSize;

    Population population;

    int worldBestScore = 0;
    Snake worldBestSnake, snakeBeingWatched;

    World(int populationSize){
        this.populationSize = populationSize;
        population = new Population(populationSize);
        gen = 1;

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
        if(population.dead){
            newGeneration(population.globalBestSnake);
        }
    }

    void newGeneration(Snake fittestSnake){
        population = new Population(populationSize, fittestSnake);
        gen++;
    }


    void render(Graphics g){
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Gen: " + gen, 15, 15);
        g.drawString("Fitness: " + snakeBeingWatched.fitness, 15, 30);
        g.drawString("Snakes Left: " + population.aliveSnakes, 15, 45);
        snakeBeingWatched.render(g);
        if(population.aliveSnakes < 10){
            for(int i = 0; i < population.aliveSnakes; i++){
                //population.snakes[i].render(g);
            }
        }
    }
}
