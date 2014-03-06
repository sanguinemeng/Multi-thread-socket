/**
 * 
 */
package chat.util;

/**
 * @author lingjiemeng
 *
 */
public class ThreadPool {
	private static ThreadPool thrdPlInst;
	private ThreadPool(){};
	public static ThreadPool getInstance(){
		if(thrdPlInst == null){
			synchronized(ThreadPool.class){
				if(thrdPlInst == null){
					thrdPlInst = new ThreadPool();
				}
			}
		}
		return thrdPlInst;
	}
	/**
	 * @return 
	 * 
	 */
	public void borrowThread() {
		// TODO Auto-generated constructor stub
		return;
	}

}
