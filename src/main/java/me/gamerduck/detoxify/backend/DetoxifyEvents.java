package me.gamerduck.detoxify.backend;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.gamerduck.detoxify.DetoxifyPlugin;
import me.gamerduck.detoxify.api.Config;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class DetoxifyEvents implements Listener {

    private final DetoxifyONNX mod;
//    private final BukkitAudiences audiences;
    private Double toxicity;
    private Double severe_toxicity;
    private Double obscene;
    private Double threat;
    private Double insult;
    private Double identity_attack;

    public DetoxifyEvents(DetoxifyPlugin plugin) throws Exception {
        mod = new DetoxifyONNX(plugin.libFolder().resolve("detoxify_quantized.onnx").toString());
//        this.audiences = plugin.adventure();
        toxicity = Config.getDouble("toxicity");
        severe_toxicity = Config.getDouble("severe_toxicity");
        obscene = Config.getDouble("obscene");
        threat = Config.getDouble("threat");
        insult = Config.getDouble("insult");
        identity_attack = Config.getDouble("identity_attack");
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        String message = PlainTextComponentSerializer.plainText().serialize(e.message());
        var sender = e.getPlayer();
        try {
            Map<String, Float> result = mod.predict(message);
            if (result.get("toxicity") > toxicity
                    || result.get("severe_toxicity") > severe_toxicity
                    || result.get("obscene") > obscene
                    || result.get("threat") > threat
                    || result.get("insult") > insult
                    || result.get("identity_attack") > identity_attack) {
                e.setCancelled(true);
                sender.sendMessage("§cYour message was removed for violating chat rules.");
//                audiences.filter(cmdsender -> cmdsender.hasPermission(""))
//                        .sendMessage(Component.text(String.format("%s's message has been removed [%s]", e.getPlayer().getName(), message)));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
