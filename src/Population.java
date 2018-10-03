import java.awt.*;
import java.util.Random;

import static jdk.nashorn.internal.objects.NativeMath.random;

public class Population {
    Snake [] snakes;

    int gen = 1;
    int globalBest = 4;
    int aliveSnakes;
    long globalBestFitness = 0;
    int currentBest = 4;
    float mutationRate = (float)0.01;
    int topAverageFitness = 1;
    int currentBestFitness = 1;
    boolean dead;
    Random rand = new Random();

    Snake globalBestSnake, generationBestSnake;

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

        for (int i = 1; i < size; i++){
            snakes[i] = new Snake(fittestSnakeFromLastPopulation.brain);
            snakes[i].mutate(mutationRate);
        }
        globalBestSnake = fittestSnakeFromLastPopulation;
        dead = false;
    }

    void updateAlive(){
        aliveSnakes = 0;
        updateAverageFitness();
        for(Snake s: snakes){
            if(s.alive){
                aliveSnakes++;
                s.update();
                if(currentBestFitness < s.fitness) {
                    currentBestFitness = s.fitness;
                }
                double alphaValue = (double)s.fitness/currentBestFitness;
                s.updateAlpha(alphaValue);
                //s.updateAlpha((double)s.fitness/topAverageFitness);
            }
        }

    }

    void updateAverageFitness(){
        int currentAverageFitness = 0;
        currentBestFitness = 1;
        for(Snake s: snakes){
            currentAverageFitness += s.fitness;
            if(currentBestFitness < s.fitness){
                currentBestFitness = s.fitness;
            }
        }
        currentAverageFitness = Math.floorDiv(currentAverageFitness, snakes.length);
        if(currentAverageFitness > topAverageFitness){
            topAverageFitness = currentAverageFitness;
        }
    }

    Snake getFirstAliveSnake(){
        for (Snake s : snakes){
            if(s.alive){
                return s;
            }
        }
        dead = true;
        return globalBestSnake.getClone();
    }

    Snake calculateGenerationBestSnake(){
        generationBestSnake = snakes[0];
        for(Snake s: snakes){
            if(s.fitness > generationBestSnake.fitness){
                generationBestSnake = s;
            }
        }
        return generationBestSnake;
    }

    void breedNextGeneration(){

        Snake[] newSnakes = new Snake[snakes.length];
        calculateGenerationBestSnake();
        System.out.println("Best Snake: " + generationBestSnake.fitness);
        updateGlobalBestSnake();
        newSnakes[0] = globalBestSnake.getClone();
        globalBestSnake = newSnakes[0];
        for(int i = 1; i < snakes.length; i++){
            newSnakes[i] = breedNewSnake().getClone();
            newSnakes[i].mutate(mutationRate);
        }
        snakes = newSnakes.clone();
        dead = false;
    }

    void updateGlobalBestSnake(){
        if(generationBestSnake.fitness > globalBestSnake.fitness){
            globalBestSnake = generationBestSnake;
        }
    }

    Snake breedNewSnake(){
        Snake parent1 = selectParentSnake();
        Snake parent2 = selectParentSnake();
        NeuralNet babyBrain;
        double crossoverProbability = rand.nextDouble();
        if(GameEngine.CROSSOVER){
            if(crossoverProbability > 0.5){
                //System.out.println("Crossover Performed");
                babyBrain = crossover(parent1, parent2);
            }else{
                babyBrain = parent1.brain.getClone();
            }
        }else{
            babyBrain = parent1.brain.getClone();
        }
        return new Snake(babyBrain);
    }

    Snake selectParentSnake(){
        long totalFitness = 0;
        for(int i = 0; i < snakes.length; i++){
            totalFitness += snakes[i].fitness;
        }
        Random rand = new Random();
        long gateValue = (long)(rand.nextDouble() * totalFitness);
        long runningTotal = 0;
        for(int i = 0; i < snakes.length; i++){
            runningTotal += snakes[i].fitness;
            if(runningTotal > gateValue){
                    return snakes[i].getClone();
            }
        }
        return selectParentSnake();
    }

    NeuralNet crossover(Snake parent1, Snake parent2){
        return parent1.crossover(parent2);
    }

    void renderAliveSnakes(Graphics g){
        for(Snake s: snakes){
            if(s.alive){
                s.render(g);
            }

        }
    }

}
