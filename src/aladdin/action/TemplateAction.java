package aladdin.action;

import org.resgain.plugin.simple.bean.ActionResult;
import org.resgain.util.StringTools;
import aladdin.entity.Template;

public class TemplateAction extends AbstractAction
{
	private Template tpa;
	
	public String save()
	{
		if(tpa==null || StringTools.isNullOrSpace(tpa.getName(), tpa.getType(), tpa.getFileName(), tpa.getContent()))
			return ActionResult.msg(false, "模板定义信息不能为空！");
		aladdinService.saveTpa(tpa);
		return ActionResult.SUC_MSG;
	}
	
	public String del()
	{
		aladdinService.delTpa(id);
		return ActionResult.SUC_MSG;
	}

	public Template getTpa()
	{
		return tpa;
	}
	public void setTpa(Template tpa)
	{
		this.tpa = tpa;
	}
}