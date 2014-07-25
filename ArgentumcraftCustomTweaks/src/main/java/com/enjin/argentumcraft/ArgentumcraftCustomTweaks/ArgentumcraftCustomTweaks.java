package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.argentumcraft.Listeners.SignClickHandler;

public class ArgentumcraftCustomTweaks extends JavaPlugin{
	
	FileConfiguration config = getConfig();
	
	@Override
	public void onEnable(){
	configInit();
	new Resources(config, new File(getDataFolder(), "EasterEggTracker.yml"), this);
	new SignClickHandler();
	}
	
	@Override
	public void onDisable(){}
	
	private void configInit(){
		config.addDefault("Options.requiredSignText","Easter Egg");
		config.addDefault("Options.easterEggSuccessMessage", "You gained $%_AMOUNT_% for finding this Easter Egg!");
		config.addDefault("Options.easterEggFailureMessage", "You already found this Easter Egg!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		/*if (cmd.getName().equalsIgnoreCase("opengui")){
			return true;
		}*/
		return false;
	}
}
