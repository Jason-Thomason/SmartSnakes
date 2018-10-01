import java.util.Random;

public class Matrix {
    int rows, cols;
    float [][] matrix;
    Random rand = new Random();

    Matrix(int r, int c){
        rows = r;
        cols = c;
        matrix = new float[rows][cols];
    }

    Matrix(float[][] m){
        matrix = m;
        rows = m.length;
        cols = m[0].length;
    }

    Matrix(float[] m){
        matrix = new float[m.length][1];
        rows = m.length;
        cols = 1;
        for(int i = 0; i < rows; i++){
            matrix[i][0] = m[i];
        }
    }

    void randomize(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                matrix[i][j] = rand.nextFloat() * 2 - 1; //Float between -1 and 1
            }
        }
    }

    void mutate(float mutationRate){
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                float mutationChance = (float)(rand.nextFloat());
                //System.out.println("MutationChance:" + mutationChance + " MutationRate:" + mutationRate);
                if(mutationChance < mutationRate){
                    float temp = matrix[i][j];
                    matrix[i][j] += rand.nextGaussian()/5;
                }

                if(matrix[i][j] > 1){
                    matrix[i][j] = 1;
                }else if(matrix[i][j] < -1){
                    matrix[i][j] = -1;
                }
            }
        }
    }

    Matrix getClone(){
        Matrix temp = new Matrix(rows, cols);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                temp.matrix[i][j] = this.matrix[i][j];
            }
        }
        return temp;
    }

    Matrix addBias(){
        Matrix m = new Matrix(rows + 1, 1);
            for(int i = 0; i < rows; i++){
                m.matrix[i][0] = this.matrix[i][0];
            }
            m.matrix[rows][0] = 1;
        return m;
    }


    Matrix dotProduct(Matrix m){
        if(cols != m.rows){
            System.out.println("Invalid Dot Product Dimensions " + rows + "X" + cols + " and " + m.rows + "X" + m.cols);
            return null;
        }

        Matrix output = new Matrix(rows, m.cols);
        for (int i = 0; i < rows; i++){
            for(int j = 0; j < m.cols; j++){
                float sum = 0;
                for (int k = 0; k < cols; k++){
                    sum += this.matrix[i][k] * m.matrix[k][j];
                }
                output.matrix[i][j] = sum;
            }
        }

        return output;
    }

    Matrix activateRelU(){
        if(this.cols > 1){
            System.out.println("Cannot Activate for " + cols + " column matrix");
            return null;
        }

        Matrix activatedValueMatrix = new Matrix(this.rows, 1);
        for(int i = 0; i < this.rows; i++){
            float activatedValue = Math.max(0, this.matrix[i][0]);
            activatedValueMatrix.matrix[i][0] = activatedValue;
        }
        return activatedValueMatrix;
    }

    float[] toArray(){
        if(cols > 1){
            System.out.println("Cannot convert " + cols + " column matrix to array");
            return null;
        }
        float[] result = new float[rows];
        for (int i = 0; i < rows; i++){
            result[i] = this.matrix[i][0];
        }
        return result;
    }

    void print(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++) {
                System.out.print(this.matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


}
