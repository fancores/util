package fan.core.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import fan.core.util.Container.SimpleComparator.SortKey;
/**
 * <p> ##################################################### </p>
 * <p> @描述：封装了数组、集合、散列表、<b><em>集合自定义关键字排序</b></em>&nbsp;等相关操作的工具类 </p>
 * <p> @作者：fancy </p>
 * <p> @邮箱：fancores@163.com </p>
 * <p> @日期：2014-05-15 </p>
 * <br> ##################################################### </p>
 */
public class Container {
	
	private Container(){
		
	}
	
	/**
	 * <p><b><em> 将参数转换为一个数组 </b></em></p>
	 * <pre>
	 * >>> private String[] persons;
	 * >>> . . . . . . 
	 * >>> persons = Container.asArray("fan", "cai", "yan");
	 * >>> Testing.printObject(persons);
	 * </pre>
	 */
	public static <T> T[] asArray(T... object){
		return object;
	}
	
	/**
	 * <p><b><em> 将容器转换为一个数组 </b></em></p>
	 * <pre>
	 * >>> Set&lt;String&gt; personSet = new HashSet&lt;String&gt;();
	 * >>> personSet.add("fan");
	 * >>> personSet.add("cai");
	 * >>> String[] persons = Container.asArray(personSet, String.class);
	 * >>> Testing.printObject(persons);
	 * </pre>
	 */
	public static <T> T[] asArray(Collection<T> collection, Class<T> type){
		return collection.toArray(newArray(type, collection.size()));
	}
	
	/**
	 * <p><b><em> 创建数组 </b></em></p>
	 * <pre>
	 * >>> String[] persons = Container.newArray(String.class, 2);
	 * >>> persons[0] = "fan";
	 * >>> persons[1] = "cai";
	 * >>> String[][] people = Container.newArray(String[].class, 1);
	 * >>> people[0] = Container.newArray(String.class, 2);
	 * >>> people[0][0] = "yan";
	 * >>> people[0][1] = "hao";
	 * >>> Testing.printObject(persons);
	 * >>> Testing.printObject(people[0]);
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<T> type, int size){
		return (T[]) Array.newInstance(type, size);
	}
	
	/**
	 * <p><b><em> 创建数组, 并以默认的值填充元素 </b></em></p>
	 * <pre>
	 * >>> Integer[] args = Container.newArray(Integer.class, 5, -1);
	 * >>> Testing.printObject(args);
	 * </pre>
	 */
	public static <T> T[] newArray(Class<T> type, int size, T defaultValue){
		T[] array = newArray(type, size);
		Arrays.fill(array, defaultValue);
		return array;
	}
	
	/**
	 * <p><b><em> 将参数转换为一个 Set 容器 </b></em></p>
	 * <pre>
	 * >>> Set&lt;String&gt; personSet = Container.asSet("fan", "cai", "yan");
	 * >>> Testing.printObject(personSet);
	 * </pre>
	 */
	public static <T> Set<T> asSet(T... object){
		return new HashSet<T>(Arrays.asList(object));
	}
	
	/**
	 * <p><b><em> 将容器作为一个 Set 集合 </b></em></p>
	 * <pre>
	 * >>> List&lt;String&gt; personList = new ArrayList&lt;String&gt;();
	 * >>> personList.add("fan");
	 * >>> personList.add("cai");
	 * >>> Set&lt;String&gt; personSet = Container.asSet(personList);
	 * >>> Testing.printObject(personSet);
	 * </pre>
	 */
	public static <T> Set<T> asSet(Collection<T> collection){
		if(collection instanceof Set){
			return (Set<T>)collection;
		}
		return new HashSet<T>(collection);
	}
	
	/**
	 * <p><b><em> 将参数转换为一个 List 容器 </b></em></p>
	 * <pre>
	 * >>> List&lt;String&gt; personList = Container.asList("fan", "cai", "yan");
	 * >>> Testing.printObject(personList);
	 * </pre>
	 */
	public static <T> List<T> asList(T... obj){
		return Arrays.asList(obj);
	}
	
	/**
	 * <p><b><em> 将容器作为一个 List 集合 </b></em></p>
	 * <pre>
	 * >>> Set&lt;String&gt; personSet = new HashSet&lt;String&gt;();
	 * >>> personSet.add("fan");
	 * >>> personSet.add("cai");
	 * >>> List&lt;String&gt; personList = Container.asList(personSet);
	 * >>> Testing.printObject(personList);
	 * </pre>
	 */
	public static <T> List<T> asList(Collection<T> collection){
		if(collection instanceof List){
			return (List<T>)collection;
		}
		return new ArrayList<T>(collection);
	}
	
	/**
	 * <p><b><em> 将 Map 容器的 Key 集合转换成一个 List 容器 </b></em></p>
	 * <pre>
	 * >>> Map&lt;String, Person&gt; map = new HashMap&lt;String, Person&gt;();
	 * >>> map.put("person1", new Person("fan", "male"));
	 * >>> map.put("person2", new Person("cai", "female"));
	 * >>> List&lt;String&gt; keys = Container.mapKeyAsList(map);
	 * >>> Testing.printObject(keys);
	 * </pre>
	 */
	public static <K> List<K> mapKeyAsList(Map<K, ?> map){
		return new ArrayList<K>(map.keySet());
	}
	
	/**
	 * <p><b><em> 将 Map 容器的 Value 集合转换成一个 List 容器 </b></em></p>
	 * <pre>
	 * >>> Map&lt;String, Person&gt; map = new HashMap&lt;String, Person&gt;();
	 * >>> map.put("person1", new Person("fan", "male"));
	 * >>> map.put("person2", new Person("cai", "female"));
	 * >>> List&lt;Person&gt; values = Container.mapValueAsList(map);
	 * >>> Testing.printObject(values);
	 * </pre>
	 */
	public static <V> List<V> mapValueAsList(Map<?, V> map){
		return new ArrayList<V>(map.values());
	}
	
	/**
	 * <p><b><em> 创建一个 List 容器 </b></em></p>
	 * <pre>
	 * >>> List&lt;Set&lt;Map&lt;String, String&gt;&gt;&gt; list = Container.newList();
	 * >>> Testing.printObject(list);
	 * </pre>
	 */
	public static <T> List<T> newList(){
		return new ArrayList<T>();
	}
	
	/**
	 * <p><b><em> 创建一个 List 容器, 并指定容器初始大小 </b></em></p>
	 * <pre>
	 * >>> List&lt;Set&lt;Map&lt;String, String&gt;&gt;&gt; list = Container.newList(5);
	 * >>> Testing.printObject(list);
	 * </pre>
	 */
	public static <T> List<T> newList(int size){
		return new ArrayList<T>(size);
	}
	
	/**
	 * <p><b><em> 创建一个 Set 容器。默认初始化容量为 16, 加载因子为 0.75 </b></em></p>
	 * <p> 当容器存储的条目数量 >= 初始容量 * 加载因子, 容器将进行扩容操作 </p>
	 * <pre>
	 * >>> Set&lt;List&lt;Map&lt;String, String&gt;&gt;&gt; set = Container.newSet();
	 * >>> Testing.printObject(set);
	 * </pre>
	 */
	public static <T> Set<T> newSet(){
		return new HashSet<T>(16, 0.75f);
	}
	
	/**
	 * <p><b><em> 创建一个 Set 容器。自行指定初始化容量和加载因子 </b></em></p>
	 * <p> 当容器存储的条目数量 >= 初始容量 * 加载因子, 容器将进行扩容操作 </p>
	 * <pre>
	 * >>> Set&lt;List&lt;Map&lt;String, String&gt;&gt;&gt; set = Container.newSet(8, 0.7);
	 * >>> Testing.printObject(set);
	 * </pre>
	 */
	public static <T> Set<T> newSet(int initialCapacity, double loadFactor){
		return new HashSet<T>(initialCapacity, ((Double)loadFactor).floatValue());
	}
	
	/**
	 * <p><b><em> 创建一个 Map 容器。默认初始化容量为 16, 加载因子为 0.75 </b></em></p>
	 * <p> 当容器存储的条目数量 >= 初始容量 * 加载因子, 容器将进行扩容操作 </p>
	 * <pre>
	 * >>> Map&lt;String, List&lt;Set&lt;String&gt;&gt;&gt; map = Container.newMap();
	 * >>> Testing.printObject(map);
	 * </pre>
	 */
	public static <K, V> Map<K, V> newMap(){
		return new HashMap<K, V>(16, 0.75f);
	}
	
	/**
	 * <p><b><em> 创建一个 Map 容器。自行指定初始化容量和加载因子 </b></em></p>
	 * <p> 当容器存储的条目数量 >= 初始容量 * 加载因子, 容器将进行扩容操作 </p>
	 * <pre>
	 * >>> Map&lt;String, List&lt;Set&lt;String&gt;&gt;&gt; map = Container.newMap(8, 0.7);
	 * >>> Testing.printObject(map);
	 * </pre>
	 */
	public static <K, V> Map<K, V> newMap(int initialCapacity, double loadFactor){
		return new HashMap<K, V>(initialCapacity, ((Double)loadFactor).floatValue());
	}
	
	/**
	 * <p><b><em> 自定义关键字升序排序集合, 关键字支持常用的数值类型、字符类型（支持中文）、日期类型 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition Class :</em></b>
	 * >>> User(int id, String name, Date createDate)
	 * >>> <b><em>Definition Constructor :</em></b>
	 * >>> User(int id, String name, Date createDate)
	 * >>> User(int id, String name, String createDate)
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;User&gt; userList = new ArrayList&lt;User&gt;();
	 * >>> userList.add(new User(2, "叶水燕", "2014-05-12"));
	 * >>> userList.add(new User(1, "杨忠杰", "2014-05-13"));
	 * >>> userList.add(new User(4, "何国群", "2014-05-15"));
	 * >>> userList.add(new User(0, "杨晓婷", "2014-05-14"));
	 * >>> userList.add(new User(3, "钟婷婷", "2014-05-11"));
	 * >>> Container.sortByAsc(userList, User.class, "id");
	 * >>> Testing.printObject(userList);
	 * >>> <b><em>output look like :</em></b>
	 * >>> 0    杨晓婷    2014-05-14
	 * >>> 1    杨忠杰    2014-05-13
	 * >>> 2    叶水燕    2014-05-12
	 * >>> 3    钟婷婷    2014-05-11
	 * >>> 4    何国群    2014-05-15
	 * >>> <b><em>e.g.</em></b>
	 * >>> Container.sortByAsc(userList, User.class, "name");
	 * >>> Testing.printObject(userList);
	 * >>> <b><em>output look like :</em></b>
	 * >>> 4    何国群    2014-05-15
	 * >>> 0    杨晓婷    2014-05-14
	 * >>> 1    杨忠杰    2014-05-13
	 * >>> 2    叶水燕    2014-05-12
	 * >>> 3    钟婷婷    2014-05-11
	 * >>> <b><em>e.g.</em></b>
	 * >>> Container.sortByAsc(userList, User.class, "createDate");
	 * >>> Testing.printObject(userList);
	 * >>> <b><em>output look like :</em></b>
	 * >>> 3    钟婷婷    2014-05-11
	 * >>> 2    叶水燕    2014-05-12
	 * >>> 1    杨忠杰    2014-05-13
	 * >>> 0    杨晓婷    2014-05-14
	 * >>> 4    何国群    2014-05-15
	 * </pre>
	 */
	public static <T> void sortByAsc(Collection<T> collection, Class<T> entityClass, String key){
		sortBySortKey(collection, entityClass, key, SortKey.ASC);
	}
	
	/**
	 * <p><b><em> 自定义关键字降序排序集合, 关键字支持常用的数值类型、字符类型（支持中文）、日期类型 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition Class :</em></b>
	 * >>> User(int id, String name, Date createDate)
	 * >>> <b><em>Definition Constructor :</em></b>
	 * >>> User(int id, String name, Date createDate)
	 * >>> User(int id, String name, String createDate)
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;User&gt; userList = new ArrayList&lt;User&gt;();
	 * >>> userList.add(new User(2, "叶水燕", "2014-05-12"));
	 * >>> userList.add(new User(1, "杨忠杰", "2014-05-13"));
	 * >>> userList.add(new User(4, "何国群", "2014-05-15"));
	 * >>> userList.add(new User(0, "杨晓婷", "2014-05-14"));
	 * >>> userList.add(new User(3, "钟婷婷", "2014-05-11"));
	 * >>> Container.sortByDesc(userList, User.class, "id");
	 * >>> Testing.printObject(userList);
	 * >>> <b><em>output look like :</em></b>
	 * >>> 4    何国群    2014-05-15
	 * >>> 3    钟婷婷    2014-05-11
	 * >>> 2    叶水燕    2014-05-12
	 * >>> 1    杨忠杰    2014-05-13
	 * >>> 0    杨晓婷    2014-05-14
	 * >>> <b><em>e.g.</em></b>
	 * >>> Container.sortByDesc(userList, User.class, "name");
	 * >>> Testing.printObject(userList);
	 * >>> <b><em>output look like :</em></b>
	 * >>> 3    钟婷婷    2014-05-11
	 * >>> 2    叶水燕    2014-05-12
	 * >>> 1    杨忠杰    2014-05-13
	 * >>> 0    杨晓婷    2014-05-14
	 * >>> 4    何国群    2014-05-15
	 * >>> <b><em>e.g.</em></b>
	 * >>> Container.sortByDesc(userList, User.class, "createDate");
	 * >>> Testing.printObject(userList);
	 * >>> <b><em>output look like :</em></b>
	 * >>> 4    何国群    2014-05-15
	 * >>> 0    杨晓婷    2014-05-14
	 * >>> 1    杨忠杰    2014-05-13
	 * >>> 2    叶水燕    2014-05-12
	 * >>> 3    钟婷婷    2014-05-11
	 * </pre>
	 */
	public static <T> void sortByDesc(Collection<T> collection, Class<T> entityClass, String key){
		sortBySortKey(collection, entityClass, key, SortKey.DESC);
	}
	
	/** <p><b><em> 根据排序关键字排序集合 </b></em></p> */
	@SuppressWarnings("unchecked")
	private static <T> void sortBySortKey(Collection<T> collection, Class<T> entityClass, String key, SortKey sortKey){
		Object[] source = asArray(collection, entityClass);
		SimpleComparator comparator = new SimpleComparator(entityClass, key, sortKey);
		quicksort(source, 0, source.length - 1, comparator);
		collection.clear();
		collection.addAll((List<T>)asList(source));
	}
	
	/** <p><b><em> 快速排序算法 </b></em></p> */
	private static void quicksort(Object[] array, int low,int hight, SimpleComparator comparator){
		if(low < hight){
			int position = partition(array, low, hight, comparator);
			quicksort(array, low, position - 1, comparator);
			quicksort(array, position + 1, hight, comparator);
		}
	}

	/** <p><b><em> 快速排序算法 </b></em></p> */
	private static int partition(Object[] array, int low,int hight, SimpleComparator comparator){
		Object key = array[low];
		while(low < hight){
			while(low < hight && comparator.compare(array[hight], key) >= 0){
				hight--;
			}
			array[low] = array[hight];
			while(low < hight && comparator.compare(array[low], key) <= 0){
				low++;
			}
			array[hight] = array[low];
		}
		array[low] = key;
		return low;
	}
	
	/**
	 * <p> ##################################################### </p>
	 * <p> @描述：比较器。提供 compare 方法比较两关键字的大小 </p>
	 * <p> @作者：fancy </p>
	 * <p> @邮箱：fancores@163.com </p>
	 * <p> @日期：2014-05-15 </p>
	 * <br> ##################################################### </p>
	 */
	static class SimpleComparator {

		/** <p><b><em> 关键字 </b></em></p> */
		private String key;
		/** <p><b><em> 关键字的类型 </b></em></p> */
		private KeyType keyType;
		/** <p><b><em> 是否是升序排序 </b></em></p> */
		private boolean isAsc;
		
		/** <p><b><em> 记录关键字, 反射获取关键字类型, 确定排序方式 </b></em></p> */
		public SimpleComparator(Class<?> entityClass, String key, SortKey sortKey){
			this.key = key;
			try {
				this.keyType = KeyType.valueOf(Reflection.getFieldType(entityClass, key));
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			isAsc = sortKey.ordinal() == SortKey.ASC.ordinal();
		}

		/** <p><b><em> 反射获取关键字的值, 并根据关键字的类型比较关键字值的大小 </b></em></p> */
		public int compare(Object o1, Object o2){
			try {
				switch (keyType) {
					case NUMBER :
						return numberCompare(value(o1), value(o2));
					case STRING :
						return StringCompare(value(o1), value(o2));
					case DATE :
						return dateCompare(value(o1), value(o2));
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			throw new UnsupportedException(key + " is not supported.");
		}

		/** <p><b><em> 比较两个数值类型关键字的大小 </b></em></p> */
		private int numberCompare(Object o1, Object o2) {
			BigDecimal n1 = new BigDecimal(o1.toString());
			BigDecimal n2 = new BigDecimal(o2.toString());
			return isAsc ? n1.compareTo(n2) : n2.compareTo(n1);
		}

		/** <p><b><em> 比较两个字符类型关键字的大小 </b></em></p> */
		private int StringCompare(Object o1, Object o2) throws UnsupportedEncodingException {
			String s1 = new String(o1.toString().getBytes("GBK"), "ISO-8859-1");
			String s2 = new String(o2.toString().getBytes("GBK"), "ISO-8859-1");
			return isAsc ? s1.compareTo(s2) : s2.compareTo(s1);
		}

		/** <p><b><em> 比较两个日期类型关键字的大小 </b></em></p> */
		private int dateCompare(Object o1, Object o2) {
			return isAsc ? ((Date)o1).compareTo((Date)o2) : ((Date)o2).compareTo((Date)o1);
		}

		/** <p><b><em> 反射获取关键字的值 </b></em></p> */
		private Object value(Object o) throws NoSuchFieldException {
			return Reflection.getFieldValue(o, key);
		}
		
		/**
		 * <p> ##################################################### </p>
		 * <p> @描述：支持的关键字类型<pre>NUMBER("数值类型"), STRING("字符类型"), DATE("日期类型")</pre></p>
		 * <p> @作者：fancy </p>
		 * <p> @邮箱：fancores@163.com </p>
		 * <p> @日期：2014-05-15 </p>
		 * <br> ##################################################### </p>
		 */
		enum KeyType {
			
			NUMBER("数值类型"), STRING("字符类型"), DATE("日期类型");
			
			private String value;
			/** <p><b><em> 支持的数值类型 </b></em></p> */
			private static final List<String> numberContainer = asList(
				"byte", "short", "int", "long", "float", "double",
				"Byte", "Short", "Integer", "Long", "Float", "Double"
			);
			
			private KeyType(String value){
				this.value = value;
			}

			/** <p><b><em> 根据关键字类型确定比较类型 </b></em></p> */
			public static KeyType valueOf(Class<?> type){
				String name = type.getSimpleName();
				if(numberContainer.contains(name)){
					return NUMBER;
				}else if(name.equals("String")){
					return STRING;
				}else if(name.equals("Date")){
					return DATE;
				}
				throw new UnsupportedException(type.getName() + " is not supported.");
			}

			@Override
			public String toString() {
				return value;
			}
			
		}
		
		/**
		 * <p> ##################################################### </p>
		 * <p> @描述：支持的排序类型<pre>ASC("升序"), DESC("降序")</pre></p>
		 * <p> @作者：fancy </p>
		 * <p> @邮箱：fancores@163.com </p>
		 * <p> @日期：2014-05-15 </p>
		 * <br> ##################################################### </p>
		 */
		enum SortKey {
			
			ASC("升序"), DESC("降序");
			
			private String value;
			
			private SortKey(String value){
				this.value = value;
			}

			@Override
			public String toString() {
				return value;
			}
			
		}
		
		/**
		 * <p> ##################################################### </p>
		 * <p> @描述：自定义异常 </p>
		 * <p> @作者：fancy </p>
		 * <p> @邮箱：fancores@163.com </p>
		 * <p> @日期：2014-05-15 </p>
		 * <br> ##################################################### </p>
		 */
		static class UnsupportedException extends RuntimeException {

			private static final long serialVersionUID = 1L;

			public UnsupportedException(String message){
				System.err.println(message);
			}
		}
		
	}
}