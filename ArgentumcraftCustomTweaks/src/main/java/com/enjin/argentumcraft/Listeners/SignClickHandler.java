package com.enjin.argentumcraft.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.block.Sign;

import com.enjin.argentumcraft.ArgentumcraftCustomTweaks.Resources;

public class SignClickHandler implements Listener{
	
	public SignClickHandler(){
		Bukkit.getPluginManager().registerEvents(this, Resources.instance);
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		//if (!event.isCancelled()){
			//if (event.getAction() == Action.RIGHT_CLICK_BLOCK || ){
				if (event.hasBlock()){
					if (event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST){
						Sign sign = (Sign) event.getClickedBlock().getState();
						if (sign.getLine(0).contains("["+ChatColor.AQUA+Resources.config.getString("Options.requiredSignText")+ChatColor.RESET+"]")){
							int x = event.getClickedBlock().getX();
							int y = event.getClickedBlock().getY();
							int z = event.getClickedBlock().getZ();
							if (Resources.listFileConfig.contains(event.getPlayer().getName()+x+"."+y+"."+z)){
								String amount = sign.getLine(1);
								Resources.economy.depositPlayer(event.getPlayer(), Double.parseDouble(amount));
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Resources.config.getString("Options.easterEggSuccessMessage").replaceAll("%_AMOUNT_%", amount)));
								Resources.listFileConfig.addDefault(event.getPlayer().getName()+x+"."+y+"."+z, true);
							}else{
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Resources.config.getString("Options.easterEggFailureMessage")));
							}
						}
					}
				}
		//	}
		//}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event){
		if (!event.isCancelled()){
			if (event.getLine(0).contains("["+Resources.config.getString("Options.requiredSignText")+"]")){
				if (event.getPlayer().hasPermission("ArgentumcraftCustomTweaks.easterEggSignAbility")){
					try{
						Double.parseDouble(event.getLine(1));
						event.setLine(0, "["+ChatColor.AQUA+Resources.config.getString("Options.requiredSignText")+ChatColor.RESET+"]");
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Resources.config.getString("Options.easterEggPlaceSuccessMessage").replaceAll("%_AMOUNT_%", event.getLine(1))));
					}catch (Exception e){
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Resources.config.getString("Options.easterEggPlaceFailureMessage").replaceAll("%_PLAYER_%", event.getPlayer().getName())));
					}
				}else{
					event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Resources.config.getString("Options.easterEggPlaceFailureMessage").replaceAll("%_PLAYER_%", event.getPlayer().getName())));
					event.setCancelled(true);
				}
			}
		}
	}
}
