package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import java.io.File;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.argentumcraft.Listeners.SignClickHandler;

public class ArgentumcraftCustomTweaks extends JavaPlugin{
	
	FileConfiguration config = getConfig();
	public static Economy economy = null;
	
	@Override
	public void onEnable(){
	configInit();
	setupEconomy();
	new Resources(config, new File(getDataFolder(), "EasterEggTracker.yml"), this, economy);
	new SignClickHandler();
	}
	
	@Override
	public void onDisable(){}
	
	private void configInit(){
		config.addDefault("Options.requiredSignText","Easter Egg");
		config.addDefault("Options.easterEggSuccessMessage", "You gained $%_AMOUNT_% for finding this Easter Egg!");
		config.addDefault("Options.easterEggFailureMessage", "You already found this Easter Egg!");
		config.addDefault("Options.easterEggPlaceSuccessMessage", "Successfully made an easter egg sign taht gives $%_AMOUNT_%");
		config.addDefault("Options.easterEggPlaceFailureMessage", "Sorry %_PLAYER_%, you either don't have the correct permissions or you made an error with the formatting");
	}
	
	private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		/*if (cmd.getName().equalsIgnoreCase("opengui")){
			return true;
		}*/
		return false;
	}
}
