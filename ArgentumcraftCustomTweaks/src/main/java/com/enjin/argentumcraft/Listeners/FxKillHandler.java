package com.enjin.argentumcraft.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import com.enjin.argentumcraft.ArgentumcraftCustomTweaks.FxRunnable;
import com.enjin.argentumcraft.ArgentumcraftCustomTweaks.Resources;

public class FxKillHandler implements Listener{
	private int NUMBER_OF_ZOMBIES = 6;
	private String player = "Fxz_y";
	private int time = 216000;
	
	public FxKillHandler(){
		Bukkit.getPluginManager().registerEvents(this, Resources.instance);
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent event){
		if (event.getEntityType() == EntityType.SNOWMAN){
			//Bukkit.getPluginManager().getPlugin("GUIAPI").getLogger().info("snowman killed");
			if (event.getEntity().getCustomName().contains("The ghost of Fx's past")){
				//Bukkit.getPluginManager().getPlugin("GUIAPI").getLogger().info("fx is naughty");
				spawn(event.getEntity().getLocation().clone());
				Bukkit.getServer().getPlayerExact(player).sendMessage(ChatColor.RED+"Naughty Fx");
			}
		}
	}
	
	@EventHandler
	public void onLogin(PlayerJoinEvent event){
		if (event.getPlayer().getName().contains(player)){
			//Bukkit.getPluginManager().getPlugin("GUIAPI").getLogger().info("logged");
			BukkitTask task = new FxRunnable().runTaskTimer(Resources.instance,time,time);
		}
	}
	
	private void spawn(Location loc){
		for (int i = 0; i < NUMBER_OF_ZOMBIES; i++){
			Zombie mob = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			mob.setCustomName("Panda's Ghost");
			mob.setBaby(true);
			ItemStack skull = new ItemStack(397, 1, (short) 3);
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			meta.setOwner("Grand_Panda");
			skull.setItemMeta(meta);
			mob.getEquipment().setHelmet(skull);
			mob.setTarget(Bukkit.getServer().getPlayerExact(player));
			mob.setVillager(false);
			mob.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000, 3));
			mob.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000, 3));
			mob.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100000000, 5));
			mob.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 3));
			mob.setCustomNameVisible(true);
			mob.setHealth(mob.getHealth() + 20);
		}
		//Bukkit.getPluginManager().getPlugin("GUIAPI").getLogger().info("spawned pandas");
		for (int i = 0; i < NUMBER_OF_ZOMBIES; i++){
			Zombie mob = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			mob.setCustomName("Austin's Ghost");
			mob.setBaby(true);
			ItemStack skull = new ItemStack(397, 1, (short) 3);
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			meta.setOwner("austinv11");
			skull.setItemMeta(meta);
			mob.getEquipment().setHelmet(skull);
			mob.setTarget(Bukkit.getServer().getPlayerExact(player));
			mob.setVillager(false);
			mob.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000, 3));
			mob.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000, 3));
			mob.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100000000, 5));
			mob.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 3));
			mob.setCustomNameVisible(true);
			mob.setHealth(mob.getHealth() + 20);
		}
		//Bukkit.getPluginManager().getPlugin("GUIAPI").getLogger().info("spawned austins");
	}
}
