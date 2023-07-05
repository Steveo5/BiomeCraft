package steven.dev;

import com.nthbyte.dialogue.DialogueAPI;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import steven.dev.quest.*;
import steven.dev.quest.journal.QuestJournalSaveTask;

import java.util.*;
import java.util.logging.Level;

public final class BiomeCraft extends JavaPlugin {

    private static List<Spawn> spawns = new ArrayList<Spawn>();
    private static final HashMap<UUID, BiomeCraftPlayer> players = new HashMap<>();
//    private static QuestHandler questHandler;
//    private static QuestUIHandler questUIHandler;
//    private static QuestDataHandler questDataHandler;

    private final List<Handler> handlers = new ArrayList<>();

    @Override
    public void onEnable() {
        DialogueAPI.hook(this);
        this.saveDefaultConfig();
        this.getCommand("biomecraft").setExecutor(new BiomeCraftCommandListener());
        this.getCommand("quest").setExecutor(new QuestCommandExecutor());
        loadFromConfig();
        getServer().getPluginManager().registerEvents(new BiomeCraftEntityListener(), this);
        getServer().getPluginManager().registerEvents(new BiomeCraftPlayerListener(), this);
        getServer().getPluginManager().registerEvents(new QuestListener(), this);

        this.registerHandler(new ScoreboardHandler());
        this.registerHandler(new QuestDataHandler());
        this.registerHandler(new QuestHandler());
        this.registerHandler(new QuestJournalDataHandler());


        try {
            BiomeCraft.getInstance().getHandler(QuestDataHandler.class).loadQuestsFromDisk(BiomeCraft.getInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        new QuestJournalSaveTask().runTaskTimer(this, 1200L, 1200L);
    }

    @Override
    public void onDisable() {
        try {
            getHandler(ScoreboardHandler.class).close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromConfig() {
        BiomeCraft.getProvidingPlugin(BiomeCraft.class).reloadConfig();
        spawns.clear();

        for (Map<?, ?> map : BiomeCraft.getProvidingPlugin(BiomeCraft.class).getConfig().getMapList("spawns")) {
            EntityType entityType = EntityType.valueOf(((String)map.get("entity-type")).toUpperCase());
            boolean canSpawn = (Boolean)map.get("can-spawn");
            Biome[] biomes = null;

            if(map.containsKey("biomes") && map.get("biomes") != null) {
                biomes = Arrays.stream(((String)map.get("biomes")).split(","))
                        .map(Biome::valueOf)
                        .toArray(Biome[]::new);
            }

            spawns.add(new Spawn(entityType, canSpawn, biomes));
        }
    }

    public static List<Spawn> getSpawns() {
        return spawns;
    }

    public static BiomeCraft getInstance() {
        return (BiomeCraft) BiomeCraft.getProvidingPlugin(BiomeCraft.class);
    }

    public void registerHandler(Handler h) {
        try {
            Handler foundHandler = this.getHandler(h.getClass());
            this.log(Level.WARNING, "Tried to register handler " + foundHandler.getClass().getName() + " twice, only one may be registered");
        } catch (ClassNotFoundException e) {
            this.handlers.add(h);
        }
    }

    @NotNull
    public <T extends Handler> T getHandler(Class<T> classType) throws ClassNotFoundException {
        for (Handler h : this.handlers) {
            if (h.getClass() == classType) {
                return classType.cast(h);
            }
        }

        throw new ClassNotFoundException();
    }

    public void log(Level level, String message) {
        this.getLogger().log(level, "[BiomeCraft] " + message);
    }

    public static BiomeCraftPlayer getPlayer(Player player) {
        if (players.containsKey(player.getUniqueId())) {
            return players.get(player.getUniqueId());
        }

        BiomeCraftPlayer wrappedPlayer = new BiomeCraftPlayer(player);
        players.put(player.getUniqueId(), wrappedPlayer);

        return wrappedPlayer;
    }

    public static List<BiomeCraftPlayer> getPlayers() {
        return players.values().stream().toList();
    }

    static void removePlayer(BiomeCraftPlayer player) {
        players.remove(player.getRoot().getUniqueId());
    }
}
