package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Resources {
	
	public static FileConfiguration config;
	public static File listFile;
	public static FileConfiguration listFileConfig;
	public static ArgentumcraftCustomTweaks instance;
	
	public Resources(FileConfiguration fileCon, File file, ArgentumcraftCustomTweaks plugin){
		config = fileCon;
		listFile = file;
		listFileConfig = YamlConfiguration.loadConfiguration(file);
		instance = plugin;
	}
}
