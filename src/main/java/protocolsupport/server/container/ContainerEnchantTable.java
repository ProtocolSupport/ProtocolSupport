package protocolsupport.server.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.Blocks;
import net.minecraft.server.v1_10_R1.Enchantment;
import net.minecraft.server.v1_10_R1.EnchantmentManager;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.IInventory;
import net.minecraft.server.v1_10_R1.ItemStack;
import net.minecraft.server.v1_10_R1.Items;
import net.minecraft.server.v1_10_R1.PlayerInventory;
import net.minecraft.server.v1_10_R1.StatisticList;
import net.minecraft.server.v1_10_R1.WeightedRandomEnchant;
import net.minecraft.server.v1_10_R1.World;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class ContainerEnchantTable extends net.minecraft.server.v1_10_R1.ContainerEnchantTable {

	private final Random random = new Random();

	private final World world;
	private final BlockPosition position;
	private final Player player;

	public ContainerEnchantTable(PlayerInventory inv, World world, BlockPosition pos) {
		super(inv, world, pos);
		this.world = world;
		this.position = pos;
		this.player = (Player) inv.player.getBukkitEntity();
	}

	@Override
	public void a(final IInventory iinventory) {
		if (iinventory == enchantSlots) {
			final ItemStack itemstack = iinventory.getItem(0);
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
					this.costs[i] = EnchantmentManager.a(random, i, bookShelfs, itemstack);
					this.h[i] = -1;
					this.i[i] = -1;
					if (this.costs[i] < (i + 1)) {
						this.costs[i] = 0;
					}
				}
				final PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(
					player, getBukkitView(),
					world.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()),
					CraftItemStack.asCraftMirror(itemstack), costs, bookShelfs
				);
				event.setCancelled(!itemstack.v());
				world.getServer().getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					Arrays.fill(this.costs, 0);
					return;
				}
				for (int i = 0; i < 3; ++i) {
					if (costs[i] > 0) {
						final List<WeightedRandomEnchant> list = getEnchantments(itemstack, i, costs[i]);
						if ((list != null) && !list.isEmpty()) {
							final WeightedRandomEnchant weightedrandomenchant = list.get(random.nextInt(list.size()));
							this.h[i] = Enchantment.getId(weightedrandomenchant.enchantment);
			                this.i[i] = weightedrandomenchant.level;
						}
					}
				}
				this.b();
			} else {
				Arrays.fill(this.costs, 0);
				Arrays.fill(this.h, -1);
				Arrays.fill(this.i, -1);
			}
		}
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean a(final EntityHuman entityhuman, final int slot) {
		boolean supportsLapisSlot = ProtocolSupportAPI.getProtocolVersion((Player) entityhuman.getBukkitEntity()).isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
		ItemStack itemstack = enchantSlots.getItem(0);
		ItemStack lapis = enchantSlots.getItem(1);
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
			final Map<org.bukkit.enchantments.Enchantment, Integer> enchants = new HashMap<org.bukkit.enchantments.Enchantment, Integer>();
			for (final WeightedRandomEnchant enchantment : enchantments) {
				enchants.put(org.bukkit.enchantments.Enchantment.getById(Enchantment.getId(enchantment.enchantment)), enchantment.level);
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
			for (final Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
				try {
					if (isBook) {
						final int enchantId = entry.getKey().getId();
						if (Enchantment.c(enchantId) == null) {
							continue;
						}
						final WeightedRandomEnchant enchantment = new WeightedRandomEnchant(Enchantment.c(enchantId), entry.getValue());
						Items.ENCHANTED_BOOK.a(itemstack, enchantment);
					} else {
						item.addUnsafeEnchantment(entry.getKey(), entry.getValue());
					}
				} catch (IllegalArgumentException ex) {
				}
			}
			entityhuman.enchantDone(supportsLapisSlot ? cost : costs[slot]); //take old levels count from clients that don't support lapis slot
			if (!entityhuman.abilities.canInstantlyBuild && supportsLapisSlot) { //ignore lapis remove for clients that don't support this slot
				final ItemStack itemStack = lapis;
				itemStack.count -= cost;
				if (lapis.count <= 0) {
					enchantSlots.setItem(1, null);
				}
			}
			entityhuman.b(StatisticList.Y);
			enchantSlots.update();
			f = entityhuman.cV();
			this.a(enchantSlots);
			return true;
		}
		return false;
	}

	private List<WeightedRandomEnchant> getEnchantments(final ItemStack itemstack, final int slot, final int cost) {
		random.setSeed(f + slot);
		final List<WeightedRandomEnchant> list = EnchantmentManager.b(random, itemstack, cost, false);
		if ((itemstack.getItem() == Items.BOOK) && (list != null) && (list.size() > 1)) {
			list.remove(random.nextInt(list.size()));
		}
		return list;
	}

}
