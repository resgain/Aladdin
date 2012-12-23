package aladdin.service;

import java.io.File;

import org.resgain.embeddb.AbstractDbConfig;
import org.resgain.util.StringTools;

import com.db4o.config.CommonConfiguration;

final public class AladdinDbConfig extends AbstractDbConfig
{
	public static AladdinDbConfig INSTANCE = new AladdinDbConfig();
	
	private AladdinDbConfig(){}

	@Override
	public String getDbFile()
	{
		return StringTools.str(System.getProperty("user.home"), String.valueOf(File.separatorChar), "aladdin.dat");
	}
	
	@Override
	public void init(CommonConfiguration config)
	{
		config.exceptionsOnNotStorable(true);        
		config.updateDepth(10);
		config.activationDepth(10);
	}	
}