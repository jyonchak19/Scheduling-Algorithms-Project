import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Project3Driver {
    public static void main(String[] args) {
        // 1. read in the input from jobs.txt and parse it

        File jobsFile = new File("jobs.txt");
        ArrayList<Job> jobList = new ArrayList<>();
        try {
            Scanner input = new Scanner(jobsFile);

            // best option: use a StringTokenizer.
            // todo finish parsing
            // Scanner input = new Scanner(jobsFile);
            // while(input.hasNextLine()) {
            while(input.hasNextLine()) {
                StringTokenizer st = new StringTokenizer(input.nextLine(), "\t");
                String jobName = st.nextToken();
                int startTime = Integer.parseInt(st.nextToken());
                int duration = Integer.parseInt(st.nextToken());

                // construct an Array of Jobs
                Job currentJob = new Job(jobName, startTime, duration);
                jobList.add(currentJob);
            }

        } catch(Exception e) {
            System.out.println("Error - file not found!");
            e.printStackTrace();
        }


        // 2. reading in the type of sched algo
        switch(args[0]) {
            case "RR":
                Project3SchedulingAlgorithms.RoundRobin(jobList);
                break;
            case "SRT":
                Project3SchedulingAlgorithms.ShortestRemainingTime(jobList);
                break;
            case "FB":
                Project3SchedulingAlgorithms.Feedback(jobList);
                break;
            case "ALL":
                Project3SchedulingAlgorithms.RoundRobin(jobList);
                Project3SchedulingAlgorithms.ShortestRemainingTime(jobList);
                Project3SchedulingAlgorithms.Feedback(jobList);
                break;
            default:
                System.out.println("Incorrect input");
        }

        // 3. run that algorithm with the parsed data
    }
}
