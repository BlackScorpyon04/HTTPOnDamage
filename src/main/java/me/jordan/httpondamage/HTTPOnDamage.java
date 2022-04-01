package me.jordan.httpondamage;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class HTTPOnDamage extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void takeDamage(EntityDamageEvent e) throws IOException, InterruptedException {
        if (e.getEntity().getType() == EntityType.PLAYER){
            Player p = (Player) e.getEntity();
            if (p.getName().equalsIgnoreCase(getConfig().getString("name"))){
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(getConfig().getString("url")))
                        .build();

                client.send(request,
                        HttpResponse.BodyHandlers.ofString());
            }
        }
    }
}
