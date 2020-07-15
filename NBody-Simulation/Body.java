public class Body {
    public double xxPos, yyPos, xxVel, yyVel, mass;
    public String imgFileName;

    public static final double GRAVITY_CONSTANT = 6.67e-11;

    public Body(double xP, double yP, double xV,
                double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double xPosDiff = b.xxPos - xxPos;
        double yPosDiff = b.yyPos - yyPos;
        return Math.sqrt(xPosDiff * xPosDiff + yPosDiff * yPosDiff);
    }

    public double calcForceExertedBy(Body b) {
        double distance = calcDistance(b);
        return GRAVITY_CONSTANT * mass * b.mass / (distance * distance);
    }

    public double calcForceExertedByX(Body b) {
        double xPosDiff = b.xxPos - xxPos;
        double force = calcForceExertedBy(b);
        double distance = calcDistance(b);
        return force * xPosDiff / distance;
    }

    public double calcForceExertedByY(Body b) {
        double yPosDiff = b.yyPos - yyPos;
        double force = calcForceExertedBy(b);
        double distance = calcDistance(b);
        return force * yPosDiff / distance;
    }

    public double calcNetForceExertedByX(Body[] bodies) {
        double netForceX = 0;
        for (Body b : bodies) {
            if (!this.equals(b)) {
                netForceX += calcForceExertedByX(b);
            }
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Body[] bodies) {
        double netForceY = 0;
        for (Body b : bodies) {
            if (!this.equals(b)) {
                netForceY += calcForceExertedByY(b);
            }
        }
        return netForceY;
    }

    public void update(double t, double netForceX, double netForceY) {
        double xxAcc = netForceX / mass;
        double yyAcc = netForceY / mass;
        xxVel += xxAcc * t;
        yyVel += yyAcc * t;
        xxPos += xxVel * t;
        yyPos += yyVel * t;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
    }
}
