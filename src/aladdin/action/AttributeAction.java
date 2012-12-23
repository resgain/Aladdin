package aladdin.action;

import org.resgain.plugin.simple.bean.ActionResult;
import org.resgain.util.StringTools;
import aladdin.entity.Attribute;

public class AttributeAction extends AbstractAction
{
	private Attribute attr;

	public String save() throws Exception
	{
		if(attr==null || StringTools.isNullOrSpace(attr.getName(), attr.getContent()))
			return ActionResult.msg(false, "属性定义信息不能为空！");
		aladdinService.saveAttribute(attr);
		return ActionResult.SUC_MSG;
	}
	
	public String del()
	{
		aladdinService.delAttribute(id);
		return ActionResult.SUC_MSG;
	}

	public Attribute getAttr()
	{
		return attr;
	}
	public void setAttr(Attribute attr)
	{
		this.attr = attr;
	}
}