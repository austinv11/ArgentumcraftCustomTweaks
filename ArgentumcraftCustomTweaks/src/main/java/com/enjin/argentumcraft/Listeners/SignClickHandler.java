package com.enjin.argentumcraft.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Sign;

import com.enjin.argentumcraft.ArgentumcraftCustomTweaks.Resources;

public class SignClickHandler implements Listener{
	
	public SignClickHandler(){
		Bukkit.getPluginManager().registerEvents(this, Resources.instance);
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if (!event.isCancelled()){
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
				if (event.hasBlock()){
					if (event.getClickedBlock().getType() == Material.SIGN || event.getClickedBlock().getType() == Material.SIGN_POST){
						Sign sign = (Sign) event.getClickedBlock().getState();
						if (sign.getLine(0).contains("["+Resources.config.getString("Options.requiredSignText")+"]")){
							if (!Resources.listFileConfig.contains(event.getPlayer().getName())){
								String amount = sign.getLine(1);
								event.getPlayer().sendMessage(Resources.config.getString("Options.easterEggSuccessMessage").replaceAll("%_AMOUNT_%", amount));
								Resources.listFileConfig.addDefault(event.getPlayer().getName(), true);
							}else{
								event.getPlayer().sendMessage(Resources.config.getString("Options.easterEggFailureMessage"));
							}
						}
					}
				}
			}
		}
	}
}
