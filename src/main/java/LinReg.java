import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class LinReg {

    RealMatrix X;
    RealMatrix Y;

    RealMatrix w;

    LinReg(double[][] _X, double[] _Y){
        X = new Array2DRowRealMatrix(_X);
        Y = new Array2DRowRealMatrix(_Y);
        linRegSearchWeight();
    }

    private void linRegSearchWeight(){
        w = pow_1((X.transpose().multiply(X)).getData()).multiply(X.transpose()).multiply(Y);
    }

    public double[] test(double[][] _X){
        double[] res = new double[_X.length];
        for(int i = 0; i < _X.length; i++){
            double temp = 0;
            for (int j = 0; j < _X[i].length; j++) {
                temp += w.getData()[j][0] * _X[i][j];
            }
            res[i] = temp;
        }
        return res;
    }

    private static RealMatrix pow_1(double[][] X){
        double temp;
        int N = X.length;
        double [][] E = new double [N][N];
        double [][] A = X.clone();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                E[i][j] = 0f;
                if (i == j)
                    E[i][j] = 1f;
            }
            for (int k = 0; k < N; k++)
            {
                temp = A[k][k];
                for (int j = 0; j < N; j++)
                {
                    A[k][j] /= temp;
                    E[k][j] /= temp;
                }
                for (int i = k + 1; i < N; i++)
                {
                    temp = A[i][k];
                    for (int j = 0; j < N; j++)
                    {
                        A[i][j] -= A[k][j] * temp;
                        E[i][j] -= E[k][j] * temp;
                    }
                }
            }
            for (int k = N - 1; k > 0; k--)
            {
                for (int i = k - 1; i >= 0; i--)
                {
                    temp = A[i][k];
                    for (int j = 0; j < N; j++)
                    {
                        A[i][j] -= A[k][j] * temp;
                        E[i][j] -= E[k][j] * temp;
                    }
                }
            }
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    A[i][j] = E[i][j];
        return new Array2DRowRealMatrix(A);
    }

    public static void AverageError(double[] Yp, double[] Y){
        double sse = 0;
        for (int i = 0; i < Yp.length; i++) {
            sse += Math.abs(Yp[i] - Y[i]);
        }
        System.out.println("AvError = " + sse/Y.length);
    }
}
