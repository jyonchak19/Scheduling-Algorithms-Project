public class Job {
    private final String name;
    private final int startTime;
    private final int duration;

    public Job(String name, int startTime, int duration) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration(){
        return duration;
    }
}
