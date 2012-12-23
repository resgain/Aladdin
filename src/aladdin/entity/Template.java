package aladdin.entity;

import java.util.Date;

import org.resgain.bean.BaseObject;

public class Template extends BaseObject
{
	private static final long serialVersionUID = 1L;
	
	private String type; //java/html/js......
	private String name;
	private String fileName; //生产的文件名
	private String content;
	private Date cdate;

	public Template() {
		super();

	}

	public Template(String name, String type, String content, String fileName) {
		this.name = name;
		this.type = type;
		this.content = content;		
		this.fileName = fileName;
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
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCdate()
	{
		return cdate;
	}
	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}
}