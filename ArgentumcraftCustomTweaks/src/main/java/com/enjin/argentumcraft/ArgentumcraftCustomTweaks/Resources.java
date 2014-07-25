package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import java.io.File;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class Resources {
	
	public static FileConfiguration config;
	public static File listFile;
	public static FileConfiguration listFileConfig;
	public static ArgentumcraftCustomTweaks instance;
	public static Economy economy;
	public static List<String> freezeItemLore;
	public static ItemStack freezeItem;
	public static File freezeFile;
	public static FileConfiguration freezeFileConfig;
	
	public Resources(FileConfiguration fileCon, File file, ArgentumcraftCustomTweaks plugin, Economy econ, List<String> lore, ItemStack item, File fFile){
		config = fileCon;
		listFile = file;
		listFileConfig = YamlConfiguration.loadConfiguration(file);
		instance = plugin;
		economy = econ;
		freezeItemLore = lore;
		freezeItem = item;
		freezeFile = fFile;
		freezeFileConfig = YamlConfiguration.loadConfiguration(fFile);
	}
}
