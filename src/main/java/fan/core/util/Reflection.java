package fan.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * <p> ##################################################### </p>
 * <p> @描述：封装了对属性、方法、构造、泛型等反射相关操作的工具类 </p>
 * <p> @作者：fancy </p>
 * <p> @邮箱：fancores@163.com </p>
 * <p> @日期：2014-05-10 </p>
 * <br> ##################################################### </p>
 */
public class Reflection {
	
	private Reflection(){
		
	}
	
	/**
	 * <p><b><em> 设置字段属性的值 </em></b></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> Children child = new Children();
	 * >>> Reflection.setFieldValue(child, "firstName", "zhong rong");
	 * >>> Reflection.setFieldValue(child, "lastName", "fan");
	 * >>> System.out.println(child);
	 * >>> <b><em>output look like :</em></b>
	 * >>> first name : zhong rong, last name : fan
	 * >>> <b><em>Definition:</em></b>
	 * >>> Children(<b><em>static</em></b> double versionCode = 1.21)
	 * >>> <b><em>e.g.</em></b>
	 * >>> Reflection.setFieldValue(Children.class, "versionCode", 1.22);
	 * >>> System.out.println(Children.getVersionCode());
	 * >>> <b><em>output look like :</em></b>
	 * >>> versionCode = 1.22
	 * </pre>
	 */
	public static void setFieldValue(Object entity, String field, Object value) throws NoSuchFieldException {
		try {
			getAccessibleField(entity, field).set(entity, value);
		} catch (Exception e) {
			throw new NoSuchFieldException(
				StringUtil.parsePlaceholder("类 ? 中找不到 ? 属性", 
					getEntityClassName(entity), field
				)
			);
		}
	}
	
	/**
	 * <p><b><em> 获取字段属性的值 </em></b></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> Children child = new Children("zhong rong", "fan");
	 * >>> String firstName = Reflection.getFieldValue(child, "firstName");
	 * >>> <b><em>output look like :</em></b>
	 * >>> first name : zhong rong
	 * >>> String lastName = Reflection.getFieldValue(child, "lastName");
	 * >>> <b><em>output look like :</em></b>
	 * >>> last name : fan
	 * >>> <b><em>Definition:</em></b>
	 * >>> Children(<b><em>static</em></b> double versionCode = 1.21)
	 * >>> <b><em>e.g.</em></b>
	 * >>> Reflection.getFieldValue(Children.class, "versionCode");
	 * >>> <b><em>output look like :</em></b>
	 * >>> versionCode = 1.21
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object entity, String field) throws NoSuchFieldException {
		try {
			return (T) getAccessibleField(entity, field).get(entity);
		} catch (Exception e) {
			throw new NoSuchFieldException(
				StringUtil.parsePlaceholder("类 ? 中找不到 ? 属性", 
					getEntityClassName(entity), field
				)
			);
		}
	}
	
	/**
	 * <p><b><em> 调用字段属性的 set 方法 </em></b></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> Children child = new Children();
	 * >>> Reflection.callSetterMethod(child, "firstName", "zhong rong");
	 * >>> Reflection.callSetterMethod(child, "lastName", "fan");
	 * >>> System.out.println(child);
	 * >>> <b><em>output look like :</em></b>
	 * >>> first name : zhong rong, last name : fan
	 * >>> <b><em>Definition:</em></b>
	 * >>> Children(<b><em>static</em></b> double versionCode = 1.21)
	 * >>> <b><em>e.g.</em></b>
	 * >>> Reflection.callSetterMethod(Children.class, "versionCode", 1.22);
	 * >>> System.out.println(Children.getVersionCode());
	 * >>> <b><em>output look like :</em></b>
	 * >>> versionCode = 1.22
	 * </pre>
	 */
	public static void callSetterMethod(Object entity, String field, Object value) throws Throwable {
		String method = "set" + StringUtil.toFirstLetterUpperCase(field);
		invokeMethod(entity, method, new Object[]{value}, new Class<?>[]{getFieldType(entity, field)});
	}
	
	/**
	 * <p><b><em> 调用字段属性的 get 方法 </em></b></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> Children child = new Children("zhong rong", "fan");
	 * >>> String firstName = Reflection.callGetterMethod(child, "firstName");
	 * >>> <b><em>output look like :</em></b>
	 * >>> first name : zhong rong
	 * >>> String lastName = Reflection.callGetterMethod(child, "lastName");
	 * >>> <b><em>output look like :</em></b>
	 * >>> last name : fan
	 * >>> <b><em>Definition:</em></b>
	 * >>> Children(<b><em>static</em></b> double versionCode = 1.21)
	 * >>> <b><em>e.g.</em></b>
	 * >>> Reflection.callGetterMethod(Children.class, "versionCode");
	 * >>> <b><em>output look like :</em></b>
	 * >>> versionCode = 1.21
	 * </pre>
	 */
	public static <T> T callGetterMethod(Object entity, String field) throws NoSuchMethodException {
		String method = "get" + StringUtil.toFirstLetterUpperCase(field);
		return invokeMethod(entity, method, null, null);
	}
	
	/**
	 * <p><b><em> 调用构造方法 </em></b></p>
	 * <pre>
	 * >>> <b><em>Definition Constructor :</em></b>
	 * >>> <b><em>default</em></b> Children(String firstName, String lastName, double code)
	 * >>> <b><em>e.g.</em></b>
	 * >>> Object[] argValues = {"zhong rong", "fan", 1.22};
	 * >>> Class<?>[] argTypes = {String.class, String.class, double.class};
	 * >>> Children child = Reflection.callConstructor(Children.class, argValues, argTypes);
	 * >>> System.out.println(child);
	 * >>> <b><em>output look like :</em></b>
	 * >>> first name : zhong rong, last name : fan, version code : 1.22
	 * </pre>
	 */
	public static <T> T callConstructor(Class<T> targetClass, Object[] argValues, Class<?>[] argTypes) throws Throwable {
		try {
			Constructor<T> constructor = targetClass.getDeclaredConstructor(argTypes);
			constructor.setAccessible(true);
			return constructor.newInstance(argValues);
		} catch (Throwable e) {
			throw e;
		}
	}
	
	/**
	 * <p><b><em> 调用方法 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> Children child = new Children();
	 * >>> Reflection.invokeMethod(child, "setLastName", new Object[]{"fan"}, new Class<?>[]{String.class});
	 * >>> String lastName = Reflection.invokeMethod(child, "getLastName", null, null);
	 * >>> System.out.println(lastName);
	 * >>> <b><em>output look like :</em></b>
	 * >>> last name : fan
	 * >>> <b><em>Definition:</em></b>
	 * >>> Children(<b><em>static</em></b> double versionCode = 1.21)
	 * >>> <b><em>e.g.</em></b>
	 * >>> Reflection.invokeMethod(Children.class, "setVersionCode", new Object[]{1.22}, new Class<?>[]{double.class});
	 * >>> double versionCode = Reflection.invokeMethod(Children.class, "getVersionCode", null, null);
	 * >>> System.out.println(versionCode);
	 * >>> <b><em>output look like :</em></b>
	 * >>> version code : 1.22
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Object entity, String method, Object[] argValues, Class<?>[] argTypes) throws NoSuchMethodException{
		try {
			return (T) getAccessibleMethod(entity, method, argTypes).invoke(entity, argValues);
		} catch (Exception e) {
			throw new NoSuchMethodException(
				StringUtil.parsePlaceholder("类 ? 中找不到 ?(?) 方法", 
					getEntityClassName(entity), method, StringUtil.toString(argTypes)
				)
			);
		}
	}
	
	/**
	 * <p><b><em> 获取父类中声明的泛型参数类型 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition Class :</em></b>
	 * >>> public <b><em>class</em></b> BaseModel&lt;K, V&gt; {
	 * >>>     public BaseModel(){
	 * >>>         Class<?> classK = Reflection.getSuperclassGenericType(getClass(), 0);
	 * >>>         Class<?> classV = Reflection.getSuperclassGenericType(getClass(), 1);
	 * >>>         System.out.println("K : " + (classK == null ? "null" : classK.getName()));
	 * >>>         System.out.println("V : " + (classV == null ? "null" : classV.getName()));
	 * >>>     }
	 * >>> }
	 * >>> public <b><em>class</em></b> PersonModel <b><em>extends</em></b> BaseModel&lt;String, Person&gt; {
	 * >>> 
	 * >>> }
	 * >>> <b><em>e.g.</em></b>
	 * >>> new PersonModel();
	 * >>> <b><em>output look like :</em></b>
	 * >>> K : java.lang.String
	 * >>> V : fan.core.test.model.Person
	 * >>> <b><em>e.g.</em></b>
	 * >>> new BaseModel();
	 * >>> <b><em>output look like :</em></b>
	 * >>> K : null
	 * >>> V : null
	 * >>> <b><em>e.g.</em></b>
	 * >>> Class<?> clazz = Reflection.getSuperclassGenericType("".getClass(), 0);
	 * >>> <b><em>output look like :</em></b>
	 * >>> clazz : null
	 * </pre>
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> Class<T> getSuperclassGenericType(Class<?> clazz, int index){
		try {
			Type type = clazz.getGenericSuperclass();
	        Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
	        return (Class<T>)parameterizedType[index];
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * <p><b><em> 获取当前类的字段属性的列表, 并将字段属性设置为可访问 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;Field&gt; fieldList = Reflection.getCurrentClassFieldList(Children.class);
	 * >>> for(Field field : fieldList){
	 * >>>     System.out.println(field.getName());
	 * >>> }
	 * >>> <b><em>output look like :</em></b>
	 * >>> firstName
	 * </pre>
	 */
	public static List<Field> getCurrentClassFieldList(Class<?> clazz){
		if(clazz == null){
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
		}
		return Arrays.asList(fields);
	}
	
	/**
	 * <p><b><em> 获取当前类的字段属性名称的列表 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;String&gt; fieldNameList = Reflection.getCurrentClassFieldNameList(Children.class);
	 * >>> for(String fieldName : fieldNameList){
	 * >>>     System.out.println(fieldName);
	 * >>> }
	 * >>> <b><em>output look like :</em></b>
	 * >>> firstName
	 * </pre>
	 */
	public static List<String> getCurrentClassFieldNameList(Class<?> clazz){
		if(clazz == null){
			return null;
		}
		Field[] fieldList = clazz.getDeclaredFields();
		List<String> fieldNameList = new ArrayList<String>(fieldList.length);
		for(Field field : fieldList){
			fieldNameList.add(field.getName());
		}
		return fieldNameList;
	}
	
	/**
	 * <p><b><em> 获取当前类的方法的列表, 并将方法设置为可访问 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;Method&gt; methodList = Reflection.getCurrentClassMethodList(Children.class);
	 * >>> for(Method method : methodList){
	 * >>>     System.out.println(method);
	 * >>> }
	 * >>> <b><em>output look like :</em></b>
	 * >>> public java.lang.String fan.core.test.model.Children.toString()
	 * >>> public java.lang.String fan.core.test.model.Children.getFirstName()
	 * >>> public void fan.core.test.model.Children.setFirstName(java.lang.String)
	 * </pre>
	 */
	public static List<Method> getCurrentClassMethodList(Class<?> clazz){
		if(clazz == null){
			return null;
		}
		Method[] methods = clazz.getDeclaredMethods();
		for(Method method : methods){
			method.setAccessible(true);
		}
		return Arrays.asList(methods);
	}
	
	/**
	 * <p><b><em> 获取当前类的方法名称的列表 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;String&gt; methodNameList = Reflection.getCurrentClassMethodNameList(Children.class);
	 * >>> for(String methodName : methodNameList){
	 * >>>     System.out.println(methodName);
	 * >>> }
	 * >>> <b><em>output look like :</em></b>
	 * >>> toString
	 * >>> getFirstName
	 * >>> setFirstName
	 * </pre>
	 */
	public static List<String> getCurrentClassMethodNameList(Class<?> clazz){
		if(clazz == null){
			return null;
		}
		Method[] methodList = clazz.getDeclaredMethods();
		List<String> methodNameList = new ArrayList<String>(methodList.length);
		for(Method method : methodList){
			methodNameList.add(method.getName());
		}
		return methodNameList;
	}
	
	/**
	 * <p><b><em> 获取字段的类型 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> Class<?> firstNameType = Reflection.getFieldType(Children.class, "firstName");
	 * >>> Class<?> lastNameType = Reflection.getFieldType(Children.class, "lastName");
	 * >>> <b><em>output look like :</em></b>
	 * >>> java.lang.String
	 * </pre>
	 */
	public static Class<?> getFieldType(Object entity, String field) throws NoSuchFieldException {
		try {
			return getAccessibleField(entity, field).getType();
		} catch (NullPointerException e) {
			throw new NoSuchFieldException(
				StringUtil.parsePlaceholder("类 ? 中找不到 ? 属性", 
					getEntityClassName(entity), field
				)
			);
		}
	}
	
	/**
	 * <p><b><em> 获取方法列表, 并将方法设置为可访问 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;Method&gt; methodList = Reflection.getDeclaredMethodList(Children.class);
	 * >>> for(Method method : methodList){
	 * >>>     System.out.println(method);
	 * >>> }
	 * >>> <b><em>output look like :</em></b>
	 * >>> public java.lang.String fan.core.test.model.Children.toString()
	 * >>> public java.lang.String fan.core.test.model.Children.getFirstName()
	 * >>> public void fan.core.test.model.Children.setFirstName(java.lang.String)
	 * >>> public java.lang.String fan.core.test.model.Parents.toString()
	 * >>> public void fan.core.test.model.Parents.setLastName(java.lang.String)
	 * >>> public java.lang.String fan.core.test.model.Parents.getLastName()
	 * >>> public java.lang.String fan.core.test.model.Parents.getFirstName()
	 * >>> public void fan.core.test.model.Parents.setFirstName(java.lang.String)
	 * >>> public boolean java.lang.Object.equals(java.lang.Object)
	 * >>> public native int java.lang.Object.hashCode()
	 * >>> . . . . . .
	 * </pre>
	 */
	public static List<Method> getDeclaredMethodList(Class<?> entityClass){
		if(entityClass == null){
			return null;
		}
		List<Method> methods = new ArrayList<Method>();
		while(entityClass != null){
			methods.addAll(getCurrentClassMethodList(entityClass));
			entityClass = entityClass.getSuperclass();
		}
		return methods;
	}
	
	/**
	 * <p><b><em> 获取方法名称列表 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;String&gt; methodNameList = Reflection.getDeclaredMethodNameList(Children.class);
	 * >>> for(String methodName : methodNameList){
	 * >>>     System.out.println(methodName);
	 * >>> }
	 * >>> <b><em>output look like :</em></b>
	 * >>> toString
	 * >>> getFirstName
	 * >>> setFirstName
	 * >>> toString
	 * >>> getFirstName
	 * >>> setFirstName
	 * >>> equals
	 * >>> hashCode
	 * >>> . . . . . .
	 * </pre>
	 */
	public static List<String> getDeclaredMethodNameList(Class<?> entityClass){
		if(entityClass == null){
			return null;
		}
		List<Method> methods = getDeclaredMethodList(entityClass);
		List<String> names = new ArrayList<String>(methods.size());
		for(Method method : methods){
			names.add(method.getName());
		}
		return names;
	}
	
	/**
	 * <p><b><em> 获取字段属性列表, 并将字段属性设置为可访问 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;Field&gt; fieldList = Reflection.getDeclaredFieldList(Children.class);
	 * >>> for(Field field : fieldList){
	 * >>>     System.out.println(field);
	 * >>> }
	 * >>> <b><em>output look like :</em></b>
	 * >>> private java.lang.String fan.core.test.model.Children.firstName
	 * >>> private java.lang.String fan.core.test.model.Parents.firstName
	 * >>> protected java.lang.String fan.core.test.model.Parents.lastName
	 * </pre>
	 */
	public static List<Field> getDeclaredFieldList(Class<?> entityClass){
		if(entityClass == null){
			return null;
		}
		List<Field> fields = new ArrayList<Field>();
		while(entityClass != null){
			fields.addAll(getCurrentClassFieldList(entityClass));
			entityClass = entityClass.getSuperclass();
		}
		return fields;
	}
	
	/**
	 * <p><b><em> 获取字段属性名称列表 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> List&lt;String&gt; fieldNameList = Reflection.getDeclaredFieldNameList(Children.class);
	 * >>> for(String fieldName : fieldNameList){
	 * >>>     System.out.println(fieldName);
	 * >>> }
	 * >>> <b><em>output look like :</em></b>
	 * >>> firstName
	 * >>> firstName
	 * >>> lastName
	 * </pre>
	 */
	public static List<String> getDeclaredFieldNameList(Class<?> entityClass){
		if(entityClass == null){
			return null;
		}
		List<Field> fields = getDeclaredFieldList(entityClass);
		List<String> names = new ArrayList<String>(fields.size());
		for(Field field : fields){
			names.add(field.getName());
		}
		return names;
	}
	
	/**
	 * <p><b><em> 获取与参数同名的可访问的字段对象 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> Field field1 = Reflection.getAccessibleField(Children.class, "firstName");
	 * >>> Field field2 = Reflection.getAccessibleField(new Children(), "lastName");
	 * >>> <b><em>output look like :</em></b>
	 * >>> private java.lang.String fan.core.test.model.Children.firstName
	 * >>> protected java.lang.String fan.core.test.model.Parents.lastName
	 * </pre>
	 */
	public static Field getAccessibleField(Object entity, String field){
		if(entity == null || field == null){
			return null;
		}
		Class<?> entityClass = entity instanceof Class ? (Class<?>) entity : entity.getClass();
		while(entityClass != null){
			try {
				Field target = entityClass.getDeclaredField(field);
				target.setAccessible(true);
				return target;
			} catch (Exception e) { /* 尝试获取与参数同名的字段对象, 无需处理抛出的异常 */ }
			/* 当前类查找不到, 回溯到父类继续查找 */
			entityClass = entityClass.getSuperclass();
		}
		return null;
	}
	
	/**
	 * <p><b><em> 获取与参数同名的可访问的方法对象 </b></em></p>
	 * <pre>
	 * >>> <b><em>Definition:</em></b>
	 * >>> Parents(String firstName, String lastName)
	 * >>> Children(String firstName) <b><em>extends</em></b> Parents
	 * >>> <b><em>e.g.</em></b>
	 * >>> Method method1 = Reflection.getAccessibleMethod(Children.class, "getLastName");
	 * >>> Method method2 = Reflection.getAccessibleMethod(Children.class, "setLastName", String.class);
	 * >>> <b><em>output look like :</em></b>
	 * >>> public java.lang.String fan.core.test.model.Parents.getLastName()
	 * >>> public void fan.core.test.model.Parents.setLastName(java.lang.String)
	 * </pre>
	 */
	public static Method getAccessibleMethod(Object entity, String method, Class<?>... type){
		if(entity == null || method == null){
			return null;
		}
		Class<?> entityClass = entity instanceof Class ? (Class<?>) entity : entity.getClass();
		while(entityClass != null){
			try {
				Method target = entityClass.getDeclaredMethod(method, type);
				target.setAccessible(true);
				return target;
			} catch (Exception e) { /* 尝试获取与参数同名的方法对象, 无需处理抛出的异常 */ }
			/* 当前类查找不到, 回溯到父类继续查找 */
			entityClass = entityClass.getSuperclass();
		}
		return null;
	}
	
	/** <p><b><em> 获取实体类的短名称 </b></em></p> */
	private static String getEntityClassName(Object entity){
		return entity == null ? null : entity instanceof Class ? 
		((Class<?>) entity).getSimpleName() : entity.getClass().getSimpleName();
	}
}