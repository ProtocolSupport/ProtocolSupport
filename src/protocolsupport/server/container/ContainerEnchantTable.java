package protocolsupport.server.container;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.Blocks;
import net.minecraft.server.v1_11_R1.Enchantment;
import net.minecraft.server.v1_11_R1.EnchantmentManager;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.IInventory;
import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.Items;
import net.minecraft.server.v1_11_R1.PlayerInventory;
import net.minecraft.server.v1_11_R1.StatisticList;
import net.minecraft.server.v1_11_R1.WeightedRandomEnchant;
import net.minecraft.server.v1_11_R1.World;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

//TODO: Remove enchant tabel injector and write a separate plugin that autoadds lapis from player inventory to enchant slot?
public class ContainerEnchantTable extends net.minecraft.server.v1_11_R1.ContainerEnchantTable {

	private Random random = new Random();

	private World world;
	private BlockPosition position;
	private Player player;

	private int[] ids = h;
	private int[] levels = i;

	public ContainerEnchantTable(PlayerInventory inv, World world, BlockPosition pos) {
		super(inv, world, pos);
		this.world = world;
		this.position = pos;
		this.player = (Player) inv.player.getBukkitEntity();
	}

	private static int enchantsNumber = 3;

	@SuppressWarnings("deprecation")
	@Override
	public void a(IInventory iinventory) {
		if (iinventory != enchantSlots) {
			return;
		}
		clearEnchantmentSlots();
		ItemStack itemstack = iinventory.getItem(0);
		if (itemstack.isEmpty()) {
			return;
		}
		int bookShelfs = getAffectingBookshelfsCount();
		random.setSeed(f);
		EnchantmentOffer[] offers = new EnchantmentOffer[enchantsNumber];
		for (int slot = 0; slot < enchantsNumber; ++slot) {
			int cost = EnchantmentManager.a(random, slot, bookShelfs, itemstack);
			if (cost >= (slot + 1)) {
				List<WeightedRandomEnchant> list = getEnchantments(itemstack, slot, cost);
				if (!list.isEmpty()) {
					WeightedRandomEnchant weightedrandomenchant = list.get(random.nextInt(list.size()));
					offers[slot] = new EnchantmentOffer(toBukkit(weightedrandomenchant.enchantment), weightedrandomenchant.level, cost);
				}
			}
		}
		PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(
			player, getBukkitView(),
			world.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()),
			CraftItemStack.asCraftMirror(itemstack), offers, bookShelfs
		);
		event.setCancelled(!itemstack.canEnchant());
		world.getServer().getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			clearEnchantmentSlots();
		} else {
			for (int slot = 0; slot < enchantsNumber; slot++) {
				EnchantmentOffer offer = event.getOffers()[slot];
				if (offer != null) {
					costs[slot] = offer.getCost();
					ids[slot] = offer.getEnchantment().getId();
					levels[slot] = offer.getEnchantmentLevel();
				}
			}
			b();
		}
	}


	@Override
	public boolean a(EntityHuman entityhuman, int slot) {
		boolean supportsLapisSlot = ProtocolSupportAPI.getProtocolVersion((Player) entityhuman.getBukkitEntity()).isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
		ItemStack itemstack = enchantSlots.getItem(0);
		ItemStack lapis = enchantSlots.getItem(1);
		int cost = slot + 1;
		if (supportsLapisSlot && lapis.getCount() < cost && !entityhuman.abilities.canInstantlyBuild) {
			return false;
		}
		if ((costs[slot] > 0) && (!itemstack.isEmpty()) && (((entityhuman.expLevel >= cost) && (entityhuman.expLevel >= costs[slot])) || entityhuman.abilities.canInstantlyBuild)) {
			boolean isBook = itemstack.getItem() == Items.BOOK;
			Map<org.bukkit.enchantments.Enchantment, Integer> enchants = getEnchantments(itemstack, slot, costs[slot])
			.stream().collect(Collectors.toMap(enchantment -> toBukkit(enchantment.enchantment), enchantment -> enchantment.level));
			CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
			EnchantItemEvent event = new EnchantItemEvent(
				(Player) entityhuman.getBukkitEntity(), getBukkitView(),
				world.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()),
				item, costs[slot], enchants, slot
			);
			world.getServer().getPluginManager().callEvent(event);
			int level = event.getExpLevelCost();
			if (event.isCancelled() || ((level > entityhuman.expLevel) && !entityhuman.abilities.canInstantlyBuild) || event.getEnchantsToAdd().isEmpty()) {
				return false;
			}
			if (isBook) {
				itemstack = new ItemStack(Items.ENCHANTED_BOOK);
				enchantSlots.setItem(0, itemstack);
			}
			for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
				try {
					if (isBook) {
						Enchantment ench = toMojang(entry.getKey());
						if (ench != null) {
							WeightedRandomEnchant wench = new WeightedRandomEnchant(ench, entry.getValue());
							Items.ENCHANTED_BOOK.a(itemstack, wench);
						}
					} else {
						item.addUnsafeEnchantment(entry.getKey(), entry.getValue());
					}
				} catch (IllegalArgumentException ex) {
				}
			}
			entityhuman.enchantDone(supportsLapisSlot ? cost : costs[slot]);
			if (!entityhuman.abilities.canInstantlyBuild && supportsLapisSlot) {
				lapis.subtract(cost);
				if (lapis.getCount() <= 0) {
					enchantSlots.setItem(1, ItemStack.a);
				}
			}
			entityhuman.b(StatisticList.Y);
			enchantSlots.update();
			f = entityhuman.cY();
			a(enchantSlots);
			return true;
		}
		return false;
	}

	private final void clearEnchantmentSlots() {
		Arrays.fill(costs, 0);
		Arrays.fill(ids, -1);
		Arrays.fill(levels, -1);
	}

	private int getAffectingBookshelfsCount() {
		int bookShelfs = 0;
		for (int z = -1; z <= 1; ++z) {
			for (int x = -1; x <= 1; ++x) {
				if (((z != 0) || (x != 0)) && world.isEmpty(position.a(x, 0, z)) && world.isEmpty(position.a(x, 1, z))) {
					if (world.getType(position.a(x * 2, 0, z * 2)).getBlock() == Blocks.BOOKSHELF) {
						++bookShelfs;
					}
					if (world.getType(position.a(x * 2, 1, z * 2)).getBlock() == Blocks.BOOKSHELF) {
						++bookShelfs;
					}
					if ((x != 0) && (z != 0)) {
						if (world.getType(position.a(x * 2, 0, z)).getBlock() == Blocks.BOOKSHELF) {
							++bookShelfs;
						}
						if (world.getType(position.a(x * 2, 1, z)).getBlock() == Blocks.BOOKSHELF) {
							++bookShelfs;
						}
						if (world.getType(position.a(x, 0, z * 2)).getBlock() == Blocks.BOOKSHELF) {
							++bookShelfs;
						}
						if (world.getType(position.a(x, 1, z * 2)).getBlock() == Blocks.BOOKSHELF) {
							++bookShelfs;
						}
					}
				}
			}
		}
		return bookShelfs;
	}

	private List<WeightedRandomEnchant> getEnchantments(ItemStack itemstack, int slot, int cost) {
		random.setSeed(f + slot);
		List<WeightedRandomEnchant> list = EnchantmentManager.b(random, itemstack, cost, false);
		if (list == null) {
			return Collections.emptyList();
		}
		if ((itemstack.getItem() == Items.BOOK) && (list.size() > 1)) {
			list.remove(random.nextInt(list.size()));
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	private static org.bukkit.enchantments.Enchantment toBukkit(Enchantment ench) {
		return org.bukkit.enchantments.Enchantment.getById(Enchantment.getId(ench));
	}

	@SuppressWarnings("deprecation")
	private static Enchantment toMojang(org.bukkit.enchantments.Enchantment ench) {
		return Enchantment.c(ench.getId());
	}

}
