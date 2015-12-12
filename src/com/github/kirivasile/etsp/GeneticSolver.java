package com.github.kirivasile.etsp;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill on 02.12.2015.
 * GitHub profile: http://github.com/kirivasile
 * E-mail: kirivasile@yandex.ru
 */
public class GeneticSolver {
    private float[][] distanceMatrix;
    private int generationCount;
    private int populationSize;
    private float mutationPercent;
    private float selectionPercent;
    private int numCities;
    private List<Path> currentPopulation;
    private SecureRandom random;

    public GeneticSolver(float[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        numCities = distanceMatrix.length;
        random = new SecureRandom();
    }

    public Path handle() {
        long startTime = System.currentTimeMillis();
        currentPopulation = createFirstPopulation();
        for (int generation = 0; generation < generationCount; ++generation) {
            selection();
            crossing();
            mutation();
            if (currentPopulation.size() == 1) {
                long endTime = System.currentTimeMillis();
                System.out.println("Working time: " + (endTime - startTime));
                return currentPopulation.get(0);
            }
        }
        int resultId = 0;
        FitnessFunction ff = new FitnessFunction();
        float maxValue = ff.getFitnessValue(currentPopulation.get(0));
        for (int i = 1; i < currentPopulation.size(); ++i) {
            float currentValue = ff.getFitnessValue(currentPopulation.get(i));
            if (currentValue < maxValue) {
                resultId = i;
                maxValue = currentValue;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Working time: " + (endTime - startTime));
        return currentPopulation.get(resultId);
    }

    private List<Path> createFirstPopulation() {
        List<Path> result = new ArrayList<Path>(populationSize);
        for (int i = 0; i < populationSize; ++i) {
            result.add(null);
        }
        for (int p = 0; p < populationSize; ++p) {
            int[] path = new int[numCities];
            for (int i = 0; i < path.length; ++i) {
                path[i] = i;
            }
            for (int i = 0; i < path.length; ++i) {
                int index = random.nextInt(path.length);
                int temp = path[i];
                path[i] = path[index];
                path[index] = temp;
            }
            result.set(p, new Path(path, distanceMatrix));
        }
        return result;
    }

    private void selection() {
        int nextGenerationSize = Math.round(populationSize * selectionPercent);
        if (nextGenerationSize == 0) {
            return;
        }
        List<Path> nextGeneration = new ArrayList<Path>(nextGenerationSize);
        for (int i = 0; i < nextGenerationSize; ++i) {
            nextGeneration.add(null);
        }
        FitnessFunction ff = new FitnessFunction();
        for (int i = 0; i < nextGenerationSize; ++i) {
            Path first = currentPopulation.get(random.nextInt(populationSize));
            Path second = currentPopulation.get(random.nextInt(populationSize));
            nextGeneration.set(i, ff.getWinner(first, second));
        }
        populationSize = nextGenerationSize;
        currentPopulation = nextGeneration;
    }

    private void mutation() {
        for (Path path : currentPopulation) {
            path.mutate(mutationPercent);
        }
    }

    public void crossing() {
        List<Path> childs = new ArrayList<Path>();
        for (int i = 0; i < populationSize; i += 2) {
            if (i < populationSize - 1) {
                Path child = Path.cross(currentPopulation.get(i), currentPopulation.get(i + 1));
                childs.add(child);
            }
        }
        for (Path child : childs) {
            currentPopulation.add(child);
        }
        populationSize += childs.size();
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }

    public void setMutationPercent(float mutationPercent) {
        this.mutationPercent = mutationPercent;
    }

    public void setSelectionPercent(float selectionPercent) {
        this.selectionPercent = selectionPercent;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    //Temp
    public void showCurrentPopulation() {
        for (int i = 0; i < populationSize; ++i) {
            System.out.print("Population " + i + ": ");
            int[] arr = currentPopulation.get(i).getPath();
            for (int it : arr) {
                System.out.print(it + " ");
            }
            System.out.println();
        }
    }
}
