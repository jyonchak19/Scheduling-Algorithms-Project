import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Project3SchedulingAlgorithms {
    private static final int QUANTUM = 1;

    public static void RoundRobin(ArrayList<Job> jobs) {
        System.out.println("running roundrobin...");
        // Create arrays duration[], arrivalTime, remainingDuration[] = duration
        int[] duration, remainingDuration, arrivalTime;
        duration = new int[jobs.size()];
        arrivalTime = new int[jobs.size()];
        remainingDuration = new int[jobs.size()];
        for(int i = 0; i < jobs.size(); i++) {
            duration[i] = jobs.get(i).getDuration();
            remainingDuration[i] = duration[i];
            arrivalTime[i] = jobs.get(i).getStartTime();
        }
        // Initialize time t = 0, temp, lastArrival and currentJob = 0;
        int t = 0, currentJob = 0, lastArrival, temp;

        Queue<Integer> jobQueue = new LinkedList<>();
        jobQueue.add(0);
        lastArrival = 0;

        for(Job job: jobs){
            System.out.print(job.getName() + " ");
        }
        System.out.println();

         while(jobQueue.peek() != null) {
             if(lastArrival + 1 < arrivalTime.length && arrivalTime[lastArrival + 1] == t){
                 jobQueue.add(lastArrival + 1);
                 lastArrival++;
             }
             if(remainingDuration[currentJob] >= QUANTUM) {
                 for(int i = currentJob * 2; i > 0; i--){
                     System.out.print(" ");
                 }
                 System.out.print("X \n");
                 t++;
                 remainingDuration[currentJob] -= QUANTUM;
                 temp = jobQueue.poll();
                 jobQueue.add(temp);
                 currentJob = jobQueue.peek();
                 }
             else {
                 //t += remainingDuration[currentJob];
                 remainingDuration[currentJob] = 0;
                 jobQueue.remove(currentJob);
                 if(jobQueue.peek() != null) {
                     currentJob = jobQueue.peek();
                 }
             }
         }
        System.out.println();
    }



    public static void ShortestRemainingTime(ArrayList<Job> jobs) {
        System.out.println("running shortest remaining time...");
        //  Create arrays duration[], arrivalTime[], and remainingDuration[] = duration
        int[] duration, arrivalTime, remainingDuration;
        duration = new int[jobs.size()];
        arrivalTime = new int[jobs.size()];
        remainingDuration = new int[jobs.size()];
        for(int i = 0; i < jobs.size(); i++) {
            duration[i] = jobs.get(i).getDuration();
            remainingDuration[i] = duration[i];
            arrivalTime[i] = jobs.get(i).getStartTime();
        }

        ArrayList<Integer> jobsInOrder = new ArrayList<>();
        jobsInOrder.add(0);

        // Initialize time t, nextArrival, and currentJob
        int t = 0, nextArrival = 1, currentJob = jobsInOrder.get(0);;

        for(Job job: jobs){
            System.out.print(job.getName() + " ");
        }
        System.out.println();

        while(!jobsInOrder.isEmpty()){
            if(nextArrival < jobs.size() && t == arrivalTime[nextArrival]){
                for(int i = 0; i <= jobsInOrder.size(); i++){
                    if(i == jobsInOrder.size()){
                        jobsInOrder.add(i, nextArrival);
                        nextArrival++;
                        break;
                    }
                    else if(remainingDuration[jobsInOrder.get(i)] >= remainingDuration[nextArrival]){
                        jobsInOrder.add(i, nextArrival);
                        nextArrival++;
                        break;
                    }
                }
            }
            currentJob = jobsInOrder.get(0);
            if(remainingDuration[currentJob] == 0){
                jobsInOrder.remove(0);
            }
            else {
                for(int i = currentJob * 2; i > 0; i--){
                    System.out.print(" ");
                }
                System.out.print("X \n");
                t++;
                remainingDuration[currentJob]--;
            }
        }
        System.out.println();
    }

    public static void Feedback(ArrayList<Job> jobs) {
        System.out.println("running feedback...");
        AtomicInteger t = new AtomicInteger(0);
        AtomicInteger nextArrival = new AtomicInteger(1);
        //  Create arrays duration[], arrivalTime[], and remainingDuration[] = duration
        int[] duration, arrivalTime, remainingDuration;
        duration = new int[jobs.size()];
        arrivalTime = new int[jobs.size()];
        remainingDuration = new int[jobs.size()];
        for(int i = 0; i < jobs.size(); i++) {
            duration[i] = jobs.get(i).getDuration();
            remainingDuration[i] = duration[i];
            arrivalTime[i] = jobs.get(i).getStartTime();
        }

        for(Job job: jobs){
            System.out.print(job.getName() + " ");
        }
        System.out.println();

        Queue<Integer> queue1 = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();
        Queue<Integer> queue3 = new LinkedList<>();

        queue1.add(0);

        executeQueue1(queue1, queue2, queue3, t, nextArrival, arrivalTime, remainingDuration);
        if(!queue2.isEmpty()){
            executeQueue2(queue1, queue2, queue3, t, nextArrival, arrivalTime, remainingDuration);
        }
        if(!queue3.isEmpty()){
            executeQueue3(queue1, queue2, queue3, t, nextArrival, arrivalTime, remainingDuration);
        }
    }

    private static void executeQueue1(Queue<Integer> queue1, Queue<Integer> queue2, Queue<Integer> queue3,
                                      AtomicInteger t, AtomicInteger nextArrival, int[] arrivalTime, int[] remainingDuration){
        int currentJob;

        while(!queue1.isEmpty() || (nextArrival.get() < arrivalTime.length &&
                t.get() == arrivalTime[nextArrival.get()])){
            if(nextArrival.get() < arrivalTime.length && t.get() == arrivalTime[nextArrival.get()]){
                queue1.add(nextArrival.get());
                nextArrival.set(nextArrival.get() + 1);
            }
            currentJob = queue1.peek();
            for(int i = currentJob * 2; i > 0; i--){
                System.out.print(" ");
            }
            System.out.print("X \n");
            remainingDuration[currentJob]--;
            t.set(t.get() + 1);
            if(remainingDuration[currentJob] != 0) {
                queue2.add(queue1.poll());
            }
        }
    }

    private static void executeQueue2(Queue<Integer> queue1, Queue<Integer> queue2, Queue<Integer> queue3,
                                      AtomicInteger t, AtomicInteger nextArrival, int[] arrivalTime, int[] remainingDuration){
        final int QUANTUM = 4;
        int currentJob;

        while(!queue2.isEmpty() || nextArrival.get() < arrivalTime.length &&
                t.get() == arrivalTime[nextArrival.get()]){
            if(nextArrival.get() < arrivalTime.length && t.get() == arrivalTime[nextArrival.get()]){
                queue1.add(nextArrival.get());
                nextArrival.set(nextArrival.get() + 1);
                executeQueue1(queue1, queue2, queue3, t, nextArrival, arrivalTime, remainingDuration);
            }
            currentJob = queue2.peek();
            for(int i = 0; i < QUANTUM; i++){
                if(nextArrival.get() < arrivalTime.length && t.get() == arrivalTime[nextArrival.get()]) {
                    queue1.add(nextArrival.get());
                    nextArrival.set(nextArrival.get() + 1);
                    executeQueue1(queue1, queue2, queue3, t, nextArrival, arrivalTime, remainingDuration);
                }
                if(remainingDuration[currentJob] != 0){
                    for(int j = currentJob * 2; j > 0; j--){
                        System.out.print(" ");
                    }
                    System.out.print("X \n");
                    remainingDuration[currentJob]--;
                    t.set(t.get() + 1);
                }
            }
            if(remainingDuration[currentJob] != 0){
                queue3.add(queue2.poll());
            }
            else{
                queue2.poll();
            }
        }
    }

    private static void executeQueue3(Queue<Integer> queue1, Queue<Integer> queue2, Queue<Integer> queue3,
                                      AtomicInteger t, AtomicInteger nextArrival, int[] arrivalTime, int[] remainingDuration){
        int currentJob;

        while(!queue3.isEmpty() || nextArrival.get() < arrivalTime.length &&
                t.get() == arrivalTime[nextArrival.get()]){
            if(nextArrival.get() < arrivalTime.length && t.get() == arrivalTime[nextArrival.get()]){
                queue1.add(nextArrival.get());
                nextArrival.set(nextArrival.get() + 1);
                executeQueue1(queue1, queue2, queue3, t, nextArrival, arrivalTime, remainingDuration);
                if(!queue2.isEmpty()){
                    executeQueue2(queue1, queue2, queue3, t, nextArrival, arrivalTime, remainingDuration);
                }
            }
            currentJob = queue3.peek();
            for(int i = currentJob * 2; i > 0; i--){
                System.out.print(" ");
            }
            System.out.print("X \n");
            remainingDuration[currentJob]--;
            t.set(t.get() + 1);
            if(remainingDuration[currentJob] == 0){
                queue3.poll();
            }
        }
    }

}