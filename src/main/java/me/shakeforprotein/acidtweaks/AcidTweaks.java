package me.shakeforprotein.acidtweaks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        System.out.println("Acid Island Tweaks Loaded");
    }

    @Override
    public void onDisable() {
        System.out.println("Acid Island Tweaks Shutdown");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            if ((cmd.getName().equalsIgnoreCase("acidtweaks")) && (args.length == 1) && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
            }
        }
        return true;
    }
}
