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
            newGeneration();
        }
    }

    void newGeneration(){
        population.breedNextGeneration();
        gen++;
    }


    void render(Graphics g){
        g.setFont(new Font("Arial", Font.BOLD, GameEngine.UNIT_SIZE));
        g.setColor(Color.WHITE);
        g.drawString("Gen: " + gen, GameEngine.UNIT_SIZE, 15);
        g.drawString("Fitness: " + population.topAverageFitness, GameEngine.UNIT_SIZE, 30);
        g.drawString("Snakes Left: " + population.aliveSnakes, GameEngine.UNIT_SIZE, 45);
        g.drawString("Crossover: " + GameEngine.CROSSOVER, GameEngine.UNIT_SIZE, 60);
        //snakeBeingWatched.render(g);
        population.renderAliveSnakes(g);
        //System.out.println(snakeBeingWatched.fitness);
        if(population.aliveSnakes < 10){
            for(int i = 0; i < population.aliveSnakes; i++){
                //population.snakes[i].render(g);
            }
        }
    }
}
