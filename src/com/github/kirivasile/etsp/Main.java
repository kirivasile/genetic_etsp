package com.github.kirivasile.etsp;

import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by Kirill on 02.12.2015.
 * GitHub profile: http://github.com/kirivasile
 * E-mail: kirivasile@yandex.ru
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new FileReader("test.txt"));
        int vertexCount = in.nextInt();
        float[][] matrix = new float[vertexCount][vertexCount];
        for(int i = 0; i < vertexCount; ++i){
            for(int j = 0; j < vertexCount; ++j){
                matrix[i][j] = (float)in.nextDouble();
            }
        }
        in.close();
        /*float[][] matrix = new float[5][5];
        matrix[0][1] = 4;
        matrix[0][2] = 5.6f;
        matrix[0][3] = 6.3f;
        matrix[0][4] = 4;
        matrix[1][0] = 4;
        matrix[1][2] = 4;
        matrix[1][3] = 6.3f;
        matrix[1][4] = 5.6f;
        matrix[2][0] = 5.6f;
        matrix[2][1] = 4;
        matrix[2][3] = 2.8f;
        matrix[2][4] = 4;
        matrix[3][0] = 6.3f;
        matrix[3][1] = 6.3f;
        matrix[3][2] = 2.8f;
        matrix[3][4] = 2.8f;
        matrix[4][0] = 4;
        matrix[4][1] = 5.6f;
        matrix[4][2] = 4;
        matrix[4][3] = 2.8f;*/
        GeneticSolver gs = new GeneticSolver(matrix);
        gs.setSelectionPercent(0.3f);
        gs.setMutationPercent(0.1f);
        gs.setGenerationCount(50);
        gs.setPopulationSize(100);
        Path result = gs.handle();
        int[] arr = result.getPath();
        float value = result.getValue();
        System.out.println("Value: " + value);
        System.out.println("Path:");
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
