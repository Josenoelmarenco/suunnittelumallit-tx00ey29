/** Concrete Observer: a wall display that reacts differently to hot/cold. */
public class WindowDisplay implements Observer {
    @Override
    public void update(int temperature) {
        String mood = temperature <= 0 ? "freezing" : temperature < 15 ? "chilly"
                    : temperature < 25 ? "pleasant" : "hot";
        System.out.println("[Window display] " + temperature + " C - looks " + mood + " today.");
    }
}
