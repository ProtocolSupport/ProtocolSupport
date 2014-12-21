package protocolsupport.server.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import protocolsupport.protocol.DataStorage;
import protocolsupport.protocol.DataStorage.ProtocolVersion;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Blocks;
import net.minecraft.server.v1_8_R1.EnchantmentManager;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.IInventory;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.PlayerInventory;
import net.minecraft.server.v1_8_R1.WeightedRandomEnchant;
import net.minecraft.server.v1_8_R1.World;

public class ContainerEnchantTable extends net.minecraft.server.v1_8_R1.ContainerEnchantTable {

	private final Random random = new Random();

	private World world;
	private BlockPosition position;
	private Player player;

	public ContainerEnchantTable(PlayerInventory inv, World world, BlockPosition pos) {
		super(inv, world, pos);
		this.world = world;
		this.position = pos;
		this.player = (Player) inv.player.getBukkitEntity();
	}

	@Override
	public void a(final IInventory iinventory) {
		if (iinventory == this.enchantSlots) {
			final net.minecraft.server.v1_8_R1.ItemStack itemstack = iinventory.getItem(0);
			if (itemstack != null) {
				int bookShelfs = 0;
				for (int z = -1; z <= 1; ++z) {
					for (int x = -1; x <= 1; ++x) {
						if ((z != 0 || x != 0) && this.world.isEmpty(this.position.a(x, 0, z)) && this.world.isEmpty(this.position.a(x, 1, z))) {
							if (this.world.getType(this.position.a(x * 2, 0, z * 2)).getBlock() == Blocks.BOOKSHELF) {
								++bookShelfs;
							}
							if (this.world.getType(this.position.a(x * 2, 1, z * 2)).getBlock() == Blocks.BOOKSHELF) {
								++bookShelfs;
							}
							if (x != 0 && z != 0) {
								if (this.world.getType(this.position.a(x * 2, 0, z)).getBlock() == Blocks.BOOKSHELF) {
									++bookShelfs;
								}
								if (this.world.getType(this.position.a(x * 2, 1, z)).getBlock() == Blocks.BOOKSHELF) {
									++bookShelfs;
								}
								if (this.world.getType(this.position.a(x, 0, z * 2)).getBlock() == Blocks.BOOKSHELF) {
									++bookShelfs;
								}
								if (this.world.getType(this.position.a(x, 1, z * 2)).getBlock() == Blocks.BOOKSHELF) {
									++bookShelfs;
								}
							}
						}
					}
				}
				this.random.setSeed(this.f);
				for (int i = 0; i < 3; ++i) {
					this.costs[i] = EnchantmentManager.a(this.random, i, bookShelfs, itemstack);
					this.h[i] = -1;
					if (this.costs[i] < i + 1) {
						this.costs[i] = 0;
					}
				}
				final CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
				final PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(this.player, this.getBukkitView(), this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), item, this.costs, bookShelfs);
				event.setCancelled(!itemstack.v());
				this.world.getServer().getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					for (bookShelfs = 0; bookShelfs < 3; ++bookShelfs) {
						this.costs[bookShelfs] = 0;
					}
					return;
				}
				for (int i = 0; i < 3; ++i) {
					if (this.costs[i] > 0) {
						final List<WeightedRandomEnchant> list = this.getEnchantments(itemstack, i, this.costs[i]);
						if (list != null && !list.isEmpty()) {
							final WeightedRandomEnchant weightedrandomenchant = list.get(this.random.nextInt(list.size()));
							this.h[i] = (weightedrandomenchant.enchantment.id | weightedrandomenchant.level << 8);
						}
					}
				}
				this.b();
			} else {
				for (int i = 0; i < 3; ++i) {
					this.costs[i] = 0;
					this.h[i] = -1;
				}
			}
		}
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean a(final EntityHuman entityhuman, final int slot) {
		boolean supportsLapisSlot = DataStorage.getVersion(player.getAddress()) == ProtocolVersion.MINECRAFT_1_8;
		net.minecraft.server.v1_8_R1.ItemStack itemstack = this.enchantSlots.getItem(0);
		net.minecraft.server.v1_8_R1.ItemStack lapis = this.enchantSlots.getItem(1);
		final int cost = slot + 1;
		if (supportsLapisSlot && (lapis == null || lapis.count < cost) && !entityhuman.abilities.canInstantlyBuild) { //ignore lapis check for clients that don't support that slot
			return false;
		}
		if (this.costs[slot] > 0 && itemstack != null && ((entityhuman.expLevel >= cost && entityhuman.expLevel >= this.costs[slot]) || entityhuman.abilities.canInstantlyBuild)) {
			List<WeightedRandomEnchant> enchantments = this.getEnchantments(itemstack, slot, this.costs[slot]);
			if (enchantments == null) {
				enchantments = new ArrayList<WeightedRandomEnchant>();
			}
			final boolean isBook = itemstack.getItem() == Items.BOOK;
			if (enchantments != null) {
				final Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
				for (final WeightedRandomEnchant enchantment : enchantments) {
					enchants.put(Enchantment.getById(enchantment.enchantment.id), enchantment.level);
				}
				final CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
				final EnchantItemEvent event = new EnchantItemEvent((Player) entityhuman.getBukkitEntity(), this.getBukkitView(), this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), item, this.costs[slot], enchants, slot);
				this.world.getServer().getPluginManager().callEvent(event);
				final int level = event.getExpLevelCost();
				if (event.isCancelled() || (level > entityhuman.expLevel && !entityhuman.abilities.canInstantlyBuild) || event.getEnchantsToAdd().isEmpty()) {
					return false;
				}
				if (isBook) {
					itemstack.setItem(Items.ENCHANTED_BOOK);
				}
				for (final Map.Entry<Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
					try {
						if (isBook) {
							final int enchantId = entry.getKey().getId();
							if (net.minecraft.server.v1_8_R1.Enchantment.getById(enchantId) == null) {
								continue;
							}
							final WeightedRandomEnchant enchantment = new WeightedRandomEnchant(net.minecraft.server.v1_8_R1.Enchantment.getById(enchantId), entry.getValue());
							Items.ENCHANTED_BOOK.a(itemstack, enchantment);
						} else {
							item.addUnsafeEnchantment(entry.getKey(), entry.getValue());
						}
					} catch (IllegalArgumentException ex) {
					}
				}
				entityhuman.b(supportsLapisSlot ? cost : this.costs[slot]); //take old levels count from clients that don't support lapis slot
				if (!entityhuman.abilities.canInstantlyBuild && supportsLapisSlot) { //ignore lapis remove for clients that don't support this slot
					final net.minecraft.server.v1_8_R1.ItemStack itemStack = lapis;
					itemStack.count -= cost;
					if (lapis.count <= 0) {
						this.enchantSlots.setItem(1, null);
					}
				}
				this.enchantSlots.update();
				this.f = entityhuman.ci();
				this.a(this.enchantSlots);
			}
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private List<WeightedRandomEnchant> getEnchantments(final net.minecraft.server.v1_8_R1.ItemStack itemstack, final int slot, final int cost) {
		this.random.setSeed(this.f + slot);
		final List<WeightedRandomEnchant> list = EnchantmentManager.b(this.random, itemstack, cost);
		if (itemstack.getItem() == Items.BOOK && list != null && list.size() > 1) {
			list.remove(this.random.nextInt(list.size()));
		}
		return list;
	}

}
