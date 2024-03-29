package fr.kwizzy.waroflegions.quest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Par Alexis le 08/10/2016.
 */

public class QuestChecker {

    private QuestChecker(){ }

	/********************
	 * STATIC METHODS
	 ********************/

	public static int getAmountFurnaceExtractAmount(Material m, InventoryClickEvent e)
	{
		Inventory clickedInventory = e.getClickedInventory();
		if(!clickedInventory.getType().equals(InventoryType.FURNACE))
			return 0;
		FurnaceInventory fi = (FurnaceInventory) clickedInventory;
		boolean click = e.getClick().isShiftClick() || e.getClick().isLeftClick() && e.getRawSlot() == 2;
		boolean item = fi.getResult() != null && fi.getResult().getType().equals(m);
		if(click && item)
			return fi.getResult().getAmount();
		return 0;
	}

	public static int getAmountFurnaceExtractAmount(Material m, FurnaceExtractEvent e) {
		Bukkit.broadcastMessage("lol");
		if (!e.getItemType().equals(m))
			return 0;
		if (e.getItemType().equals(m)) {
			return e.getItemAmount();
		}
		return 0;
	}

	public static int getCraftedItems(Material m, CraftItemEvent e) {
		if (e.isCancelled())
			return 0;
		Player p = (Player) e.getWhoClicked();
		if (!e.getCurrentItem().getType().equals(m))
			return 0;
		if (e.isShiftClick()) {
			int amountCanBeMade = getCanBeMade(p, e);
			int amountOfItems = e.getRecipe().getResult().getAmount() * getPossibleCreation(e);
			return amountOfItems > amountCanBeMade ? amountCanBeMade : amountOfItems;
		} else
			return e.getRecipe().getResult().getAmount();
	}

	private static int getPossibleCreation(CraftItemEvent e) {
		int itemsChecked = 0;
		int possibleCreations = 1;
		for (ItemStack item : e.getInventory().getMatrix()) {
			if (item != null && item.getType() != Material.AIR) {
				if (itemsChecked == 0) {
					possibleCreations = item.getAmount();
					itemsChecked++;
				} else
					possibleCreations = Math.min(possibleCreations, item.getAmount());
			}
		}
		return possibleCreations;
	}

	private static int getCanBeMade(Player p, CraftItemEvent e) {
		int amountCanBeMade = 0;
		ItemStack i = e.getRecipe().getResult();
		for (int s = 0; s <= 35; s++) {
			ItemStack test = p.getInventory().getItem(s);
			if (test == null || test.getType() == Material.AIR) {
				amountCanBeMade += i.getMaxStackSize();
				continue;
			}
			if (test.isSimilar(i))
				amountCanBeMade += i.getMaxStackSize() - test.getAmount();
		}
		return amountCanBeMade;
	}

}
