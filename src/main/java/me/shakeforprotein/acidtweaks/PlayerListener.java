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

import java.util.Set;

public class PlayerListener implements Listener {


    private AcidTweaks pl;

    public void PlayerListener(AcidTweaks instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
        this.pl = instance;
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

                        for (String blocks : pl.getConfig().getConfigurationSection("blocks").getKeys(false))
                            if (e.getClickedBlock().getType() == Material.matchMaterial(blocks)) {
                                e.getClickedBlock().setType(Material.getMaterial(pl.getConfig().getConfigurationSection("blocks").get("" + blocks).toString()));
                            }
                    }
                    item.setType(Material.AIR);
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
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                    public void run() {
                        p.sendMessage("... You struggle to keep your eyes open");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200000, 1), true);
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                            public void run() {
                                p.sendMessage("You start to feel lighter");
                                p.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 1), true);
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                                    public void run() {
                                        p.sendMessage("... Much lighter");
                                        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 2000, 1), true);
                                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                                            public void run() {
                                                p.sendMessage("... Like all the weight has been lifted off your shoulders");
                                                p.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 2), true);
                                                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 200000, 1), true);
                                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                                                    public void run() {
                                                        p.sendMessage("You feel so light, you are starting to feel unwell");
                                                        p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200000, 1), true);
                                                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200000, 1), true);
                                                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200000, 1), true);
                                                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200000, 1), true);
                                                    }
                                                }, 40L);
                                            }
                                        }, 40L);

                                    }
                                }, 40L);
                            }
                        }, 40L);
                    }

                }, 40L);
            }
        }
    }
}
