package amrelk.frc.ramsetetester;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RamseteTest extends TimedRobot {

    HashMap<String, Trajectory> map;
    Trajectory traj;
    Pose2D pos;
    Pose2D posd;
    Pose2D err = new Pose2D();
    int counter = 0;

    @Override
    public void robotInit() {
        System.out.println("robot init!");
        map = getTrajectoriesFromDirectory("/home/amrelk/robotics/Competition2018/src/main/resources/paths/");
        for (String key : map.keySet()) {
            traj = map.get(key);
            pos = new Pose2D(traj.get(0).x, traj.get(0).y, traj.get(0).heading);
        }
    }

    @Override
    public void robotPeriodic() {
        if (counter < traj.length()) {
            posd = new Pose2D(traj.get(counter).x, traj.get(counter).y, traj.get(counter).heading);
            err.x = Math.cos(pos.theta) * (posd.x - pos.x) + Math.sin(pos.theta) * (posd.y - pos.y);
            err.y = Math.cos(pos.theta) * (posd.y - pos.y) - Math.sin(pos.theta) * (posd.x - pos.x);
            err.theta = posd.theta - pos.theta;
            System.out.println(err);
            counter++;
        }
    }

    public HashMap<String, Trajectory> getTrajectoriesFromDirectory(String dir) {
        HashMap<String, Trajectory> paths = new HashMap<>();

        ArrayList<File> filesInFolder;

        filesInFolder = listf(dir);
        for ( int i = filesInFolder.size() - 1; i >= 0; i--) {
            if (!filesInFolder.get(i).getName().contains("_source_Jaci.csv")) {
                filesInFolder.remove(i);
            }
        }

        for ( File traj : filesInFolder ) {
            paths.put(traj.getName().replace("_source_Jaci.csv", ""), Pathfinder.readFromCSV(traj));
        }

        return paths;
    }

    public ArrayList<File> listf(String directoryName) {
        File directory = new File(directoryName);

        ArrayList<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        return resultList;
    }
}
