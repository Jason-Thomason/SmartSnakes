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
                float mutationChance = rand.nextFloat();
                if(mutationChance < mutationRate){
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

    Matrix addBias(){
        Matrix m = new Matrix(rows, cols +1);
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    m.matrix[i][j] = this.matrix[i][j];
                }
                m.matrix[i][cols + 1] = 1;
            }
            return m;
    }

    Matrix dotProduct(Matrix m){
        if(cols != m.rows){
            System.out.println("Invalid Dot Product Dimensions");
            return null;
        }

        Matrix output = new Matrix(rows, m.cols);
        for (int i = 0; i < rows; i++){
            for(int j = 0; j < m.cols; j++){
                int sum = 0;
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


}
