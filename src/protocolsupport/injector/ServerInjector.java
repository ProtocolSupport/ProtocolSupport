package protocolsupport.injector;

import net.minecraft.server.v1_8_R1.TileEntity;

import java.lang.reflect.Field;
import java.util.Map;

import protocolsupport.server.tileentity.TileEntityEnchantTable;
import protocolsupport.utils.Utils;

public class ServerInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		registerTileEntity(TileEntityEnchantTable.class, "EnchantTable");
	}

	@SuppressWarnings("unchecked")
	private static void registerTileEntity(Class<? extends TileEntity> entityClass, String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		((Map<String, Class<? extends TileEntity>>) Utils.<Field>setAccessible(TileEntity.class.getDeclaredField("f")).get(null)).put(name, entityClass);
		((Map<Class<? extends TileEntity>, String>) Utils.<Field>setAccessible(TileEntity.class.getDeclaredField("g")).get(null)).put(entityClass, name);
	}

}
