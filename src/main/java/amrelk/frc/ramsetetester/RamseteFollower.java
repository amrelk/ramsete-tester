package amrelk.frc.ramsetetester;

public class RamseteFollower {
    private Pose2D currentPose = new Pose2D();
    private Pose2D targetPose = new Pose2D();
    private double v, w;
    private final double
            b = 0.7,
            zeta = 0.7,
            k2 = b;

    private double k1(double v_d, double w_d) {
        return 2 * zeta * Math.sqrt(Math.pow(w_d, 2) + b * Math.pow(v_d, 2));
    }

    private double k3(double v_d, double w_d) {
        return k1(v_d, w_d);
    }

    private double e_x() {
        return Math.cos(currentPose.theta) * (targetPose.x - currentPose.x) + Math.sin(currentPose.theta) * (targetPose.y - currentPose.y);
    }

    private double e_y() {
        return Math.cos(currentPose.theta) * (targetPose.y - currentPose.y) + Math.sin(currentPose.theta) * (targetPose.x - currentPose.x);
    }

    private double e_theta() {
        return targetPose.theta - currentPose.theta;
    }

    private double v_d() {
        return 0;
    }

    private double w_d() {
        return 0;
    }

    public double v() {
        return v_d() * Math.cos(e_theta()) + k1(v_d(), w_d()) * e_x();
    }


}
