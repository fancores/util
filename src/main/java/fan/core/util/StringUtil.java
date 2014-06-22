package fan.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
/**
 * <p> ##################################################### </p>
 * <p> @描述：封装了字符串相关操作的工具类 </p>
 * <p> @作者：fancy </p>
 * <p> @邮箱：fancores@163.com </p>
 * <p> @日期：2014-05-14 </p>
 * <br> ##################################################### </p>
 */
public class StringUtil {

	/** <p> 空串 </p> */
	private static final String BLANK = "";
	/** <p> 占位符 </p> */
	private static final String PLACEHOLDER = "\\?";
	
	private StringUtil(){
		
	}
	
	/**
	 * <p> 判断对象是否为空 </p>
	 * <pre>
	 * >>> StringUtil.isEmpty("") = true
	 * >>> StringUtil.isEmpty(null) = true
	 * >>> StringUtil.isEmpty(" ") = false
	 * </pre>
	 */
	public static boolean isEmpty(String source) {
		return source == null || source.length() == 0;
	}
	
	/**
	 * <p> 首字母大写 </p>
	 * <pre>
	 * >>> String source = "when the sun shines we shine together";
	 * >>> StringUtil.toFirstLetterUpperCase(source);
	 * >>> Output : When the sun shines we shine together
	 * </pre>
	 */
	public static String toFirstLetterUpperCase(String source){
		return source.substring(0, 1).toUpperCase() + source.substring(1);
	}
	
	/**
	 * <p> 解析占位符 </p>
	 * <pre>
	 * >>> StringUtil.parsePlaceholder("email: ?", "fancores@163.com")
	 * >>> Output : email: fancores@163.com
	 * >>> StringUtil.parsePlaceholder("id(?, ?, ?, ?)", "fancy", "fancydeepin", "fancore", "fancores")
	 * >>> Output : id(fancy, fancydeepin, fancore, fancores)
	 * >>> StringUtil.parsePlaceholder("id(?, ?, ?)", "fancy", "fancydeepin", "fancore", "fancores")
	 * >>> Output : id(fancy, fancydeepin, fancore)
	 * >>> StringUtil.parsePlaceholder("id(?, ?, ?, ?)", "fancy", "fancydeepin", "fancore")
	 * >>> Output : id(fancy, fancydeepin, fancore, ?)
	 * </pre>
	 */
	public static String parsePlaceholder(String source, Object... values){
		if(source == null || values == null){
			return source;
		}
		int length = values == null ? 0 : values.length;
		for(int i = 0; i < length; i++){
			source = source.replaceFirst(PLACEHOLDER, values[i].toString());
		}
		return source;
	}
	
	/**
	 * <p> 对象的描述。支持 Collection, Map, Iterator, Pojo, 数组</p>
	 * <pre>
	 * >>> StringUtil.toString(Map);
	 * >>> return "key1 = value1, key2 = value2, ..."
	 * >>> StringUtil.toString(Set);
	 * >>> return "o1.toString(), o2.toString(), ..."
	 * >>> StringUtil.toString(List);
	 * >>> return "o1.toString(), o2.toString(), ..."
	 * >>> StringUtil.toString(POJO);
	 * >>> return "o.toString()"
	 * >>> StringUtil.toString(Array);
	 * >>> return "o1.toString(), o2.toString(), ..."
	 * >>> StringUtil.toString(Iterator);
	 * >>> return "o1.toString(), o2.toString(), ..."
	 * >>> StringUtil.toString(Collection);
	 * >>> return "o1.toString(), o2.toString(), ..."
	 * >>> StringUtil.toString(null);
	 * >>> return null
	 * </pre>
	 */
	public static String toString(Object object){
		if(object instanceof Collection){
			Collection<?> collection = (Collection<?>)object;
			String string = Arrays.toString(collection.toArray());
			return string.substring(1, string.length() - 1);
		}else if(object instanceof Map){
			Map<?, ?> map = (Map<?, ?>)object;
			if(map.size() == 0) return BLANK;
			StringBuilder builder = new StringBuilder();
			for(Object key : map.keySet()){
				builder.append(key).append(" = ").append(map.get(key)).append(", ");
			}
			int length = builder.length();
			return builder.replace(length - 2, length, BLANK).toString();
		}else if(object instanceof Iterator){
			Iterator<?> it = (Iterator<?>)object;
			if(!it.hasNext()) return BLANK;
			StringBuilder builder = new StringBuilder();
			while(it.hasNext()){
				builder.append(it.next().toString()).append(", ");
			}
			int length = builder.length();
			return builder.replace(length - 2, length, BLANK).toString();
		}else if(object != null && object.getClass().isArray()){
			String string = Arrays.toString((Object[])object);
			return string.substring(1, string.length() - 1);
		}else{
			return object == null ? null : object.toString();
		}
	}
}