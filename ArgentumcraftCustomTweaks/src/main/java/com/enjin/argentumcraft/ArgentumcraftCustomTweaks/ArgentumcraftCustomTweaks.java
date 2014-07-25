package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ArgentumcraftCustomTweaks extends JavaPlugin{
	
	FileConfiguration config = getConfig();
	
	@Override
	public void onEnable(){
	configInit();	
	}
	
	@Override
	public void onDisable(){}
	
	private static void configInit(){
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("opengui")){
			return true;
		}
		return false;
	}
}
