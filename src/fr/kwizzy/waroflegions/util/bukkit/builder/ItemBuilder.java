package fr.kwizzy.waroflegions.util.bukkit.builder;

/**
 * Par Alexis le 24/04/2016.
 */

import java.lang.reflect.Method;
import java.util.*;

import fr.kwizzy.waroflegions.util.java.Log;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {

	private static final String NULL_MATERIAL = "The Material is null.";
	private static final String NULL_DISPLAYNAME = "The Material is null.";

	private ItemStack item;
	private ItemMeta meta;
	private Material material = Material.STONE;
	private int amount = 1;
	private MaterialData data;
	private short damage = 0;
	private Map<Enchantment, Integer> enchantments = new HashMap<>();
	private String displayname;
	private List<String> lore = new ArrayList<>();
	private List<ItemFlag> flags = new ArrayList<>();
	private boolean andSymbol = true;

	/**
	 * Initials the ItemBuilder with the Material
	 * 
	 * @see Material
	 */
	public ItemBuilder(Material material) {
		Validate.notNull(material, NULL_MATERIAL);
		this.item = new ItemStack(material);
		this.material = material;
	}

	/**
	 * Initials the ItemBuilder with the Material and the amount
	 * 
	 * @author Kev575
	 * @see Material
	 */
	public ItemBuilder(Material material, int amount) {
		Validate.notNull(material, NULL_MATERIAL);
		this.item = new ItemStack(material, amount);
		this.material = material;
		this.amount = amount;
	}

	/**
	 * Initials the ItemBuilder with the Material, the amount and the displaname
	 * 
	 * @author Kev575
	 * @see Material
	 */
	public ItemBuilder(Material material, int amount, String displayname) {
		Validate.notNull(material, NULL_MATERIAL);
		Validate.notNull(displayname, NULL_DISPLAYNAME);
		this.item = new ItemStack(material, amount);
		this.material = material;
		this.amount = amount;
		this.displayname = displayname;
	}

	/**
	 * Initials the ItemBuilder with the Material and the displaname
	 * 
	 * @see Material
	 */
	public ItemBuilder(Material material, String displayname) {
		Validate.notNull(material, NULL_MATERIAL);
		Validate.notNull(displayname, NULL_DISPLAYNAME);
		this.item = new ItemStack(material);
		this.material = material;
		this.displayname = displayname;
	}

	/**
	 * Initials the ItemBuilder with an already existing ItemStackJson
	 * 
	 * @see ItemStack
	 */
	public ItemBuilder(ItemStack item) {
		Validate.notNull(item, "The Item is null.");
		this.item = item;
		this.meta = item.getItemMeta();
		this.material = item.getType();
		this.amount = item.getAmount();
		this.data = item.getData();
		this.damage = item.getDurability();
		this.enchantments = item.getEnchantments();
		this.displayname = item.getItemMeta().getDisplayName();
		this.lore = item.getItemMeta().getLore();
		for (ItemFlag f : item.getItemMeta().getItemFlags()) {
			flags.add(f);
		}
	}

	/**
	 * Initials the ItemBuilder with an already existing instance of the
	 * ItemBuilder
	 * 
	 * @see ItemBuilder
	 */
	public ItemBuilder(ItemBuilder builder) {
		Validate.notNull(builder, "The ItemBuilder is null.");
		this.item = builder.item;
		this.meta = builder.meta;
		this.material = builder.material;
		this.amount = builder.amount;
		this.damage = builder.damage;
		this.data = builder.data;
		this.damage = builder.damage;
		this.enchantments = builder.enchantments;
		this.displayname = builder.displayname;
		this.lore = builder.lore;
		this.flags = builder.flags;
	}

	/**
	 * Sets the amount of the builded ItemStackJson
	 * 
	 * @param amount
	 *            (Integer)
	 */
	public ItemBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Sets the MaterialData of the builded ItemStackJson
	 * 
	 * @param data
	 *            (MaterialData)
	 */
	public ItemBuilder data(MaterialData data) {
		Validate.notNull(data, "The Data is null.");
		this.data = data;
		return this;
	}

	/**
	 * Sets the damage (durability) of the builded ItemStackJson
	 * 
	 * @param damage
	 *            (Short)
	 * @deprecated Use ItemBuilder#durability
	 */
	@Deprecated
	public ItemBuilder damage(short damage) {
		this.damage = damage;
		return this;
	}

	/**
	 * Sets the durability (damage) of the builded ItemStackJson
	 * 
	 * @param damage
	 *            (Short)
	 */
	public ItemBuilder durability(short damage) {
		this.damage = damage;
		return this;
	}

	/**
	 * Sets the Material of the builded ItemStackJson
	 * 
	 * @param material
	 *            (Material)
	 */
	public ItemBuilder material(Material material) {
		Validate.notNull(material, "The Mater is null.");
		this.material = material;
		return this;
	}

	/**
	 * Sets the ItemMeta of the builded ItemStackJson
	 * 
	 * @param meta
	 *            (ItemMeta)
	 */
	public ItemBuilder meta(ItemMeta meta) {
		Validate.notNull(meta, "The Meta is null.");
		this.meta = meta;
		return this;
	}

	/**
	 * Adds the Enchantment of the builded ItemStackJson
	 * 
	 * @param enchant
	 *            (Enchantment)
	 */
	public ItemBuilder enchant(Enchantment enchant, int level) {
		Validate.notNull(enchant, "The Enchantment is null.");
		enchantments.put(enchant, level);
		return this;
	}

	/**
	 * Sets the Enchantments of the builded ItemStackJson
	 * 
	 * @param enchantments
	 *            (Map<Enchantment, Integer> )
	 */
	public ItemBuilder enchant(Map<Enchantment, Integer> enchantments) {
		Validate.notNull(enchantments, "The Enchantments is null.");
		this.enchantments = enchantments;
		return this;
	}

	/**
	 * Sets the Displayname of the builded ItemStackJson
	 * 
	 * @param displayname
	 *            (Displayname)
	 */
	public ItemBuilder displayname(String displayname) {
		Validate.notNull(displayname, "The Displayname is null.");
		if (andSymbol)
			this.displayname = ChatColor.translateAlternateColorCodes('&', displayname);
		else
			this.displayname = displayname;
		return this;
	}

	/**
	 * Adds the line to the Lore of the builded ItemStackJson
	 * 
	 * @param line
	 *            (String)
	 */
	public ItemBuilder lore(String line) {
		Validate.notNull(line, "The Line is null.");
		if (andSymbol)
			lore.add(ChatColor.translateAlternateColorCodes('&', line));
		else
			lore.add(line);
		return this;
	}

	/**
	 * Sets the lore of the builded ItemStackJson
	 * 
	 * @param lore
	 *            (List<String>)
	 */
	public ItemBuilder lore(List<String> lore) {
		Validate.notNull(lore, "The Lore is null.");
		this.lore = lore;
		return this;
	}

	/**
	 * Adds the lines to the Lore of the builded ItemStackJson
	 * 
	 * @param lines
	 *            (String...)
	 * @author Kev575
	 */
	public ItemBuilder lores(String... lines) {
		Validate.notNull(lines, "The Lines are null.");
		for (String line : lines) {
			if (andSymbol) {
				lore.add(ChatColor.translateAlternateColorCodes('&', line));
			} else {
				lore.add(line);
			}
		}
		lore.addAll(Arrays.asList(lines));
		return this;
	}

	/**
	 * Adds the line as the counts position as lore
	 * 
	 * @param line
	 *            (String)
	 * @param count
	 *            (Integer)
	 */
	public ItemBuilder lore(String line, int count) {
		Validate.notNull(line, "The Line is null.");
		if (andSymbol)
			lore.set(count, ChatColor.translateAlternateColorCodes('&', line));
		else
			lore.set(count, line);
		return this;
	}

	/**
	 * Adds the ItemFlag to the builded ItemStackJson
	 * 
	 * @param flag
	 *            (ItemFlag)
	 */
	public ItemBuilder flag(ItemFlag flag) {
		Validate.notNull(flag, "The Flag is null.");
		flags.add(flag);
		return this;
	}

	/**
	 * Sets the ItemFlags of the builded ItemStackJson
	 * 
	 * @param flags
	 *            (List<ItemFlag>)
	 */
	public ItemBuilder flag(List<ItemFlag> flags) {
		Validate.notNull(flags, "The Flags are null.");
		this.flags = flags;
		return this;
	}

	/**
	 * Makes the Item (un-)breakable
	 * 
	 * @param unbreakable
	 *            (Boolean)
	 */
	public ItemBuilder unbreakable(boolean unbreakable) {
		meta.spigot().setUnbreakable(unbreakable);
		return this;
	}

	/**
	 * Makes the Item Glow
	 */
	public ItemBuilder glow() {
		enchant(material != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK, 10);
		flag(ItemFlag.HIDE_ENCHANTS);
		return this;
	}

	/**
	 * Sets the Owner of the Skull This only affects if the Item is a SKULL_ITEM
	 * or a SKULL
	 * 
	 * @param user
	 *            (String)
	 */
	public ItemBuilder owner(String user) {
		Validate.notNull(user, "The Username is null.");
		if ((material == Material.SKULL_ITEM) || (material == Material.SKULL)) {
			SkullMeta smeta = (SkullMeta) meta;
			smeta.setOwner(user);
			meta = smeta;
		}
		return this;
	}

	/**
	 * Method to access all NBT Methods
	 */
	public Unsafe unsafe() {
		return new Unsafe(this);
	}

	/**
	 * Toggles the replace of the ampersand (&) symbol to the section sign (§)
	 * symbol
	 * 
	 * @author Kev575
	 */
	public ItemBuilder replaceAndSymbol() {
		replaceAndSymbol(!andSymbol);
		return this;
	}

	/**
	 * Enables / disables the replace of the ampersand (&) symbol to the section
	 * sign (§) symbol
	 * 
	 * @author Kev575
	 */
	public ItemBuilder replaceAndSymbol(boolean replace) {
		andSymbol = replace;
		return this;
	}

	/**
	 * Builds the ItemStackJson and returns it
	 * 
	 * @return (ItemStackJson)
	 */
	public ItemStack build() {
		item.setType(material);
		item.setAmount(amount);
		item.setDurability(damage);
		meta = item.getItemMeta();
		if (data != null) {
			item.setData(data);
		}
		if (enchantments.size() > 0) {
			item.addUnsafeEnchantments(enchantments);
		}
		if (displayname != null) {
			meta.setDisplayName(displayname);
		}
		if (lore.isEmpty()) {
			meta.setLore(lore);
		}
		if (flags.isEmpty()) {
			for (ItemFlag f : flags) {
				meta.addItemFlags(f);
			}
		}
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * This Methods are unsafe and should not be used when you don't know what
	 * they do. These methods can cause a corruption of the Players Inventory
	 * File when it contains a invalid Item and causes so that the PLayer can no
	 * longer join the World with the corrupted Inventory File.
	 * 
	 * @author Acquized
	 * @since 1.5
	 */
	public class Unsafe {

		/**
		 * Don't access the ReflectionUtils Class using this field. Using
		 * methods from ReflectionUtils Class is not suggested and may cause a
		 * major plugin break.
		 **/
		protected final ReflectionUtils utils = new ReflectionUtils();
		protected final ItemBuilder builder;

		/**
		 * Initials the Unsafe Class with the ItemBuilder
		 * 
		 * @param builder
		 *            (ItemBuilder)
		 */
		public Unsafe(ItemBuilder builder) {
			this.builder = builder;
		}

		/**
		 * Sets a String in the Item NBT Compound
		 */
		public void setString(String key, String value) {
			builder.item = utils.setString(builder.item, key, value);
		}

		/**
		 * Gets a String from the Item NBT Compound
		 */
		public String getString(String key) {
			return utils.getString(builder.item, key);
		}

		/**
		 * Sets a Integer in the Item NBT Compound
		 */
		public void setInt(String key, int value) {
			builder.item = utils.setInt(builder.item, key, value);
		}

		/**
		 * Gets a Integer from the Item NBT Compound
		 */
		public int getInt(String key) {
			return utils.getInt(builder.item, key);
		}

		/**
		 * Sets a Double in the Item NBT Compound
		 */
		public void setDouble(String key, double value) {
			builder.item = utils.setDouble(builder.item, key, value);
		}

		/**
		 * Gets a Double from the Item NBT Compound
		 */
		public double getDouble(String key) {
			return utils.getDouble(builder.item, key);
		}

		/**
		 * Sets a Boolean in the Item NBT Compound
		 */
		public void setBoolean(String key, boolean value) {
			builder.item = utils.setBoolean(builder.item, key, value);
		}

		/**
		 * Gets a Boolean from the Item NBT Compound
		 */
		public boolean getBoolean(String key) {
			return utils.getBoolean(builder.item, key);
		}

		/**
		 * Checks if the Item NBT Compound contains the Key
		 */
		public boolean containsKey(String key) {
			return utils.hasKey(builder.item, key);
		}

		/**
		 * Accesses back the ItemBuilder Class and exits the Unsafe Class
		 * 
		 * @return ItemBuilder
		 */
		public ItemBuilder builder() {
			return builder;
		}

		public class ReflectionUtils {

			public String getString(ItemStack item, String key) {
				Object compound = getNBTTagCompound(getItemAsNMSStack(item));
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					return (String) compound.getClass().getMethod("getString", String.class).invoke(compound, key);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return null;
			}

			public ItemStack setString(ItemStack item, String key, String value) {
				Object nmsItem = getItemAsNMSStack(item);
				Object compound = getNBTTagCompound(nmsItem);
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					compound.getClass().getMethod("setString", String.class, String.class).invoke(compound, key, value);
					nmsItem = setNBTTag(compound, nmsItem);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return getItemAsBukkitStack(nmsItem);
			}

			public int getInt(ItemStack item, String key) {
				Object compound = getNBTTagCompound(getItemAsNMSStack(item));
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					return (Integer) compound.getClass().getMethod("getInt", String.class).invoke(compound, key);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return -1;
			}

			public ItemStack setInt(ItemStack item, String key, int value) {
				Object nmsItem = getItemAsNMSStack(item);
				Object compound = getNBTTagCompound(nmsItem);
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					compound.getClass().getMethod("setInt", String.class, Integer.class).invoke(compound, key, value);
					nmsItem = setNBTTag(compound, nmsItem);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return getItemAsBukkitStack(nmsItem);
			}

			public double getDouble(ItemStack item, String key) {
				Object compound = getNBTTagCompound(getItemAsNMSStack(item));
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					return (Double) compound.getClass().getMethod("getDouble", String.class).invoke(compound, key);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return Double.NaN;
			}

			public ItemStack setDouble(ItemStack item, String key, double value) {
				Object nmsItem = getItemAsNMSStack(item);
				Object compound = getNBTTagCompound(nmsItem);
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					compound.getClass().getMethod("setDouble", String.class, Double.class).invoke(compound, key, value);
					nmsItem = setNBTTag(compound, nmsItem);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return getItemAsBukkitStack(nmsItem);
			}

			public boolean getBoolean(ItemStack item, String key) {
				Object compound = getNBTTagCompound(getItemAsNMSStack(item));
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					return (Boolean) compound.getClass().getMethod("getBoolean", String.class).invoke(compound, key);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return false;
			}

			public ItemStack setBoolean(ItemStack item, String key, boolean value) {
				Object nmsItem = getItemAsNMSStack(item);
				Object compound = getNBTTagCompound(nmsItem);
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					compound.getClass().getMethod("setBoolean", String.class, Boolean.class).invoke(compound, key,
							value);
					nmsItem = setNBTTag(compound, nmsItem);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return getItemAsBukkitStack(nmsItem);
			}

			public boolean hasKey(ItemStack item, String key) {
				Object compound = getNBTTagCompound(getItemAsNMSStack(item));
				if (compound == null) {
					compound = getNewNBTTagCompound();
				}
				try {
					return (Boolean) compound.getClass().getMethod("hasKey", String.class).invoke(compound, key);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return false;
			}

			/** <!-- UNSAFE METHODS (DO NOT USE!) !--> **/
			public Object getNewNBTTagCompound() {
				String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
				try {
					return Class.forName("net.minecraft.server." + ver + ".NBTTagCompound").newInstance();
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return null;
			}

			public Object setNBTTag(Object tag, Object item) {
				try {
					item.getClass().getMethod("setTag", item.getClass()).invoke(item, tag);
					return item;
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return null;
			}

			public Object getNBTTagCompound(Object nmsStack) {
				try {
					return nmsStack.getClass().getMethod("getTag").invoke(nmsStack);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return null;
			}

			@SuppressWarnings("unchecked")
			public Object getItemAsNMSStack(ItemStack item) {
				try {
					Method m = getCraftItemStackClass().getMethod("asNMSCopy", ItemStack.class);
					return m.invoke(getCraftItemStackClass(), item);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return null;
			}

			@SuppressWarnings("unchecked")
			public ItemStack getItemAsBukkitStack(Object nmsStack) {
				try {
					Method m = getCraftItemStackClass().getMethod("asCraftMirror", nmsStack.getClass());
					return (ItemStack) m.invoke(getCraftItemStackClass(), nmsStack);
				} catch (ReflectiveOperationException ex) {
					Log.printException(ex);
				}
				return null;
			}

			public Class getCraftItemStackClass() {
				String ver = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
				try {
					return Class.forName("org.bukkit.craftbukkit." + ver + ".inventory.CraftItemStack");
				} catch (ClassNotFoundException ex) {
					Log.printException(ex);
				}
				return null;
			}

		}
	}
}
