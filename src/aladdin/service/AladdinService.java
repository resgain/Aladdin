package aladdin.service;

import java.io.InputStream;
import java.util.List;
import org.resgain.embeddb.Db4oService;
import org.resgain.exception.KnowException;
import org.resgain.util.FileTool;
import org.resgain.util.StringTools;
import aladdin.entity.Application;
import aladdin.entity.Attribute;
import aladdin.entity.Template;
import aladdin.util.AladdinUtil;

public class AladdinService extends Db4oService
{

	public List<Application> getAppList()
	{
		return getList(Application.class);
	}
	
	public Application getApp(String code)
	{
		return getObject(Application.class, code);
	}
	
	public void saveApp(String name, InputStream is)
	{
		String content = FileTool.readFile(is, "UTF-8");
		if(StringTools.isNullOrSpace(content))
			throw new KnowException("这不是一个正确的PDM文件");
		Application app = new Application(name, content);
		save(app);
	}

	public void delApp(String id)
	{
		delete(Application.class, id);
	}

	public List<Template> getTpaList()
	{
		return getList(Template.class);
	}
	
	public Template getTpa(String tpaId)
	{
		return getObject(Template.class, tpaId);
	}

	public String saveTpa(Template tpa)
	{
		if(!StringTools.isNullOrSpace(tpa.getId()))
		{
			Template tc = getTpa(tpa.getId());
			tc.setContent(tpa.getContent());
			tc.setType(tpa.getType());
			tc.setName(tpa.getName());
			tc.setFileName(tpa.getFileName());
			save(tc);
		} else {
			tpa.setCdate(getNowDate());
			save(tpa);
		}
		return tpa.getId();
	}
	
	public void  delTpa(String id)
	{
		delete(Template.class, id);
	}
	
	public List<Attribute> getAttributeList()
	{
		return getList(Attribute.class);
	}
	
	public Attribute getAttribute(String id)
	{
		return getObject(Attribute.class, id);
	}
	
	public void saveAttribute(Attribute att) throws Exception
	{
		AladdinUtil.putScript(att.getName(), att.getContent());
		save(att);
	}
	
	public void delAttribute(String id)
	{
		AladdinUtil.removeScript(getAttribute(id).getName());
		delete(Attribute.class, id);
	}
}