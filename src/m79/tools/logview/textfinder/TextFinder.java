package m79.tools.logview.textfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hober on 2017.03.21..
 */
public class TextFinder {

    private List<Match> matches;

    private boolean virgin;

    private int idx = -1;

    public TextFinder() {

    }

    public void invalidate() {
        virgin = true;
        matches = new ArrayList<>();
    }

    public boolean isVirgin() {
        return virgin;
    }

    public boolean hasNext() {
        return !virgin && idx < matches.size() - 1;
    }

    public boolean hasPrevious() {
        return !virgin && idx > 0;
    }

    public Match next(boolean circular) {
        return hasNext() ? matches.get(++idx) : circular ? matches.get(idx = 0) : null;
    }

    public Match previous(boolean circular) {
        return hasPrevious() ? matches.get(--idx) : circular ? matches.get(idx = matches.size() - 1) : null;
    }

    public Match directedCircularNext(boolean forward) {
        return forward ? next(true) : previous(true);
    }

    public void find(String text, String regex) {
        invalidate();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            matches.add(new Match(m.start(), m.end()));
        }
        virgin = false;
        System.out.println("Found " + matches);
    }

}
