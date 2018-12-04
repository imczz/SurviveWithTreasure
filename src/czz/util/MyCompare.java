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
	public static <K, V> boolean compairHashMap(AbstractMap<K, V> mapa, AbstractMap<K, V> mapb) {
		boolean ret = false;
		if (mapa == null && mapb == null) ret = true;
		else if (mapa != null && mapb != null && mapa.size() == mapb.size()){
			ret = true;
			Iterator<Entry<K, V>> iter = mapa.entrySet().iterator();
			Entry<K, V> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				if (!mapb.get(entry.getKey()).equals(entry.getValue())) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
}
