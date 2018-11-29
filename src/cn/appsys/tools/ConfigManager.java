package cn.appsys.tools;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties properties ;
	
	//˽�в��Ҿ�̬�Ķ���
	//����ģʽ��������Ŀ��������ʵ��������û���̰߳�ȫ���⡣ȱ�㣬���ʺ���Դ�ϴ����
	private static ConfigManager configManager;
	
	
	//˽�й�����
	private ConfigManager(){
		String configFile = "database.properties";
		properties = new Properties();
		InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(configFile);
		try {
			properties.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ȫ�ַ��ʵ�   
	//����ģʽ������������synchronized�����Ǵ���̫�ߣ�ÿ�λ�ȡ��Ҫͬ��
	public static  ConfigManager getInstance(){
		if(configManager == null){
			configManager = new ConfigManager();
		}
		return configManager;
	}
	
	public String getValue(String key){
		return properties.getProperty(key);
	}
}
