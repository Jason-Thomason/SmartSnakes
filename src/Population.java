import static jdk.nashorn.internal.objects.NativeMath.random;

public class Population {
    Snake [] snakes;

    int gen = 1;
    int globalBest = 4;
    long globalBestFitness = 0;
    int currentBest = 4;
    int currentBestSnake = 0;

    Snake globalBestSnake;

    Population(int size){
        snakes = new Snake[size];

        for (int i = 0; i < size; i++){
            snakes[i] = new Snake();
        }
        globalBestSnake = snakes[0];

    }

    void updateAlive(){

    }

    Snake getFirstAliveSnake(){
        for (Snake s : snakes){
            if(s.alive){
                return s;
            }
        }
        return globalBestSnake;
    }

}
