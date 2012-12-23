package aladdin.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.resgain.plugin.simple.bean.ActionResult;
import org.resgain.plugin.simple.result.DownloadInfo;
import org.resgain.util.FileTool;
import org.resgain.util.RandomGUID;
import org.resgain.util.StringTools;
import org.resgain.util.ViewTools;
import org.resgain.util.request.UploadBean;
import aladdin.entity.Application;
import aladdin.entity.Template;
import aladdin.util.AladdinUtil;
import com.resgain.sparrow.pdm.bean.Table;

public class ProjectAction extends AbstractAction
{
	private String tabIds; //选择的表ID
	private String name; //项目名称
	private UploadBean myFile; //上传的PDM文件
	private Template tpa; //模板
	
	//新建项目
	public String add() throws Exception
	{
		if(myFile==null || !".pdm".equalsIgnoreCase(myFile.getExtName()))
			return ActionResult.msg(false, "文件不对，请上传Powerdesigner设计的PDM文件。");		
		aladdinService.saveApp(name, myFile.getLsInputStream());
		return ActionResult.SUC_MSG;
	}
		
	public Object build() throws Exception
	{
		Application app = aladdinService.getApp(id);
		if(app==null || app.getProject()==null || StringTools.isNullOrSpace(tabIds))
			return ActionResult.msg(false, "错误的项目源或者没选定表！");

		Map<String, Object> map = new HashMap<String, Object>();  
		map.put("project", app.getProject());
		map.put("author", app.getProject().getAuthor());
		map.put("vt", ViewTools.getInstance());
		map.put("aladdin", AladdinUtil.getInstance());
		
		String path = StringTools.str(System.getProperty("user.home"), String.valueOf(File.separatorChar), RandomGUID.getGUID(), String.valueOf(File.separatorChar)).toString();
		FileTool.createDir(path);
		for (String tabId : tabIds.split(",")) {
			if(StringTools.isNullOrSpace(tabId))
				continue;
			Table table = app.getProject().getTable(tabId);
			if(table == null)
				continue;
			map.put("table", table);
			map.put("columns", table.getColumns());
			FileTool.writeFile(path+AladdinUtil.parse(replace(tpa.getFileName()), map), AladdinUtil.parse(replace(tpa.getContent()), map));
		}
        File code = File.createTempFile("code", ".zip");
        code.deleteOnExit();
        FileTool.zipFolder(path, code.getPath());
        FileTool.delete(path);
        return new DownloadInfo("code.zip", "application/x-zip-compressed", code.length(), new FileInputStream(code));
	}
	
	public String del()
	{
		aladdinService.delApp(id);		
		return ActionResult.SUC_MSG;
	}

	public String getTabIds()
	{
		return tabIds;
	}
	public void setTabIds(String tabIds)
	{
		this.tabIds = tabIds;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public UploadBean getMyFile()
	{
		return myFile;
	}
	public void setMyFile(UploadBean myFile)
	{
		this.myFile = myFile;
	}

	public Template getTpa()
	{
		return tpa;
	}
	public void setTpa(Template tpa)
	{
		this.tpa = tpa;
	}
	
	private String replace(String body)
	{
		String reg = "([\\$]{1}[\\!]{0,1}[\\{]{0,1}([\\w\\.]+):([\\w]+)[}]{0,1})";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(body);
		StringBuffer sb = new StringBuffer();
		while(matcher.find())
			matcher.appendReplacement(sb, "\\$!{aladdin.tpa(\""+matcher.group(3)+"\", \\$"+matcher.group(2)+")}");
		matcher.appendTail(sb);
		System.out.println(sb.toString());
		return sb.toString();
	}	
}