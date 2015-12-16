package com.github.kirivasile.etsp;

import java.awt.*;
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
        /*for(int i = 0; i < vertexCount; ++i){
            for(int j = 0; j < vertexCount; ++j){
                matrix[i][j] = (float)in.nextDouble();
            }
        }*/
        Vertex[] vertexes = new Vertex[vertexCount];
        for (int i = 0; i < vertexCount; ++i) {
            vertexes[i] = new Vertex();
            vertexes[i].setX(in.nextFloat());
            vertexes[i].setY(in.nextFloat());
        }
        for (int i = 0; i < vertexCount; ++i) {
            for (int j = 0; j < vertexCount; ++j) {
                float dx = Math.abs(vertexes[i].getX() - vertexes[j].getX());
                float dy = Math.abs(vertexes[i].getY() - vertexes[j].getY());
                matrix[i][j] = (float)Math.sqrt(dx * dx + dy * dy);
            }
        }
        in.close();
        GeneticSolver gs = new GeneticSolver(matrix);
        gs.setSelectionPercent(0.5f);
        gs.setMutationPercent(0.1f);
        gs.setGenerationCount(500);
        gs.setPopulationSize(1000);
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

    public static class Vertex {
        float x;
        float y;

        public Vertex() {
        }

        public Vertex(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
