package ${pack}.$!{subpack};

import java.util.*;
import org.resgain.bean.QueryPage;
import org.resgain.bean.ResultPage;
import org.springframework.stereotype.Service;
import ${pack}.${table.className};
import com.withub.cheetah.abst.AbstractService;

/**
 * ${table.name}业务方法类, 创建于$vt.fd($!{today})
 * @author ${author}
 */
@Service
public abstract class ${table.className}Service extends AbstractService
{
    /**
     * 取得${table.name}的分页列表信息
     * @param qp
     * @return
     */
    public ResultPage<${table.className}> get${table.className}List(QueryPage qp) //测试因有事务所以临时改名
    {
    	return find(${table.className}.class, qp, null, null);
    }
    
    /**
     * 保存${table.name}信息
     * @param user
     * @return
     */
    public String save${table.className}(${table.className} po)
    {
        return save(po);
    }
    
    /**
     * 取得${table.name}信息
     * @param userId
     * @return
     */
    public ${table.className} get${table.className}(String id)
    {
        return get(${table.className}.class, id);
    }
    
    /**
     * 删除${table.name}信息
     * @param userId
     */
    public void del${table.className}(String... ids)
    {
        delete(${table.className}.class, ids);
    }  
}