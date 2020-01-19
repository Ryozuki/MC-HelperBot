package xyz.ryozuki.helperbot;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question {
    private String answer;
    private int cooldown;
    private boolean broadcast;
    private Map<UUID, Long> cooldownMap = new HashMap<>();
    private Long globalCooldown;
    private Pattern pattern;

    public Question(String question, String answer, int cooldown, boolean broadcast) {
        this.answer = answer;
        this.cooldown = cooldown;
        this.broadcast = broadcast;
        this.globalCooldown = 0L;
        this.pattern = Pattern.compile(question, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    }

    public boolean matches(String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public boolean canAnswer(Player p) {
        if (broadcast) {
            return globalCooldown + (cooldown * 1000) <= System.currentTimeMillis();
        } else {
            return cooldownMap.getOrDefault(p.getUniqueId(), 0L) + (cooldown * 1000) <= System.currentTimeMillis();
        }
    }

    public boolean isBroadcasted() {
        return broadcast;
    }

    public String getAnswer(Player p, boolean usePlaceholderAPI) {
        if (broadcast) {
            globalCooldown = System.currentTimeMillis();
        } else {
            cooldownMap.put(p.getUniqueId(), System.currentTimeMillis());
        }

        if (usePlaceholderAPI) {
            return PlaceholderAPI.setPlaceholders(p, answer);
        }

        return answer;
    }
}
