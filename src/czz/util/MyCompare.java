package czz.util;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 比较工具
 * @author CZZ
 * */
public class MyCompare {
	
	/**
	 * 比较两个HashMap大小相同且键与值对应相同
	 * @param mapa 第一个HashMap
	 * @param mapb 第二个HashMap
	 * @return true相同;false不同
	 * */
	public static <K, V> boolean hashMapEquals(AbstractMap<K, V> mapa, AbstractMap<K, V> mapb) {
		boolean ret = false;
		if (mapa == null && mapb == null) ret = true;
		else if (mapa != null && mapb != null && mapa.size() == mapb.size()){
			ret = true;
			Iterator<Entry<K, V>> iter = mapa.entrySet().iterator();
			Entry<K, V> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				if (!entry.getValue().equals(mapb.get(entry.getKey()))) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 在两个HashMap大小相同且键相同时，比较值的对应关系
	 * @param mapa 第一个HashMap
	 * @param mapb 第二个HashMap
	 * @return 0两个HashMap完全相同;1amap包含bmap（b是a的子集）;-1amap包含于bmap（a是b的子集）;-2无法比较
	 * */
	public static <K, V extends Comparable<V>> int compairHashMap(AbstractMap<K, V> mapa, AbstractMap<K, V> mapb) {
		int ret = -1;
		if (mapa == null && mapb == null) ret = 0;				//均为空集
		else if (mapa != null && mapb != null && mapa.size() == mapb.size()){
			ret = 0;
			Iterator<Entry<K, V>> iter = mapa.entrySet().iterator();
			Entry<K, V> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				if (entry.getValue().compareTo(mapb.get(entry.getKey())) > 0) {
					if (ret == 0) {
						ret = 1;
					} else {
						ret = -2;
						break;
					}
				} else if (entry.getValue().compareTo(mapb.get(entry.getKey())) < 0) {
					if (ret == 0) {
						ret = -1;
					} else {
						ret = -2;
						break;
					}
				}
			}
		}
		return ret;
	}
}
