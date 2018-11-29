package me.shakeforprotein.acidtweaks;

import org.bukkit.plugin.java.JavaPlugin;

public final class AcidTweaks extends JavaPlugin {

    private PlayerListener pl;
    private UpdateChecker uc;
    public AcidTweaks(){}

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        System.out.println("Acid Island Tweaks Started");
        getConfig().options().copyDefaults(true);
        getConfig().set("version", this.getDescription().getVersion());
        saveConfig();
        System.out.println("Config updated");
        this.uc = new UpdateChecker(this);
        uc.getCheckDownloadURL();
        System.out.println("Acid Island Tweaks Loadewd");
    }

    @Override
    public void onDisable() {
        saveConfig();
        System.out.println("Acid Island Tweaks Shutdown");
    }
}
