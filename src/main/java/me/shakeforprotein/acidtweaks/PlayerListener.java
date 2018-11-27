package me.shakeforprotein.acidtweaks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

public class PlayerListener implements Listener {


    private AcidTweaks plugin;

    public PlayerListener(AcidTweaks main) {

        plugin = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        String World = p.getLocation().getWorld().getName();
        if ((World.equalsIgnoreCase("AcidIsland_world")) || (World.equalsIgnoreCase("AcidIsland_world_nether")) || (World.equalsIgnoreCase("AcidIsland_world_the_end"))) {
            ItemStack item = e.getItem();

            if (item.getType() == Material.POTION && item.hasItemMeta()) {
                if (item.getItemMeta() instanceof PotionMeta) {
                    final PotionMeta pmeta = (PotionMeta) item.getItemMeta();
                    final PotionData pdata = pmeta.getBasePotionData();
                    if (pdata.getType() == PotionType.WATER) {
                        p.damage(1);
                        for (String blocks : plugin.getConfig().getConfigurationSection("blocks").getKeys(false))
                            if (e.getClickedBlock().getType() == Material.matchMaterial(blocks)) {
                                e.getClickedBlock().setType(Material.getMaterial(plugin.getConfig().getConfigurationSection("blocks").get("" + blocks).toString()));
                                item.setType(Material.GLASS_BOTTLE);
                                break;
                            }
                    }

                }
            }
        }
    }

    @EventHandler
    public void onConsumeEvent(PlayerItemConsumeEvent e) {
        if ((e.getItem().getType() == Material.POTION) && (e.getItem().hasItemMeta()) && (e.getItem().getItemMeta() instanceof PotionMeta)) {
            final PotionMeta pmeta = (PotionMeta) e.getItem().getItemMeta();
            final PotionData pdata = pmeta.getBasePotionData();
            if (pdata.getType() == PotionType.WATER) {
                Player p = e.getPlayer();
                p.sendMessage("... You feel tired");
                p.damage(1);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        p.sendMessage("... You struggle to keep your eyes open");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200000, 1), true);
                        p.damage(1);
                    }
                }, 40L);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        p.sendMessage("You start to feel lighter");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1, 1), true);
                        p.damage(2);
                    }
                }, 80L);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        p.sendMessage("... Much lighter");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 2000, 1), true);
                        p.damage(2);
                    }
                }, 250L);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        p.sendMessage("... Like all the weight has been lifted off your shoulders");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200000, 1), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200000, 2), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 200000, 1), true);
                        p.damage(4);
                    }
                }, 400L);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        p.sendMessage("You feel so light, you are starting to feel unwell");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200000, 1), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200000, 1), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200000, 1), true);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200000, 1), true);
                        p.damage(4);
                    }
                }, 800L);
            }
        }
    }
}
