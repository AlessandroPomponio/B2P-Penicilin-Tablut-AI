package b2p.utils;

public class Timer {

    private final long duration;
    private long startTime;

    public Timer(int maxSeconds) {
        this.duration = 1000L * maxSeconds;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public boolean expired() {
        return System.currentTimeMillis() > startTime + duration;
    }

}
