package models.sink;

public class ConsoleSink extends Sink{

    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
