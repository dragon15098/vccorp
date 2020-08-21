package main;
public class Timer {
    private long start = 0;
    public Timer() { play(); }
    public double check() { return (System.nanoTime()-start); }
    public void play() { start = System.nanoTime(); }
}