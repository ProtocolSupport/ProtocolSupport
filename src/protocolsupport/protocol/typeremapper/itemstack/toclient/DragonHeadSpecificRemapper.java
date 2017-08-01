package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.utils.GameProfileSerializer;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.protocol.utils.authlib.UUIDTypeAdapter;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class DragonHeadSpecificRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		if (itemstack.getData() == 5) {
			itemstack.setData(3);
			NBTTagCompoundWrapper wrapper = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			wrapper.setCompound("SkullOwner", createTag());
			itemstack.setTag(wrapper);
		}
		return itemstack;
	}

	private static final GameProfile dragonHeadGameProfile = new GameProfile(UUIDTypeAdapter.fromString("d34aa2b831da4d269655e33c143f096c"), "EnderDragon");
	static {
		dragonHeadGameProfile.getProperties().put("textures", new ProfileProperty(
			"textures", "eyJ0aW1lc3RhbXAiOjE0NzE0Mzg3NTEzMjYsInByb2ZpbGVJZCI6ImQzNGFhMmI4MzFkYTRkMjY5NjU1ZTMzYzE0M2YwOTZjIiwicHJvZmlsZU5hbWUiOiJFbmRlckRyYWdvbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRlYzdhNGRlMDdiN2VkZWFjZTliOWI5OTY2NDRlMjFkNThjYmRhYzM5MmViZWIyNDdhY2NkMTg1MzRiNTQifX19",
			"I/XY6is7j0RTGFwuVhc4PVuWUl5RRNToNUpw6jCF+V4TzXYzsBreiLQ6IM4NSJqlPXBmSlqibblDKVQL/4LnXt24/nRNVzChZv68HXB1quGxmYcSEvOLpoUk1cmXgqWJYHA68C+r1ytoJVerGWuIwuBzQZ9GpH0yb+qg7erFQgB24cbH6hh6uB6KYLwIYLQAg3TFjILv9sVJtC3FakXmtkCV3VfRUdjhigpfKP0JR3VhLVIWeW/7E+C4QCXnGlffc3Lz8PNahXtD4qRitVokId0t1qBcL8mM1qnZ/rHlNPLST61ycY9WNlRr6P83yDw2ha8QMiRH1vI5tvdXIwV7Dkn+JxfhOOeHtGunLBVe7ZEWBZfjePr/HqZGR6F7/cwZU32uH5MdTXQ+oKWUlb6HJOXxj7DfMr/uZWjrwjzmKpSDAinwvQM/8Sf96prufvcSfhZ0yopkumpnTjivgPxsJhwFXThIyFZ3ijTClMgm5NSzmB6hJ+HsBnVkDs7eyE5eI72/ES/6SksFezmBzDOqU31QbPA2mWoOYWdyZngtnf45oFnZ7NNpDW7ZKxY7FTsPEXoON/VX516KbnQ5OERI9YUpGzyKCjyMnf0L99gwgHpx5LpawdzIwk04sqFoy796BkGJf7xH6+h+AurMIenMt4on7T4FUE1ZaJvvaqexQME="
		));
	}

	public static NBTTagCompoundWrapper createTag() {
		return GameProfileSerializer.serialize(dragonHeadGameProfile);
	}

}
