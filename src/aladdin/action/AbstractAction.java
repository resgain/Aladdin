package aladdin.action;

import org.resgain.embeddb.Db4oServiceFactory;
import aladdin.service.AladdinDbConfig;
import aladdin.service.AladdinService;

public class AbstractAction
{
	protected String id;

	protected AladdinService aladdinService = Db4oServiceFactory.getInstance(AladdinService.class, AladdinDbConfig.INSTANCE);
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
}