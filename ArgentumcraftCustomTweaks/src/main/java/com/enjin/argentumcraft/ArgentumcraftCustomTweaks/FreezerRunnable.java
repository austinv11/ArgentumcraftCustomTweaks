package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import com.enjin.argentumcraft.Listeners.FreezeItemHandler;

public class FreezerRunnable extends BukkitRunnable{
	
	private int id;
	private Location loc;
	
	public FreezerRunnable(int entId, Location location){
		id = entId;
		loc = location.clone();
	}
	
	@Override
	public void run(){
		for (Entity e : loc.getWorld().getEntities()){
			if (e.getEntityId() == id){
				if (!e.isDead() && FreezeItemHandler.isFrozen(id)){
					e.teleport(loc);
				}else{
					this.cancel();
				}
			}
		}
	}
}
