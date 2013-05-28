package aladdin.entity;

import org.resgain.bean.BaseObject;
import org.resgain.util.request.annotation.HtmlParameter;
import org.resgain.util.request.annotation.HtmlParameter.LEVEL;

public class Attribute extends BaseObject
{
	private static final long serialVersionUID = 1L;
	
	private String name; //属性名
	@HtmlParameter(level=LEVEL.Free)
	private String content; //方法内容

	public Attribute() {
		super();
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
}