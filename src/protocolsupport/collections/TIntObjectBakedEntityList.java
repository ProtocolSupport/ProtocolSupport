package protocolsupport.collections;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.server.v1_8_R1.Entity;

public class TIntObjectBakedEntityList implements List<Entity> {

	private TIntObjectHashMap<Entity> entityByIdMap = new TIntObjectHashMap<Entity>();
	private List<Entity> entityList;

	public TIntObjectBakedEntityList(List<Entity> entityList) {
		this.entityList = entityList;
		for (Entity entity : entityList) {
			entityByIdMap.put(entity.getId(), entity);
		}
	}

	public Entity getById(int entityId) {
		return entityByIdMap.get(entityId);
	}

	@Override
	public int size() {
		return entityList.size();
	}

	@Override
	public boolean isEmpty() {
		return entityList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}
		return entityByIdMap.containsKey(((Entity) o).getId());
	}

	@Override
	public Object[] toArray() {
		return entityList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return entityList.toArray(a);
	}

	@Override
	public boolean add(Entity e) {
		if (entityList.add(e)) {
			if (e != null) {
				entityByIdMap.put(e.getId(), e);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof Entity) {
			entityList.remove(o);
			entityByIdMap.remove(((Entity) o).getId());
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object obj : c) {
			if (!entityByIdMap.containsKey(((Entity) obj).getId())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Entity> c) {
		if (entityList.addAll(c)) {
			for (Entity entity : c) {
				entityByIdMap.put(entity.getId(), entity);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Entity> c) {
		if (entityList.addAll(index, c)) {
			for (Entity entity : c) {
				entityByIdMap.put(entity.getId(), entity);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (entityList.removeAll(c)) {
			for (Object obj : c) {
				entityByIdMap.remove(((Entity) obj).getId());
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		clear();
		for (Object entity : c) {
			add((Entity) entity);
		}
		return true;
	}

	@Override
	public void clear() {
		entityList.clear();
		entityByIdMap.clear();
	}

	@Override
	public Entity get(int index) {
		return entityList.get(index);
	}

	@Override
	public Entity set(int index, Entity element) {
		entityByIdMap.put(element.getId(), element);
		return entityList.set(index, element);
	}

	@Override
	public void add(int index, Entity element) {
		entityByIdMap.put(element.getId(), element);
		entityList.add(index, element);
	}

	@Override
	public Entity remove(int index) {
		Entity removed = entityList.remove(index);
		if (removed != null) {
			entityByIdMap.remove(removed.getId());
		}
		return removed;
	}

	@Override
	public int indexOf(Object o) {
		return entityList.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return entityList.lastIndexOf(o);
	}

	@Override
	public Iterator<Entity> iterator() {
		return listIterator();
	}

	@Override
	public ListIterator<Entity> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<Entity> listIterator(int index) {
		return new TIntObjectBakedEntityListIterator(this, index);
	}

	@Override
	public List<Entity> subList(int fromIndex, int toIndex) {
		return new TIntObjectBakedEntitySubList(this, fromIndex, toIndex);
	}

}
