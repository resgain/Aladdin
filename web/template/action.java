package ${pack}.$!{subpack};

import java.util.*;
import com.withub.cheetah.abst.AbstractAction;

/**
 * ${table.name}Action类, 创建于$vt.fd($!{today})
 * @author ${author}
 */
public class ${table.className}Action extends AbstractAction
{
	public String list() throws Exception
	{
		return RSTPL;
	}
	
	public String modi() throws Exception
	{
		return json();
	}
	
	public String del() throws Exception
	{
		return json();
	}

}