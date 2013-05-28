package com.resgain.lion.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.resgain.base.abst.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ${table.name}Action方法类, 创建于$vt.fdt($!{today})
 * @author ${author}
 */
public class ${table:class}Action extends AbstractAction
{
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(${table:class}Action.class);
    
    @Autowired private ${table:class}Service ${table:prop}Service;
    
    private String id;
    private ${table:class} ${table:prop};
    
    //${table.name}列表
    public String list() throws Exception
	{
        logger.debug("获取${table.name}列表数据");
		return json();
	}
	
    //${table.name}查看
	public String view() throws Exception
	{
        logger.debug("获取${table.name}指定ID：{}的详细数据", id);
		return json();
	}
    
    //${table.name}保存
    public String save() throws Exception
	{
        logger.debug("保存${table.name}数据:");
		return json();
	}    
	
    //${table.name}删除方法
	public String del() throws Exception
	{
        logger.warn("删除${table.name}数据, ID:{}", id);
		return json();
	}
}