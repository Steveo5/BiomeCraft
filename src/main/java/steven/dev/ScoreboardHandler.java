package steven.dev;

import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;

public class ScoreboardHandler extends Handler {
    private ScoreboardLibrary scoreboardLibrary;

    public ScoreboardHandler() {
        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(BiomeCraft.getInstance());
        } catch (NoPacketAdapterAvailableException e) {
            // If no packet adapter was found, you can fallback to the no-op implementation:
            scoreboardLibrary = new NoopScoreboardLibrary();
        }
    }

    public void close() {
        // On plugin shutdown:
        scoreboardLibrary.close();
    }

    public ScoreboardLibrary getScoreboardLibrary() {
        return this.scoreboardLibrary;
    }
}
