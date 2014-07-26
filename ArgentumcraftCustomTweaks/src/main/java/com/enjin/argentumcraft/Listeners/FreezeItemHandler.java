package com.enjin.argentumcraft.Listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import com.enjin.argentumcraft.ArgentumcraftCustomTweaks.FreezerRunnable;
import com.enjin.argentumcraft.ArgentumcraftCustomTweaks.Resources;

public class FreezeItemHandler implements Listener{
	
	public FreezeItemHandler(){
		Bukkit.getPluginManager().registerEvents(this, Resources.instance);
	}
	
	@EventHandler
	public void test(PlayerInteractEntityEvent event){
		if (event.getPlayer().getItemInHand().isSimilar(Resources.freezeItem)){
			event.getPlayer().sendMessage(event.getRightClicked().getEntityId()+"");
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		if (isFrozen(event.getPlayer().getEntityId())){
			setFrozen(event.getPlayer().getEntityId(), false, event.getPlayer());
		}
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event){
		if (!event.isCancelled()){
			if (isFrozen(event.getPlayer().getEntityId())){
				setFrozen(event.getPlayer().getEntityId(), false, event.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event){
		if (isFrozen(event.getPlayer().getEntityId())){
			setFrozen(event.getPlayer().getEntityId(), false, event.getPlayer());
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if (!event.isCancelled()){
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
				if (event.hasItem()){
					if (event.getItem().isSimilar(Resources.freezeItem)){
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		//if (!event.isCancelled()){
			//if (event.getCause() == DamageCause.ENTITY_ATTACK){
				//Bukkit.getLogger().info("Entity attack");//
				if (event.getDamager().getType() == EntityType.PLAYER){
					//Bukkit.getLogger().info("player attack");
					Player player = (Player) event.getDamager();
					if (player.getItemInHand().isSimilar(Resources.freezeItem)){
						//Bukkit.getLogger().info("is similar");
						event.setCancelled(true);
						int damaged = event.getEntity().getEntityId();
						if (event.getEntity().getType() == EntityType.PLAYER){
							Player p = (Player) event.getEntity();
							if (!p.hasPermission("ArgentumcraftCustomTweaks.freezeBypass")){
								setFrozen(damaged, !isFrozen(damaged), (Player) event.getEntity());
							}
						}else{
							setFrozen(damaged, !isFrozen(damaged));
							if (isFrozen(damaged)){
								BukkitTask task = new FreezerRunnable(damaged, event.getEntity().getLocation().clone()).runTaskTimer(Resources.instance,0,1);
							}
						}
						if (isFrozen(damaged)){
							LivingEntity lE = (LivingEntity) event.getEntity();
							lE.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000000, 10));
							player.sendMessage(ChatColor.AQUA+"The entity has just been frozen!");
						}else{
							player.sendMessage(ChatColor.RED+"The entity has just been thawed!");
						}
					}
				}
			//}
		//}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		if (!event.isCancelled()){
			if (isFrozen(event.getPlayer().getEntityId())){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event){
		if (!event.isCancelled()){
			if (isFrozen(event.getPlayer().getEntityId())){
				event.setCancelled(true);
			}
		}
	}
	
	public static void setFrozen(int id, boolean frozen, Player player){
		Resources.freezeFileConfig.set(id+"", frozen);
		if (frozen){
			player.sendMessage(ChatColor.AQUA+"You have just been frozen!");
		}else{
			player.sendMessage(ChatColor.RED+"You have just been thawed!");
		}
		try{
			Resources.freezeFileConfig.save(Resources.freezeFile);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void setFrozen(int id, boolean frozen){
		Resources.freezeFileConfig.set(id+"", frozen);
		try{
			Resources.freezeFileConfig.save(Resources.freezeFile);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static boolean isFrozen(int id){
		return Resources.freezeFileConfig.getBoolean(id+"");
	}
}
