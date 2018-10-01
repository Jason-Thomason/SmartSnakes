import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake {
    int size = 1;
    Coordinate pos;
    ArrayList<Coordinate> tailPositions;
    Coordinate velocity;
    Coordinate temp;
    Food food;
    NeuralNet brain;
    float[] vision = new float[24];
    float[] brainOutputs;

    Random rand = new Random();
    int lifetime = 0;
    long fitness = 0;
    int idleTimer = 200;

    int growCount = 0;

    boolean alive = true;
    boolean testSnake = false;


    Snake(){
        int x = 250;
        int y = 450;
        pos = new Coordinate(x, y);
        velocity = new Coordinate(10, 0);
        tailPositions = new ArrayList<Coordinate>();
        tailPositions.add(new Coordinate(x - 40, y));
        tailPositions.add(new Coordinate(x - 30, y));
        tailPositions.add(new Coordinate(x - 20, y ));
        tailPositions.add(new Coordinate(x - 10, y ));
        size = 5;

        newFood();

        brain = new NeuralNet(24, 18, 2);
        idleTimer = 200;
    }

    Snake(NeuralNet parentBrain){
        int x = 250;
        int y = 450;
        pos = new Coordinate(x, y);
        velocity = new Coordinate(10, 0);
        tailPositions = new ArrayList<Coordinate>();
        tailPositions.add(new Coordinate(x - 40, y));
        tailPositions.add(new Coordinate(x - 30, y));
        tailPositions.add(new Coordinate(x - 20, y ));
        tailPositions.add(new Coordinate(x - 10, y ));
        size = 5;

        newFood();

        brain = parentBrain.getClone();
        idleTimer = 200;
    }

    void newFood(){
        int x = (rand.nextInt(49) + 1) *10;
        int y = (rand.nextInt(49) + 1) *10;
        while(isOnTail(new Coordinate(x, y)) || (x == pos.x && y == pos.y)){
            x = (rand.nextInt(49) + 1) *10;
            y = (rand.nextInt(49) + 1) *10;
        }
        food = new Food(new Coordinate(x,y));
    }

    void mutate(float mutationRate){
        brain.mutate(mutationRate);
    }

    Snake getClone(){
        Snake s = new Snake(this.brain);
        return s;
    }

    void update(){
        lookAndSetVisionValues();
        brainOutputs = brain.outputs(vision);
        velocity = decideAction(brainOutputs);

        lifetime += 1;
        idleTimer -= 1;

        calculateFitness();

        if(idleTimer < 0 || outOfBounds(pos) || isOnTail(pos)){
            alive = false;
        }else if(pos.x == food.position.x && pos.y == food.position.y){
            eat();
        }

        if(growCount > 0){
            growCount--;
            grow();
        }else{
            for(int i = 0; i < tailPositions.size() -1; i++){
                temp = new Coordinate(tailPositions.get(i+1).x, tailPositions.get(i+1).y);
                tailPositions.set(i, temp);
            }
            temp = new Coordinate(pos.x, pos.y);
            tailPositions.set(size-2, temp);
        }

        pos.add(velocity);


    }

    void grow(){
        temp = new Coordinate(pos.x, pos.y);
        tailPositions.add(temp);
        size++;
    }

    void eat(){
        newFood();
        idleTimer += 100;
        growCount += 3;
    }

    void lookAndSetVisionValues(){
        vision = new float[24];
        float[] temp = lookForFoodTailAndWallInDirection(-10, 0);
        vision[0] = temp[0];
        vision[1] = temp[1];
        vision[2] = temp[2];
        temp = lookForFoodTailAndWallInDirection(-10, 10);
        vision[3] = temp[0];
        vision[4] = temp[1];
        vision[5] = temp[2];
        temp = lookForFoodTailAndWallInDirection(0, 10);
        vision[6] = temp[0];
        vision[7] = temp[1];
        vision[8] = temp[2];
        temp = lookForFoodTailAndWallInDirection(10, 10);
        vision[9] = temp[0];
        vision[10] = temp[1];
        vision[11] = temp[2];
        temp = lookForFoodTailAndWallInDirection(10, 0);
        vision[12] = temp[0];
        vision[13] = temp[1];
        vision[14] = temp[2];
        temp = lookForFoodTailAndWallInDirection(10, -10);
        vision[15] = temp[0];
        vision[16] = temp[1];
        vision[17] = temp[2];
        temp = lookForFoodTailAndWallInDirection(0, -10);
        vision[18] = temp[0];
        vision[19] = temp[1];
        vision[20] = temp[2];
        temp = lookForFoodTailAndWallInDirection(-10, -10);
        vision[21] = temp[0];
        vision[22] = temp[1];
        vision[23] = temp[2];
    }

    float[] lookForFoodTailAndWallInDirection(int x, int y){
        Coordinate tempScanner = new Coordinate(pos.x, pos.y);
        float[] results = new float[3];
        boolean foodIsFound = false;
        boolean tailIsFound = false;
        float distance = 0;
        tempScanner.add(x, y);
        distance += 1;
        while(!outOfBounds(tempScanner)){
            if(!foodIsFound && tempScanner.x == food.position.x && tempScanner.y == food.position.y){
                foodIsFound = true;
                results[0] = 1/distance;
            }

            if(!tailIsFound && isOnTail(tempScanner)){
                tailIsFound = true;
                results[1] = 1/distance;
            }

            tempScanner.add(x, y);
            distance += 1;
        }
        results[2] = 1/distance;
        //Matrix m = new Matrix(results);
        //m.print();
        return results;
    }

    boolean outOfBounds(Coordinate c){
        return (c.x <= 0 || c.x >= 500 || c.y <= 0 || c.y >= 500);
    }

    boolean isOnTail(Coordinate c){
        for(Coordinate cc : tailPositions){
            if(c.x == cc.x && c.y == cc.y){
                return true;
            }
        }
        return false;
    }

    Coordinate decideAction(float[] brainOutputs){
        //System.out.println(brainOutputs[0] + " vs " + brainOutputs[1]);
        if(brainOutputs[0] > brainOutputs[1]){
            //System.out.println("Turning Left");
            return turnLeft(velocity);
        }else if(brainOutputs[0] < brainOutputs[1]){
            //System.out.println("Turning Right");
            return turnRight(velocity);
        }else{
            //System.out.println("Not Turning");
            return velocity;
        }
    }

    Coordinate turnLeft(Coordinate c){
        int newX, newY;
        if(Math.abs(c.x) == 10){
            newX = 0;
        }else{
            newX = c.y * (-1);
        }
        if(Math.abs(c.y) == 10){
            newY = 0;
        }else{
            newY = c.x;
        }
        return new Coordinate(newX, newY);
    }

    Coordinate turnRight(Coordinate c){
        int newX, newY;
        if(Math.abs(c.x) == 10){
            newX = 0;
        }else{
            newX = c.y * 1;
        }
        if(Math.abs(c.y) == 10){
            newY = 0;
        }else{
            newY = c.x * (-1);
        }
        return new Coordinate(newX, newY);
    }

    void calculateFitness(){
        fitness = lifetime/10 + size*size*size;
    }

    void render(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(pos.x, pos.y, 10, 10);
        g.setColor(Color.BLUE);
        g.drawRect(pos.x, pos.y, 10, 10);
        g.setColor(Color.BLACK);
        g.fillOval(pos.x +8, pos.y +2, 1, 2);
        g.fillOval(pos.x +8, pos.y +6, 1, 2);
        for (Coordinate tp : tailPositions){
            g.setColor(Color.CYAN);
            g.fillRect(tp.x, tp.y, 10, 10);
            g.setColor(Color.BLUE);
            g.drawRect(tp.x, tp.y, 10, 10);
        }
        food.render(g);
    }
}
