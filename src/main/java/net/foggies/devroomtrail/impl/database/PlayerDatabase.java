package net.foggies.devroomtrail.impl.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import me.lucko.helper.utils.TimeUtil;
import net.foggies.devroomtrail.ParkourPlugin;
import net.foggies.devroomtrail.api.IPlayerDatabase;
import net.foggies.devroomtrail.impl.obj.LeaderboardPlayer;
import net.foggies.devroomtrail.impl.obj.ParkourAttempt;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerDatabase implements IPlayerDatabase {

    private ParkourPlugin parkourPlugin;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> serverCollection;

    public PlayerDatabase(ParkourPlugin parkourPlugin) {
        this.parkourPlugin = parkourPlugin;
        connect();
    }

    private void connect() {
        this.mongoClient = new com.mongodb.MongoClient(new MongoClientURI(parkourPlugin.getMongoURI()));
        this.mongoDatabase = this.mongoClient.getDatabase(parkourPlugin.getDatabase());
        this.serverCollection = this.mongoDatabase.getCollection(parkourPlugin.getCollection());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB.");
    }

    @Override
    public void storePlayer(ParkourAttempt parkourAttempt) {

        if (containPlayer(parkourAttempt.getPlayer().getUniqueId())) {
            updatePlayer(parkourAttempt);
        } else {
            Document document = new Document("uuid", parkourAttempt.getPlayer().getUniqueId().toString());
            document.append("attempts", parkourAttempt.getCurrentTries());
            document.append("elapsed_time_seconds", parkourAttempt.getStopWatch().getTime(TimeUnit.SECONDS));
            serverCollection.insertOne(document);
        }
    }

    private void updatePlayer(ParkourAttempt parkourAttempt) {
        Document document = getPlayerDocument(parkourAttempt.getPlayer().getUniqueId());
        if (document != null) {
            long attemptTime = parkourAttempt.getStopWatch().getTime(TimeUnit.SECONDS);
            long time = document.getLong("elapsed_time_seconds");
            if (time > attemptTime) {
                Bson updates = Updates.combine(
                        Updates.set("elapsed_time_seconds", attemptTime),
                        Updates.set("attempts", parkourAttempt.getCurrentTries())
                );
                serverCollection.updateOne(document, updates);
            }
        }
    }

    @Override
    public LinkedHashSet<LeaderboardPlayer> getLeaderboard() {
        LinkedHashSet<LeaderboardPlayer> leaderboardPlayers = new LinkedHashSet<>();
        FindIterable<Document> cursor = serverCollection.find().sort(new Document("elapsed_time_seconds", 1)).limit(5);
        for (Document document : cursor) {
            LeaderboardPlayer leaderboardPlayer = new LeaderboardPlayer(
                    UUID.fromString(document.getString("uuid")),
                    document.getInteger("attempts"),
                    TimeUtil.toShortForm(document.getLong("elapsed_time_seconds"))
            );
            leaderboardPlayers.add(leaderboardPlayer);
        }
        return leaderboardPlayers;
    }

    @Override
    public boolean containPlayer(UUID uuid) {
        return getPlayerDocument(uuid) != null;
    }

    @Override
    public long getElapsedTime(UUID uuid) {
        if (!containPlayer(uuid)) return 0;
        return getPlayerDocument(uuid).getLong("elapsed_time_seconds");
    }

    @Override
    public Document getPlayerDocument(UUID uuid) {
        return serverCollection.find(new Document("uuid", uuid.toString())).first();
    }

}
