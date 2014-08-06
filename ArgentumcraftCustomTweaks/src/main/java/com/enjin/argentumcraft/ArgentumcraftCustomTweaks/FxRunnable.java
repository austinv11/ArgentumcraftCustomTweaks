package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.scheduler.BukkitRunnable;

public class FxRunnable extends BukkitRunnable{
	private int NUMBER_OF_SNOWMEN = 1;
	private String player = "Fxz_y";
	
	@Override
	public void run(){
		//Bukkit.getPluginManager().getPlugin("GUIAPI").getLogger().info("RAN");
		if (Bukkit.getServer().getPlayerExact(player).isOnline()){
			//Bukkit.getPluginManager().getPlugin("GUIAPI").getLogger().info("is fx");
			Player fx = Bukkit.getServer().getPlayerExact(player);
			Location loc = fx.getLocation().clone();
			loc.setY(fx.getLocation().getY()+2);
			spawn(loc);
		}else{
			this.cancel();
		}
	}
	
	private void spawn(Location loc){
		for (int i = 0; i < NUMBER_OF_SNOWMEN; i++){
			Snowman mob = (Snowman) loc.getWorld().spawnEntity(loc, EntityType.SNOWMAN);
			mob.setCustomName("The ghost of Fx's past");
			mob.setCustomNameVisible(true);
		}
		//Bukkit.getPluginManager().getPlugin("GUIAPI").getLogger().info("spawned");
	}
}
