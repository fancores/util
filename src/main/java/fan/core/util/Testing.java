package fan.core.util;

import java.awt.Desktop;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
/**
 * <p> ##################################################### </p>
 * <p> @描述：封装测试、调试常用操作的工具类 </p>
 * <p> @作者：fancy </p>
 * <p> @邮箱：fancores@163.com </p>
 * <p> @日期：2014-05-18 </p>
 * <br> ##################################################### </p>
 */
public class Testing {
	
	private Testing(){
		
	}

	/**
	 * <p> 输出对象 。支持 Collection, Map, Iterator, Pojo, 数组</p>
	 * <pre>
	 * >>> Testing.printObject(Map);
	 * >>> Testing.printObject(Set);
	 * >>> Testing.printObject(List);
	 * >>> Testing.printObject(POJO);
	 * >>> Testing.printObject(Array);
	 * >>> Testing.printObject(Iterator);
	 * >>> Testing.printObject(Collection);
	 * </pre>
	 */
	public static void printObject(Object object){
		if(object instanceof Collection){
			Collection<?> collection = (Collection<?>)object;
			if(objectIsEmpty(collection.size())) return ;
			for(Object o : collection){
				System.out.println(o);
			}
		}else if(object instanceof Map){
			Map<?, ?> map = (Map<?, ?>)object;
			if(objectIsEmpty(map.size())) return ;
			for(Object key : map.keySet()){
				System.out.println(key + " : " + map.get(key));
			}
		}else if(object instanceof Iterator){
			Iterator<?> it = (Iterator<?>)object;
			if(objectIsEmpty(it.hasNext() ? 1 : 0)) return ;
			while(it.hasNext()){
				System.out.println(it.next());
			}
		}else if(object != null && object.getClass().isArray()){
			int length = Array.getLength(object);
//			if(objectIsEmpty(length)) return ;
			for(int i = 0; i < length; i++){
				System.out.println(Array.get(object, i));
			}
		}else{
			System.out.println(object);
		}
	}
	
	/** <p> 判定对象内容是否为空 </p> */
	private static boolean objectIsEmpty(int size){
		if(size == 0){
			System.out.println("--- >>> The object argument is empty <<< ---");
			return true;
		}
		return false;
	}
	
	/**
	 * <p> 使用操作系统工具打开文件 </p>
	 * <pre>
	 * >>> Testing.openFile(new File("src/test/java/fan/core/util/test/image.jpg"));
	 * </pre>
	 */
	public static void openFile(File file){
		if(file == null){
			System.err.println("[ERROR] File must not be null");
		}else {
			try {
				System.out.println("Opening " + file.getAbsolutePath() + ", please wait.");
				Desktop.getDesktop().open(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}