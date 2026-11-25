import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The saved recommendation lists.
 *
 * Every saved recommendation can also act as a prototype: the user can clone
 * any of them as the starting point for a new list.
 */
public class RecommendationCatalog {

    private final List<Recommendation> recommendations = new ArrayList<>();

    public void save(Recommendation recommendation) {
        recommendations.add(recommendation);
    }

    /** Returns the recommendation at the given position (0-based). */
    public Recommendation get(int index) {
        return recommendations.get(index);
    }

    public int size() {
        return recommendations.size();
    }

    public List<Recommendation> getAll() {
        return Collections.unmodifiableList(recommendations);
    }
}
