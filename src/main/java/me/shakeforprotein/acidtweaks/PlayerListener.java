package me.shakeforprotein.acidtweaks;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import java.util.ArrayList;
import java.util.Random;

public class PlayerListener implements Listener {


    private AcidTweaks plugin;

    public PlayerListener(AcidTweaks main) {

        plugin = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (plugin.getConfig().get("pourCorrosion").toString().equalsIgnoreCase("true")) {
            if (e.getClickedBlock() != null && e.getClickedBlock().getType() != Material.AIR) {
                Player p = e.getPlayer();
                String World = p.getLocation().getWorld().getName();

                if ((World.equalsIgnoreCase("AcidIsland_world")) || (World.equalsIgnoreCase("AcidIsland_world_nether")) || (World.equalsIgnoreCase("AcidIsland_world_the_end"))) {
                    ItemStack item = e.getItem();

                    if (item != null && item.getType() != null && item.getType() == Material.POTION && item.hasItemMeta()) {
                        if (item.getItemMeta() != null && item.getItemMeta() instanceof PotionMeta) {
                            final PotionMeta pmeta = (PotionMeta) item.getItemMeta();
                            final PotionData pdata = pmeta.getBasePotionData();
                            if (pdata.getType() == PotionType.WATER) {
                                p.damage(1);
                                for (String blocks : plugin.getConfig().getConfigurationSection("blocks").getKeys(false)) {
                                    if (e.getClickedBlock().getType() == Material.matchMaterial(blocks)) {
                                        e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation().add(0, 1, 0), Effect.SMOKE, 31, 3);
                                        e.getClickedBlock().setType(Material.getMaterial(plugin.getConfig().getConfigurationSection("blocks").get("" + blocks).toString()));
                                        item.setType(Material.GLASS_BOTTLE);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onConsumeEvent(PlayerItemConsumeEvent e) {
        if (plugin.getConfig().get("deadlyDrink").toString().equalsIgnoreCase("true")) {
            String World = e.getPlayer().getWorld().getName();
            String pWorld;
            if ((World.equalsIgnoreCase("AcidIsland_world")) || (World.equalsIgnoreCase("AcidIsland_world_nether")) || (World.equalsIgnoreCase("AcidIsland_world_the_end"))) {

                if ((e.getItem().getType() == Material.POTION) && (e.getItem().hasItemMeta()) && (e.getItem().getItemMeta() instanceof PotionMeta)) {
                    final PotionMeta pmeta = (PotionMeta) e.getItem().getItemMeta();
                    final PotionData pdata = pmeta.getBasePotionData();
                    if (pdata.getType() == PotionType.WATER) {
                        Player p = e.getPlayer();
                        p.sendMessage("... You feel tired");
                        p.damage(1);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                if (p.getWorld().getName().contains("AcidIsland")) {
                                    p.sendMessage("... You struggle to keep your eyes open");
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200000, 1), true);
                                    p.damage(1);
                                } else {
                                    ClearEffects(p);
                                }
                            }

                        }, 40L);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                if (p.getWorld().getName().contains("AcidIsland")) {
                                    p.sendMessage("You start to feel lighter");
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1, 1), true);
                                    p.damage(2);
                                } else {
                                    ClearEffects(p);
                                }
                            }
                        }, 80L);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                if (p.getWorld().getName().contains("AcidIsland")) {
                                    p.sendMessage("... Much lighter");
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 2000, 1), true);
                                    p.damage(2);
                                } else {
                                    ClearEffects(p);
                                }
                            }
                        }, 250L);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                if (p.getWorld().getName().contains("AcidIsland")) {
                                    p.sendMessage("... Like all the weight has been lifted off your shoulders");
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200000, 1), true);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200000, 2), true);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 200000, 1), true);
                                    p.damage(4);
                                } else {
                                    ClearEffects(p);
                                }
                            }
                        }, 400L);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                if (p.getWorld().getName().contains("AcidIsland")) {
                                    p.sendMessage("You feel so light, you are starting to feel unwell");
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200000, 1), true);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200000, 1), true);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200000, 1), true);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200000, 1), true);
                                    p.damage(4);
                                } else {
                                    ClearEffects(p);
                                }
                            }
                        }, 800L);
                    }
                }
            }
        }
    }

    private ArrayList<String> cooldown = new ArrayList<String>();
    private final BlockFace[] sides = new BlockFace[]{BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (plugin.getConfig().get("worldCorrosion").toString().equalsIgnoreCase("true")) {
            Player p = e.getPlayer();
            String world = e.getPlayer().getWorld().getName();
            if (world.contains("AcidIsland")) {

                if (!cooldown.contains(p.getUniqueId().toString())) {
                    cooldown.add(p.getUniqueId().toString());
                    p.sendMessage("onlist");
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            cooldown.remove(p.getUniqueId().toString());
                            p.sendMessage("offList");
                        }
                    }, 1200L);

                    Integer pX = (int) p.getLocation().getX();
                    Integer pY = (int) p.getLocation().getY();
                    Integer pZ = (int) p.getLocation().getZ();


                    int xRadius = plugin.getConfig().getInt("xCorr");
                    int yRadius = plugin.getConfig().getInt("yCorr");
                    int zRadius = plugin.getConfig().getInt("zCorr");
                    Block middle = e.getTo().getBlock();
                    Block targetBlock;
                    Block adjacentBlock;
                    int upper = 100;
                    int lower = 0;
                    for (int x = xRadius; x >= -xRadius; x--) {
                        for (int y = yRadius; y >= -yRadius; y--) {
                            for (int z = zRadius; z >= -zRadius; z--) {
                                targetBlock = middle.getRelative(x, y, z);
                                for (String blocks : plugin.getConfig().getConfigurationSection("blocks").getKeys(false))
                                    if (targetBlock.getType() == Material.matchMaterial(blocks)) {
                                        for (BlockFace side : sides) {
                                            adjacentBlock = targetBlock.getRelative(side);
                                            if (adjacentBlock.getType() == Material.WATER) {

                                                for (String cblock : plugin.getConfig().getConfigurationSection("blocks").getKeys(false)) {
                                                    if (targetBlock.getType() == Material.matchMaterial(cblock)) {
                                                        int ran = (int) (Math.random() * (upper - lower)) + lower;
                                                        if (ran < 15) {
                                                            targetBlock.setType(Material.getMaterial(plugin.getConfig().getConfigurationSection("blocks").getString(cblock)));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }

    public void ClearEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }
}