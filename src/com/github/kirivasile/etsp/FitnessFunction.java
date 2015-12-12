package com.github.kirivasile.etsp;

/**
 * Created by Kirill on 12.12.2015.
 * GitHub profile: http://github.com/kirivasile
 * E-mail: kirivasile@yandex.ru
 */
public class FitnessFunction {
    public Path getWinner(Path a, Path b) {
        return getFitnessValue(a) > getFitnessValue(b) ? a : b;
    }

    public float getFitnessValue(Path p) {
        if (p.getValue() == 0) {
            return 0;
        }
        return 1 / p.getValue();
    }
}
