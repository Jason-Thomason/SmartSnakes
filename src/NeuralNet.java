public class NeuralNet {
    private int iNodes, hNodes, oNodes;
    Matrix hiWeights, hhWeights, ohWeights;

    public NeuralNet(int inputNo, int hiddenNo, int outputNo){
        iNodes = inputNo;
        hNodes = hiddenNo;
        oNodes = outputNo;

        hiWeights = new Matrix(hNodes, iNodes + 1);
        hhWeights = new Matrix(hNodes, hNodes + 1);
        ohWeights = new Matrix(oNodes, hNodes + 1);

        hiWeights.randomize();
        hhWeights.randomize();
        ohWeights.randomize();
    }

    void mutate(float mutationRate){
        hiWeights.mutate(mutationRate);
        hhWeights.mutate(mutationRate);
        ohWeights.mutate(mutationRate);
    }

    float[] outputs(float[] visionInputs){
        Matrix inputs = new Matrix(visionInputs);
        Matrix inputsWithBias = inputs.addBias();

        Matrix weightedHiddenLayer1Inputs = hiWeights.dotProduct(inputsWithBias);
        //weightedHiddenLayer1Inputs.print();
        Matrix activatedHiddenLayer1Outputs = weightedHiddenLayer1Inputs.activateRelU();
        Matrix activatedHiddenLayer1OutputsWithBias = activatedHiddenLayer1Outputs.addBias();

        Matrix weightedHiddenLayer2Inputs = hhWeights.dotProduct((activatedHiddenLayer1OutputsWithBias));
        Matrix activatedHiddenLayer2Outputs = weightedHiddenLayer2Inputs.activateRelU();
        Matrix activatedHiddenLayer2OutputsWithBias = activatedHiddenLayer2Outputs.addBias();

        Matrix weightedOutputLayerInputs = ohWeights.dotProduct(activatedHiddenLayer2OutputsWithBias);
        Matrix activatedOutputLayerOutputs = weightedOutputLayerInputs.activateRelU();



        return activatedOutputLayerOutputs.toArray();
    }

    NeuralNet getClone(){
        NeuralNet temp = new NeuralNet(iNodes, hNodes, oNodes);
        temp.hiWeights = this.hiWeights.getClone();
        temp.hhWeights = this.hhWeights.getClone();
        temp.ohWeights = this.ohWeights.getClone();
        return temp;
    }

}
