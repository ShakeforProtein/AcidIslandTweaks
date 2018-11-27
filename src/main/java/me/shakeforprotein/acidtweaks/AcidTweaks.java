package me.shakeforprotein.acidtweaks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AcidTweaks extends JavaPlugin {

    private PlayerListener pl;
    public AcidTweaks(){}

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        System.out.println("Acid Island Tweaks Started");
        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    @Override
    public void onDisable() {
    System.out.println("Acid Island Tweaks Shutdown");
    }
}
