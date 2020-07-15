public class NBody {
    public static double readRadius(String universeFileName) {
        In in = new In(universeFileName);

        in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String universeFileName) {
        In in = new In(universeFileName);
        double xP, yP, xV, yV, m;
        String img;

        int numPlanets = in.readInt();
        in.readDouble();
        Body[] bodies = new Body[numPlanets];
        for (int i = 0; i < numPlanets; i += 1) {
            xP = in.readDouble();
            yP = in.readDouble();
            xV = in.readDouble();
            yV = in.readDouble();
            m = in.readDouble();
            img = in.readString();
            bodies[i] = new Body(xP, yP, xV, yV, m, img);
        }
        return bodies;
    }

    public static void main(String[] args) {
        double currTime = 0;
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double universeRadius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        int numPlanets = bodies.length;
        double[] xForces = new double[numPlanets];
        double[] yForces = new double[numPlanets];

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-universeRadius, universeRadius);

        while (currTime < T) {
            StdDraw.clear();
            StdDraw.picture(0, 0, "./images/starfield.jpg");
            for (int i = 0; i < numPlanets; i += 1) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            for (int i = 0; i < numPlanets; i += 1) {
                bodies[i].update(dt, xForces[i], yForces[i]);
                bodies[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            currTime += dt;
        }

        StdOut.printf("%d\n", numPlanets);
        StdOut.printf("%.2e\n", universeRadius);
        for (int i = 0; i < numPlanets; i += 1) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                          bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
