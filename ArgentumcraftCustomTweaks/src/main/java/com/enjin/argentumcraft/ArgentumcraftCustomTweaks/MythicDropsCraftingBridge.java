package com.enjin.argentumcraft.ArgentumcraftCustomTweaks;

import io.github.austinv11.GUIAPI.Menu;
import io.github.austinv11.GUIAPI.MenuInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class MythicDropsCraftingBridge implements Listener{
	public static int MAX_SOCKETS = 4;
	public static Logger logger= Bukkit.getPluginManager().getPlugin("ArgentumcraftCustomTweaks").getLogger();
	public static ItemStack socket = new ItemStack(Material.HOPPER);
	public static Material[] possibleItems = {Material.LEATHER_BOOTS,Material.LEATHER_CHESTPLATE,
		Material.LEATHER_HELMET,Material.LEATHER_LEGGINGS,Material.IRON_HELMET,Material.IRON_CHESTPLATE,
		Material.IRON_LEGGINGS,Material.IRON_BOOTS,Material.GOLD_HELMET,Material.GOLD_CHESTPLATE,
		Material.GOLD_LEGGINGS,Material.GOLD_BOOTS,Material.CHAINMAIL_BOOTS,Material.CHAINMAIL_CHESTPLATE,
		Material.CHAINMAIL_HELMET,Material.CHAINMAIL_LEGGINGS,Material.DIAMOND_HELMET,
		Material.DIAMOND_CHESTPLATE,Material.DIAMOND_LEGGINGS,Material.DIAMOND_BOOTS,
		Material.WOOD_AXE,Material.WOOD_HOE,Material.WOOD_PICKAXE,Material.WOOD_SPADE,
		Material.WOOD_SWORD, Material.IRON_AXE,Material.IRON_HOE,Material.IRON_PICKAXE,
		Material.IRON_SPADE,Material.IRON_SWORD,Material.GOLD_AXE,Material.GOLD_HOE,Material.GOLD_PICKAXE,
		Material.GOLD_SPADE,Material.GOLD_SWORD,Material.DIAMOND_AXE,Material.DIAMOND_HOE,
		Material.DIAMOND_PICKAXE,Material.DIAMOND_SPADE,Material.DIAMOND_SWORD,
		Material.FISHING_ROD,Material.SHEARS};
	
	public MythicDropsCraftingBridge(){
		Bukkit.getPluginManager().registerEvents(this, Resources.instance);
		ItemMeta socketMeta = socket.getItemMeta();
		List<String> socketLore = new ArrayList<String>();
		socketLore.add("It has a slight attraction");
		socketLore.add("to nearby socket gems...");
		socketMeta.setLore(socketLore);
		socketMeta.setDisplayName(ChatColor.GOLD+"Socket Receptacle");
		socket.setItemMeta(socketMeta);
		loadCrafting();
	}
	
	
	
	/*@EventHandler
	public void onInvClick(InventoryClickEvent event){
		if (!event.isCancelled()){
			if (event.getInventory().getType() == InventoryType.WORKBENCH){
				CraftingInventory inventory = (CraftingInventory) event.getInventory();
				if (inventory.contains(socket)){
					//logger.info("contains socket");
					if (inventory.getContents().length > 1){
						//logger.info("contents > 1");
						int isValid = 0;
						int index = 0;
						loop:
						for (Material m : possibleItems){
							ItemStack[] contents = inventory.getContents();
							for (int i = 0; i < inventory.getContents().length; i++){
								ItemStack item = contents[i];
								if (item.getType() == m){
									//logger.info("item is good");
									isValid++;
									index = i;
									if (item.hasItemMeta()){
										if (item.getItemMeta().hasLore()){
											//logger.info("has lore");
											isValid = 10;
										}
									}
									if (isValid > 1){
										//logger.info("not valid");
										break loop;
									}
								}
							}
						}
						if (isValid == 1){
							////logger.info("valid");
							int numberOfSockets = 0;
							for (ItemStack item2: inventory.getContents()){
								if (item2.isSimilar(socket)){
									numberOfSockets++;
									////logger.info("sockets found:"+numberOfSockets);
								}
							}
							if (numberOfSockets != 0 && numberOfSockets < (MAX_SOCKETS+1)){
								////logger.info("good socket #");
								ItemStack newResult = inventory.getItem(index).clone();
								ItemMeta newMeta = newResult.getItemMeta();
								List<String> newLore = new ArrayList<String>();
								for (int j = 0; j < numberOfSockets; j++){
									newLore.add(ChatColor.DARK_GREEN+"(Socket)");
								}
								newMeta.setLore(newLore);
								newResult.setItemMeta(newMeta);
								inventory.setResult(newResult);
								//event.getInventory().setItem(0, newResult);
								Player player = (Player) event.getWhoClicked();
								player.updateInventory();
							}
						}
					}
				}
			}
		}
	}*/
	
	@EventHandler
	public void onCraft(CraftItemEvent event){
		if (!event.isCancelled()){
			if (event.getInventory().getResult().isSimilar(socket)){
				Player player = (Player) event.getWhoClicked();
				if (!(player.getLevel() >= (event.getInventory().getResult().getAmount()*10))){
					event.setCancelled(true);
				}else{
					player.setLevel(player.getLevel()-(event.getInventory().getResult().getAmount()*10));
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if (!event.isCancelled()){
			//if (event.hasItem()){
				if (event.getItem().isSimilar(socket)){
					event.setCancelled(true);
					if (event.getItem().getAmount() <= MAX_SOCKETS){
						Menu menu = new Menu(event.getPlayer(), ChatColor.RED+"Add sockets (x"+event.getItem().getAmount()+")",event.getPlayer().getInventory().getSize());
						for (int i = 0; i < event.getPlayer().getInventory().getSize(); i++){
							boolean valid = false;
							if (event.getPlayer().getInventory().getItem(i) != null){
								loop:
									for (int j = 0; j < possibleItems.length; j++){
										if (event.getPlayer().getInventory().getItem(i).getType() == possibleItems[j]){
											valid = true;
											break loop;
										}
									}
							}
							if (event.getPlayer().getInventory().getItem(i) != null){
								ItemStack buttonItem = event.getPlayer().getInventory().getItem(i).clone();
								ItemMeta buttonMeta = buttonItem.getItemMeta();
								List<String> buttonLore = new ArrayList<String>();
								if (valid){
									if (buttonItem.hasItemMeta()){
										if (buttonItem.getItemMeta().hasLore()){
											buttonLore = buttonMeta.getLore();
											buttonLore.add(ChatColor.GREEN+"*APPLICABLE*");
										}else{
											buttonLore.add(ChatColor.GREEN+"*APPLICABLE*");
										}
									}else{
										buttonLore.add(ChatColor.GREEN+"*APPLICABLE*");
									}
								}else{
									if (buttonItem.hasItemMeta()){
										if (buttonItem.getItemMeta().hasLore()){
											buttonLore = buttonMeta.getLore();
											buttonLore.add(ChatColor.RED+"*NOT APPLICABLE*");
										}else{
											buttonLore.add(ChatColor.RED+"*NOT APPLICABLE*");
										}
									}else{
										buttonLore.add(ChatColor.RED+"*NOT APPLICABLE*");
									}
								}
								buttonMeta.setLore(buttonLore);
								buttonItem.setItemMeta(buttonMeta);
								menu.setButton(i, buttonItem);
							}
						}
						menu.openMenu();
					}else{
						event.getPlayer().sendMessage(ChatColor.RED+"Sorry, you can't add that many sockets");
					}
				}
			//}
		}
	}
	
	@EventHandler
	public void onMenuClick(MenuInteractEvent event){//TODO ONLY DEPENDS ON MAX_SOCKET
		if (!event.isCancelled()){
			if (event.getButtonItem() != null){
				if (event.getButtonItem().getType() != null && event.getButtonItem().getType() != Material.AIR){
					if (event.getButtonItem().hasItemMeta()){
						if (event.getButtonItem().getItemMeta().hasLore()){
							if (event.getMenuName().contains("Add sockets (x1)")){
								if (event.getButtonDescription().contains(ChatColor.GREEN+"*APPLICABLE*")){
									ItemStack newItem = event.getPlayer().getInventory().getItem(event.getSlot()).clone();
									ItemMeta newMeta = newItem.getItemMeta();
									List<String> newLore = new ArrayList<String>();
									if (newItem.hasItemMeta()){
										if (newItem.getItemMeta().hasLore()){
											newLore = newMeta.getLore();
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										}else{
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										}
									}else{
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
									}
									newMeta.setLore(newLore);
									newItem.setItemMeta(newMeta);
									event.getPlayer().getInventory().setItem(event.getSlot(), newItem);
									event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
									event.getMenu().closeMenu();
									event.getPlayer().closeInventory();
									event.getPlayer().updateInventory();
									event.setCancelled(true);
									event.getPlayer().sendMessage(ChatColor.GREEN+"Successfully added 1 socket!");
								}else{
									event.getPlayer().sendMessage(ChatColor.RED+"Sorry, that item cannot receive a socket");
								}
							}else if (event.getMenuName().contains("Add sockets (x2)")){
								if (event.getButtonDescription().contains(ChatColor.GREEN+"*APPLICABLE*")){
									ItemStack newItem = event.getPlayer().getInventory().getItem(event.getSlot()).clone();
									ItemMeta newMeta = newItem.getItemMeta();
									List<String> newLore = new ArrayList<String>();
									if (newItem.hasItemMeta()){
										if (newItem.getItemMeta().hasLore()){
											newLore = newMeta.getLore();
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										}else{
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										}
									}else{
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
									}
									newMeta.setLore(newLore);
									newItem.setItemMeta(newMeta);
									event.getPlayer().getInventory().setItem(event.getSlot(), newItem);
									event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
									event.getMenu().closeMenu();
									event.getPlayer().closeInventory();
									event.getPlayer().updateInventory();
									event.setCancelled(true);
									event.getPlayer().sendMessage(ChatColor.GREEN+"Successfully added 2 sockets!");
								}else{
									event.getPlayer().sendMessage(ChatColor.RED+"Sorry, that item cannot receive a socket");
								}
							}else if (event.getMenuName().contains("Add sockets (x3)")){
								if (event.getButtonDescription().contains(ChatColor.GREEN+"*APPLICABLE*")){
									ItemStack newItem = event.getPlayer().getInventory().getItem(event.getSlot()).clone();
									ItemMeta newMeta = newItem.getItemMeta();
									List<String> newLore = new ArrayList<String>();
									if (newItem.hasItemMeta()){
										if (newItem.getItemMeta().hasLore()){
											newLore = newMeta.getLore();
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										}else{
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										}
									}else{
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
									}
									newMeta.setLore(newLore);
									newItem.setItemMeta(newMeta);
									event.getPlayer().getInventory().setItem(event.getSlot(), newItem);
									event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
									event.getMenu().closeMenu();
									event.getPlayer().closeInventory();
									event.getPlayer().updateInventory();
									event.setCancelled(true);
									event.getPlayer().sendMessage(ChatColor.GREEN+"Successfully added 3 sockets!");
								}else{
									event.getPlayer().sendMessage(ChatColor.RED+"Sorry, that item cannot receive a socket");
								}
							}else if (event.getMenuName().contains("Add sockets (x4)")){
								if (event.getButtonDescription().contains(ChatColor.GREEN+"*APPLICABLE*")){
									ItemStack newItem = event.getPlayer().getInventory().getItem(event.getSlot()).clone();
									ItemMeta newMeta = newItem.getItemMeta();
									List<String> newLore = new ArrayList<String>();
									if (newItem.hasItemMeta()){
										if (newItem.getItemMeta().hasLore()){
											newLore = newMeta.getLore();
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										}else{
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
											newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										}
									}else{
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
										newLore.add(ChatColor.DARK_GREEN+"(Socket)");
									}
									newMeta.setLore(newLore);
									newItem.setItemMeta(newMeta);
									event.getPlayer().getInventory().setItem(event.getSlot(), newItem);
									event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().getHeldItemSlot(), null);
									event.getMenu().closeMenu();
									event.getPlayer().closeInventory();
									event.getPlayer().updateInventory();
									event.setCancelled(true);
									event.getPlayer().sendMessage(ChatColor.GREEN+"Successfully added 4 sockets!");
								}else{
									event.getPlayer().sendMessage(ChatColor.RED+"Sorry, that item cannot receive a socket");
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static void loadCrafting(){
		ShapedRecipe socketRecipe = new ShapedRecipe(socket);
		socketRecipe.shape("DED", "DHD", "DDD");
		socketRecipe.setIngredient('D', Material.DIAMOND);
		socketRecipe.setIngredient('H', Material.HOPPER);
		socketRecipe.setIngredient('E', Material.EYE_OF_ENDER);
		Bukkit.getServer().addRecipe(socketRecipe);
	}
}
