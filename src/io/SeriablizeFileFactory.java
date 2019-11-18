package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import model.Config;

public class SeriablizeFileFactory{
	public static boolean luuFile(Config config,URL path)
	{
		try
		{
			File file = new File(path.getPath());
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(config);
			fos.close();
			oos.close();
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	public static Config docFile(URL path)
	{
		try
		{
			Config config = new Config();
			File file = new File(path.getPath());
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object data = ois.readObject();
			config = (Config) data;
			ois.close();
			fis.close();
			return config;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
}
