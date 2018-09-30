import static jdk.nashorn.internal.objects.NativeMath.random;

public class Population {
    Snake [] snakes;

    int gen = 1;
    int globalBest = 4;
    int aliveSnakes;
    long globalBestFitness = 0;
    int currentBest = 4;
    int currentBestSnake = 0;
    float mutationRate = (float)0.01;
    boolean dead;

    Snake globalBestSnake;

    Population(int size){
        snakes = new Snake[size];

        for (int i = 0; i < size; i++){
            snakes[i] = new Snake();
        }
        dead = false;
        globalBestSnake = snakes[0];

    }

    Population(int size, Snake fittestSnakeFromLastPopulation){
        snakes = new Snake[size];

        snakes[0] = fittestSnakeFromLastPopulation;
        for (int i = 1; i < 11; i++){
            snakes[i] = new Snake(fittestSnakeFromLastPopulation.brain);
            snakes[i].mutate((float)0.5);
        }
        for (int i = 11; i < size; i++){
            snakes[i] = new Snake(fittestSnakeFromLastPopulation.brain);
            snakes[i].mutate(mutationRate);
        }
        globalBestSnake = fittestSnakeFromLastPopulation;
        dead = false;
    }

    void updateAlive(){
        aliveSnakes = 0;
        for(Snake s: snakes){
            if(s.alive){
                aliveSnakes++;
                s.update();
            }
        }

    }

    Snake getFirstAliveSnake(){
        for (Snake s : snakes){
            if(s.alive){
                return s;
            }
        }
        dead = true;
        globalBestSnake = calculateBestSnake();
        return globalBestSnake;
    }

    Snake calculateBestSnake(){
        Snake bestSnake = snakes[0];
        for(Snake s: snakes){
            if(s.fitness > bestSnake.fitness){
                bestSnake = s;
            }
        }
        return bestSnake;
    }

}
