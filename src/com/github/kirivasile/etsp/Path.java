package com.github.kirivasile.etsp;

import java.security.SecureRandom;
import java.util.*;

/**
 * Created by Kirill on 12.12.2015.
 * GitHub profile: http://github.com/kirivasile
 * E-mail: kirivasile@yandex.ru
 */
public class Path {
    private int[] path;
    private float[][] distances;

    public Path(int[] path, float[][] distances) {
        this.path = path;
        this.setDistances(distances);
    }

    public int[] getPath() {
        return path;
    }

    public float getValue() {
        float result = 0;
        for (int i = 0; i < path.length; ++i) {
            int next = (i + 1) % path.length;
            result += getDistances()[path[i]][path[next]];
        }
        return result;
    }

    public void mutate(float mutationPercent) {
        for (int i = 0; i < path.length; ++i) {
            SecureRandom random = new SecureRandom();
            if (random.nextFloat() <= mutationPercent) {
                int next = random.nextInt(path.length);
                int temp = path[i];
                path[i] = path[next];
                path[next] = temp;
            }
        }
    }

    public static Path cross(Path a, Path b) {
        int pathLength = a.getPath().length;
        SecureRandom random = new SecureRandom();
        int begin = random.nextInt(pathLength);
        int end = begin + random.nextInt(pathLength - begin);
        List<Integer> resultList = new ArrayList<Integer>();
        for (int i = 0; i <= end; ++i) {
            resultList.add(null);
        }
        for (int i = begin; i <= end; ++i) {
            resultList.set(i, a.getPath()[i]);
        }
        int index = 0;
        for (int i = 0; i < pathLength; ++i) {
            boolean found = false;
            int value = b.getPath()[i];
            for (int j = begin; j <= end; ++j) {
                if (resultList.get(j).equals(new Integer(value))) {
                    found = true;
                }
            }
            if (!found) {
                if (index < begin) {
                    resultList.set(index, value);
                } else {
                    resultList.add(value);
                }
                ++index;
            }
        }
        int[] result = new int[pathLength];
        for (int i = 0; i < resultList.size(); ++i) {
            result[i] = resultList.get(i);
        }
        return new Path(result, a.getDistances());
    }

    public float[][] getDistances() {
        return distances;
    }

    public void setDistances(float[][] distances) {
        this.distances = distances;
    }
}
