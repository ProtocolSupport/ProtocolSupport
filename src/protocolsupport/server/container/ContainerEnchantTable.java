package protocolsupport.server.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.WeightedRandomEnchant;
import net.minecraft.server.v1_8_R3.World;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class ContainerEnchantTable extends net.minecraft.server.v1_8_R3.ContainerEnchantTable {

	private final Random random = new Random();

	private World world;
	private BlockPosition position;
	private Player player;

	public ContainerEnchantTable(PlayerInventory inv, World world, BlockPosition pos) {
		super(inv, world, pos);
		this.world = world;
		position = pos;
		player = (Player) inv.player.getBukkitEntity();
	}

	@Override
	public void a(final IInventory iinventory) {
		if (iinventory == enchantSlots) {
			final net.minecraft.server.v1_8_R3.ItemStack itemstack = iinventory.getItem(0);
			if (itemstack != null) {
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
				random.setSeed(f);
				for (int i = 0; i < 3; ++i) {
					costs[i] = EnchantmentManager.a(random, i, bookShelfs, itemstack);
					h[i] = -1;
					if (costs[i] < (i + 1)) {
						costs[i] = 0;
					}
				}
				final CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
				final PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(player, getBukkitView(), world.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()), item, costs, bookShelfs);
				event.setCancelled(!itemstack.v());
				world.getServer().getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					for (bookShelfs = 0; bookShelfs < 3; ++bookShelfs) {
						costs[bookShelfs] = 0;
					}
					return;
				}
				for (int i = 0; i < 3; ++i) {
					if (costs[i] > 0) {
						final List<WeightedRandomEnchant> list = getEnchantments(itemstack, i, costs[i]);
						if ((list != null) && !list.isEmpty()) {
							final WeightedRandomEnchant weightedrandomenchant = list.get(random.nextInt(list.size()));
							h[i] = (weightedrandomenchant.enchantment.id | (weightedrandomenchant.level << 8));
						}
					}
				}
				this.b();
			} else {
				for (int i = 0; i < 3; ++i) {
					costs[i] = 0;
					h[i] = -1;
				}
			}
		}
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean a(final EntityHuman entityhuman, final int slot) {
		boolean supportsLapisSlot = ProtocolSupportAPI.getProtocolVersion((Player) entityhuman.getBukkitEntity()) == ProtocolVersion.MINECRAFT_1_8;
		net.minecraft.server.v1_8_R3.ItemStack itemstack = enchantSlots.getItem(0);
		net.minecraft.server.v1_8_R3.ItemStack lapis = enchantSlots.getItem(1);
		final int cost = slot + 1;
		if (supportsLapisSlot && ((lapis == null) || (lapis.count < cost)) && !entityhuman.abilities.canInstantlyBuild) { //ignore lapis check for clients that don't support that slot
			return false;
		}
		if ((costs[slot] > 0) && (itemstack != null) && (((entityhuman.expLevel >= cost) && (entityhuman.expLevel >= costs[slot])) || entityhuman.abilities.canInstantlyBuild)) {
			List<WeightedRandomEnchant> enchantments = getEnchantments(itemstack, slot, costs[slot]);
			if (enchantments == null) {
				enchantments = new ArrayList<WeightedRandomEnchant>();
			}
			final boolean isBook = itemstack.getItem() == Items.BOOK;
			final Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
			for (final WeightedRandomEnchant enchantment : enchantments) {
				enchants.put(Enchantment.getById(enchantment.enchantment.id), enchantment.level);
			}
			final CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
			final EnchantItemEvent event = new EnchantItemEvent((Player) entityhuman.getBukkitEntity(), getBukkitView(), world.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()), item, costs[slot], enchants, slot);
			world.getServer().getPluginManager().callEvent(event);
			final int level = event.getExpLevelCost();
			if (event.isCancelled() || ((level > entityhuman.expLevel) && !entityhuman.abilities.canInstantlyBuild) || event.getEnchantsToAdd().isEmpty()) {
				return false;
			}
			if (isBook) {
				itemstack.setItem(Items.ENCHANTED_BOOK);
			}
			for (final Map.Entry<Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
				try {
					if (isBook) {
						final int enchantId = entry.getKey().getId();
						if (net.minecraft.server.v1_8_R3.Enchantment.getById(enchantId) == null) {
							continue;
						}
						final WeightedRandomEnchant enchantment = new WeightedRandomEnchant(net.minecraft.server.v1_8_R3.Enchantment.getById(enchantId), entry.getValue());
						Items.ENCHANTED_BOOK.a(itemstack, enchantment);
					} else {
						item.addUnsafeEnchantment(entry.getKey(), entry.getValue());
					}
				} catch (IllegalArgumentException ex) {
				}
			}
			entityhuman.enchantDone(supportsLapisSlot ? cost : costs[slot]); //take old levels count from clients that don't support lapis slot
			if (!entityhuman.abilities.canInstantlyBuild && supportsLapisSlot) { //ignore lapis remove for clients that don't support this slot
				final net.minecraft.server.v1_8_R3.ItemStack itemStack = lapis;
				itemStack.count -= cost;
				if (lapis.count <= 0) {
					enchantSlots.setItem(1, null);
				}
			}
			entityhuman.b(StatisticList.W);
			enchantSlots.update();
			f = entityhuman.cj();
			this.a(enchantSlots);
			return true;
		}
		return false;
	}

	private List<WeightedRandomEnchant> getEnchantments(final net.minecraft.server.v1_8_R3.ItemStack itemstack, final int slot, final int cost) {
		random.setSeed(f + slot);
		final List<WeightedRandomEnchant> list = EnchantmentManager.b(random, itemstack, cost);
		if ((itemstack.getItem() == Items.BOOK) && (list != null) && (list.size() > 1)) {
			list.remove(random.nextInt(list.size()));
		}
		return list;
	}

}
