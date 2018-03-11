package edu.hawhamburg.shared.datastructures.spline;

import edu.hawhamburg.shared.math.Vector;

public class HermiteSpline {

    private final Vector[] controlPoints;

    private final double deltaT;

    public HermiteSpline(Vector... controlPoints) {
        this.controlPoints = controlPoints;
        this.deltaT = 1.0 / controlPoints.length;
    }

    private Vector wrappedGet(int index) {
        if (index >= controlPoints.length) {
            index -= controlPoints.length;
        } else if (index < 0) {
            index += controlPoints.length;
        }
        return controlPoints[index];
    }

    private Vector getTangent(int index) {
        return wrappedGet(index + 1)
                .subtract(wrappedGet(index - 1))
                .getNormalized();
    }

    private int getIndexFromGlobalT(double globalT) {
        return (int) (globalT / deltaT);
    }

    private double getLocalTFromGlobalT(double globalT) {
        return (globalT - getIndexFromGlobalT(globalT) * deltaT) / deltaT;
    }

    private double H3_0(double localT) {
        return Math.pow(1 - localT, 2) * (1 + 2 * localT);
    }

    private double H3_0_prime(double localT) {
        return 6 * (localT - 1) * localT;
    }

    private double H3_1(double localT) {
        return localT * Math.pow(1 - localT, 2);
    }

    private double H3_1_prime(double localT) {
        return 3 * Math.pow(localT, 2) - 4 * localT + 1;
    }

    private double H3_2(double localT) {
        return -Math.pow(localT, 2) * (1 - localT);
    }

    private double H3_2_prime(double localT) {
        return localT * (3 * localT - 2);
    }

    private double H3_3(double localT) {
        return (3 - 2 * localT) * Math.pow(localT, 2);
    }

    private double H3_3_prime(double localT) {
        return -6 * (localT - 1) * localT;
    }

    public Vector evaluateCurve(double globalT) {
        double localT = getLocalTFromGlobalT(globalT);
        int index = getIndexFromGlobalT(globalT);

        return wrappedGet(index)
                .multiply(H3_0(localT)).add(
                        getTangent(index).multiply(H3_1(localT))
                ).add(
                        getTangent(index + 1).multiply(H3_2(localT))
                ).add(
                        wrappedGet(index + 1).multiply(H3_3(localT))
                );
    }

    public Vector evaluateTangent(double globalT) {
        double localT = getLocalTFromGlobalT(globalT);
        int index = getIndexFromGlobalT(globalT);

        return wrappedGet(index)
                .multiply(H3_0_prime(localT))
                .add(
                        getTangent(index).multiply(H3_1_prime(localT))
                ).add(
                        getTangent(index + 1).multiply(H3_2_prime(localT))
                ).add(
                        wrappedGet(index + 1).multiply(H3_3_prime(localT))
                )
                .getNormalized();
    }
}
