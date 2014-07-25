package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
	List<String> lore = new ArrayList<String>();
	lore.add("It's cold to the touch.");
	lore.add("You wonder what'll happen if you");
	lore.add("hit something with it...");
	ItemStack freezeItem = new ItemStack(Material.SNOW_BALL);
	ItemMeta im = freezeItem.getItemMeta();
	im.setDisplayName(ChatColor.AQUA+"Freezeball");
	im.setLore(Resources.freezeItemLore);
	freezeItem.setItemMeta(im);
	new Resources(config, new File(getDataFolder(), "EasterEggTracker.yml"), this, economy, lore, freezeItem, new File(getDataFolder(), "FreezeTracker.yml"));
	new SignClickHandler();
	}
	
	@Override
	public void onDisable(){}
	
	private void configInit(){
		config.addDefault("Options.requiredSignText","Easter Egg");
		config.addDefault("Options.easterEggSuccessMessage", "You gained $%_AMOUNT_% for finding this Easter Egg!");
		config.addDefault("Options.easterEggFailureMessage", "You already found this Easter Egg!");
		config.addDefault("Options.easterEggPlaceSuccessMessage", "Successfully made an easter egg sign that gives $%_AMOUNT_%");
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
		if (cmd.getName().equalsIgnoreCase("givefreezeitem")){
			Player player;
			if (args.length < 1){
				sender.sendMessage(ChatColor.GOLD+"Giving "+sender.getName()+" a freezeball");
				player = (Player) sender;
			}else{
				player = Bukkit.getServer().getPlayer(args[0]);
				sender.sendMessage(ChatColor.GOLD+"Giving "+args[0]+" a freezeball");
				player.sendMessage(ChatColor.GOLD+"Gave "+args[0]+" a freezeball");
			}
			player.getInventory().addItem(Resources.freezeItem);
			player.updateInventory();
			return true;
		}
		return false;
	}
}
