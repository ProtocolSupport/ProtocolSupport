package protocolsupport.protocol.typeremapper.itemstack.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.bukkit.Material;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftItemData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupport.utils.Utils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ItemStackLegacyData {

	private ItemStackLegacyData() {
	}

	public static final ItemStackLegacyDataRegistry REGISTRY = new ItemStackLegacyDataRegistry();

	public static class ItemStackLegacyDataRegistry extends MappingRegistry<ItemStackLegacyDataTable> {

		public ItemStackLegacyDataRegistry() {
			applyDefaultRemaps();
		}

		public void applyDefaultRemaps() {
			clear();

			JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("legacyitemtype.json"));
			for (String versionString : rootObject.keySet()) {
				ProtocolVersion version = ProtocolVersion.valueOf(versionString);
				JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
				ItemStackLegacyDataTable table = getTable(version);
				for (String itemidString : entriesObject.keySet()) {
					table.set(Integer.parseInt(itemidString), Utils.createSingletonArrayList(new ItemStackLegacyDataTypeMappingOperator(JsonUtils.getInt(entriesObject, itemidString))));
				}
			}

			set(Material.DRAGON_HEAD, new ItemStackLegacyDataOperatorDragonHeadToDragonPlayerHead(), ProtocolVersionsHelper.DOWN_1_8);


			add(Material.WRITABLE_BOOK, new ItemStackLegacyDataOperatorEmptyBookPageAdd());

			addAll(version -> new ItemStackLegacyDataOperatorEnchantFilter(GenericIdSkipper.ENCHANT.getTable(version)));
		}

		@Override
		protected ItemStackLegacyDataTable createTable() {
			return new ItemStackLegacyDataTable();
		}

		protected void set(int runtimeId, UnaryOperator<NetworkItemStack> operator, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).set(runtimeId, Utils.createSingletonArrayList(operator));
			}
		}

		protected void set(Material material, UnaryOperator<NetworkItemStack> operator, ProtocolVersion... versions) {
			set(ItemMaterialLookup.getRuntimeId(material), operator, versions);
		}

		protected void add(int runtimeId, UnaryOperator<NetworkItemStack> operator, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				getTable(version).add(runtimeId, operator);
			}
		}

		protected void add(Material material, UnaryOperator<NetworkItemStack> operator, ProtocolVersion... versions) {
			add(ItemMaterialLookup.getRuntimeId(material), operator, versions);
		}

		protected void add(Material material, UnaryOperator<NetworkItemStack> operator) {
			add(material, operator, ProtocolVersionsHelper.ALL);
		}

		protected void addAll(UnaryOperator<NetworkItemStack> operator, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				ItemStackLegacyDataTable table = getTable(version);
				MinecraftItemData.getItems().forEach(material -> table.add(ItemMaterialLookup.getRuntimeId(material), operator));
			}
		}

		protected void addAll(Function<ProtocolVersion, UnaryOperator<NetworkItemStack>> operatorSupplier, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				ItemStackLegacyDataTable table = getTable(version);
				UnaryOperator<NetworkItemStack> operator = operatorSupplier.apply(version);
				MinecraftItemData.getItems().forEach(material -> table.add(ItemMaterialLookup.getRuntimeId(material), operator));
			}
		}

		protected void addAll(Function<ProtocolVersion, UnaryOperator<NetworkItemStack>> operatorSupplier) {
			addAll(operatorSupplier, ProtocolVersionsHelper.ALL);
		}

	}

	public static class ItemStackLegacyDataTable extends MappingTable {

		@SuppressWarnings("unchecked")
		protected final List<UnaryOperator<NetworkItemStack>>[] table = new List[MinecraftItemData.ITEM_COUNT];

		public void set(int typeId, List<UnaryOperator<NetworkItemStack>> list) {
			table[typeId] = new ArrayList<>(list);
		}

		public void add(int typeId, UnaryOperator<NetworkItemStack> operator) {
			List<UnaryOperator<NetworkItemStack>> list = table[typeId];
			if (list == null) {
				list = new ArrayList<>();
				table[typeId] = list;
			}
			list.add(operator);
		}

		public List<UnaryOperator<NetworkItemStack>> get(int typeId) {
			if ((typeId >= 0) && (typeId < table.length)) {
				List<UnaryOperator<NetworkItemStack>> list = table[typeId];
				if (list != null) {
					return list;
				}
			}
			return Collections.emptyList();
		}

		public NetworkItemStack apply(NetworkItemStack itemstack) {
			for (UnaryOperator<NetworkItemStack> operator : get(itemstack.getTypeId())) {
				itemstack = operator.apply(itemstack);
			}
			return itemstack;
		}

	}

}
