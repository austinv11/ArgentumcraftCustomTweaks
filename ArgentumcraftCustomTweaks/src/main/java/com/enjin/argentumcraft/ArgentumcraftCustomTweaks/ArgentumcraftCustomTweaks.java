package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import java.io.File;
import java.util.Random;
import java.util.UUID;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.enjin.argentumcraft.Listeners.FreezeItemHandler;
import com.enjin.argentumcraft.Listeners.SignClickHandler;

public class ArgentumcraftCustomTweaks extends JavaPlugin{
	
	FileConfiguration config = getConfig();
	public static Economy economy = null;
	
	@Override
	public void onEnable(){
		configInit();
		setupEconomy();
		ItemStack freezeItem = new ItemStack(Material.SNOW_BALL);
		ItemMeta im = freezeItem.getItemMeta();
		im.setDisplayName(ChatColor.AQUA+"Freezeball");
		freezeItem.setItemMeta(im);
		new Resources(config, new File(getDataFolder(), "EasterEggTracker.yml"), this, economy, freezeItem, new File(getDataFolder(), "FreezeTracker.yml"));
		new SignClickHandler();
		new FreezeItemHandler();
		File tempFile = new File(getDataFolder(), "temp.yml");
		FileConfiguration temp = YamlConfiguration.loadConfiguration(tempFile);
		for (World w: Bukkit.getWorlds()){
			for (Entity e : w.getEntities()){
				LivingEntity tempEnt = (LivingEntity) e;
				if (temp.contains("%"+tempEnt.getCustomName()+"%")){
					if (temp.getString("%"+tempEnt.getCustomName()+"%") == "%NULL%"){
						tempEnt.setCustomName("");
					}else{
						tempEnt.setCustomName(temp.getString("%"+tempEnt.getCustomName()+"%"));
					}
					FreezeItemHandler.setFrozen(e.getEntityId(), true);
					BukkitTask task = new FreezerRunnable(e.getEntityId(), e.getLocation().clone()).runTaskTimer(Resources.instance,0,1);
				}
			}
		}
		tempFile.delete();
	}
	
	@Override
	public void onDisable(){
		Random r = new Random();
		for (World w: Bukkit.getWorlds()){
			for (Entity e : w.getEntities()){
				if (FreezeItemHandler.isFrozen(e.getEntityId())){
					int tempInt = r.nextInt();
					FileConfiguration temp = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "temp.yml"));
					while (temp.contains(tempInt+"")){
						tempInt = r.nextInt();
					}
					LivingEntity tempEnt = (LivingEntity) e;
					if (tempEnt.getCustomName() == null){
						temp.set("%"+tempInt+"%", "%NULL%");
					}else{
						temp.set("%"+tempInt+"%", tempEnt.getCustomName());
					}
					tempEnt.setCustomName(tempInt+"");
				}
			}
		}
		Resources.freezeFile.delete();
	}
	
	private void configInit(){
		config.addDefault("Options.requiredSignText","EasterEgg");
		config.addDefault("Options.easterEggSuccessMessage", "&aYou gained &3$%_AMOUNT_% &afor finding this Easter Egg!");
		config.addDefault("Options.easterEggFailureMessage", "&aYou already found this Easter Egg!");
		config.addDefault("Options.easterEggPlaceSuccessMessage", "&aSuccessfully made an easter egg sign that gives &3$%_AMOUNT_%");
		config.addDefault("Options.easterEggPlaceFailureMessage", "&cSorry %_PLAYER_%&c, you either don't have the correct permissions or you made an error with the formatting");
		config.options().copyDefaults(true);
		saveConfig();
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
