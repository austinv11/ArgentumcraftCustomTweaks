package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import java.io.File;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Resources {
	
	public static FileConfiguration config;
	public static File listFile;
	public static FileConfiguration listFileConfig;
	public static ArgentumcraftCustomTweaks instance;
	public static Economy economy;
	
	public Resources(FileConfiguration fileCon, File file, ArgentumcraftCustomTweaks plugin, Economy econ){
		config = fileCon;
		listFile = file;
		listFileConfig = YamlConfiguration.loadConfiguration(file);
		instance = plugin;
		economy = econ;
	}
}
