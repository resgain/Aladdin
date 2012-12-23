package aladdin.entity;

import java.util.Date;
import org.resgain.bean.BaseObject;

import com.resgain.sparrow.pdm.PDMParser;
import com.resgain.sparrow.pdm.bean.Project;

public class Application extends BaseObject
{
	private static final long serialVersionUID = 1L;
	
    private String name; //项目名称    
    private Date cdate; //创建日期
    private String source; //PDM文件内容
    private Project project; //分解好的项目
    
	public Application() {
		super();
	}

	public Application(String name, String source) {
		super();
		this.name = name;
		this.source = source;
		this.cdate = new Date();
		this.project = PDMParser.parse(source);
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public Date getCdate()
	{
		return cdate;
	}
	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject(Project project)
	{
		this.project = project;
	}
}
