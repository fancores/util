package fan.core.util;
/**
 * <p> ##################################################### </p>
 * <p> @描述：执行时异常, 抛出终止客户端代码执行 </p>
 * <p> @作者：fancy </p>
 * <p> @邮箱：fancores@163.com </p>
 * <p> @日期：2014-05-24 </p>
 * <br> ##################################################### </p>
 */
public class ExecutetimeException extends RuntimeException {

	private static final long serialVersionUID = 6635246863882893402L;

	public ExecutetimeException(String message){
		System.err.println(message);
	}

	public ExecutetimeException(Throwable e){
		e.printStackTrace();
	}
	
	public ExecutetimeException(Throwable e, String message){
		System.err.println(message);
		try {
			Thread.sleep(25);
		} catch (InterruptedException ie) { }
		e.printStackTrace();
	}
}