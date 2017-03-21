package m79.tools.logview.textfinder;

/**
 * Created by Hober on 2017.03.21..
 */
public class Match {

    public final int startIndex, endIndex;

    Match(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "[" + startIndex + ".." + endIndex + "]";
    }
}
