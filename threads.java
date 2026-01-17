class IntegralThread extends Thread {

    private double start;
    private double end;
    private int steps;
    private double result;

    public IntegralThread(double start, double end, int steps) {
        this.start = start;
        this.end = end;
        this.steps = steps;
    }

    private double function(double x) {
        return x * x; 
    }

    @Override
    public void run() {
        double dx = (end - start) / steps;
        double sum = 0;

        for (int i = 0; i < steps; i++) {
            double x = start + i * dx;
            sum += function(x) * dx;
        }

        result = sum;
        System.out.println(
            Thread.currentThread().getName() +
            " حسب الجزء من " + start + " إلى " + end +
            " والنتيجة = " + result
        );
    }

    public double getResult() {
        return result;
    }
}

public class NumericalIntegration {

    public static void main(String[] args) throws InterruptedException {

        double a = 0;   
        double b = 10;   
        int totalSteps = 1_000_000;
        int numThreads = 4; 

        IntegralThread[] threads = new IntegralThread[numThreads];
        double range = (b - a) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            double start = a + i * range;
            double end = start + range;
            threads[i] = new IntegralThread(start, end, totalSteps / numThreads);
            threads[i].setName("Thread-" + (i + 1));
            threads[i].start();
        }

        for (IntegralThread t : threads) {
            t.join();
        }

        double finalResult = 0;
        for (IntegralThread t : threads) {
            finalResult += t.getResult();
        }

        System.out.println("--------------------------------");
        System.out.println("النتيجة النهائية للتكامل = " + finalResult);
    }
}