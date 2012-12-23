package aladdin.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.resgain.embeddb.Db4oServiceFactory;
import org.resgain.face.IViewUtil;
import aladdin.entity.Application;
import aladdin.entity.Attribute;
import aladdin.entity.Template;
import aladdin.service.AladdinDbConfig;
import aladdin.service.AladdinService;

/**
 * 页面上使用的数据处理方法的工具类
 * @author gyl
 */
public class PageTools  implements IViewUtil 
{
	private static Map<String, Object> utils = new HashMap<String, Object>();
	
	private AladdinService df = Db4oServiceFactory.getInstance(AladdinService.class, AladdinDbConfig.INSTANCE);
	
	static{
		utils.put("pt", new PageTools());
	}
	
	@Override
	public Map<String, Object> getUtils() {
		return utils;
	}	

	public List<Application> getProjectList()
	{
		return df.getAppList();
	}
	
	public Application getProject(String id){
		return df.getApp(id);
	}
	
	public List<Template> getTemplateList(){
		return df.getTpaList();
	}
	
	public Template getTemplate(String id){
		return df.getTpa(id);
	}
	
	public List<Attribute> getAttributeList()
	{
		return df.getAttributeList();
	}
	
	public Attribute getAttribute(String id)
	{
		return df.getAttribute(id);
	}	
	
	public boolean isStr(String type){
		String t = type.toLowerCase();
		return t.equals("clob") || t.equals("text") || t.indexOf("char")>=0;
	}
}