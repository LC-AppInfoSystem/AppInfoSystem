package cn.appsys.tools;

public class Singleton {
	
	private static Singleton singleton;
	private Singleton(){
		//�����������ڼ䣬ֻ��ִ��һ�εĴ��롣
	}
	
	//�����������������ģʽ��ȱ�㣬���ȼ�����
	public static class SingletonHelper{
		private static final Singleton INSTANCE = new Singleton();
	}
	
	public static Singleton getInstance(){
		return singleton = SingletonHelper.INSTANCE;
	}
	
	public static Singleton test(){
		return singleton;
	}
}
