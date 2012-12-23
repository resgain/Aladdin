package ${pack}.$!{subpack};

#if($table.existsDate)
import java.util.*;
#end
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import com.withub.cheetah.abst.AbstractObject;
#if($table.existsManyToOne)
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
#end

/**
 * ${table.name}实体类, 创建于$vt.fd($!{today})
 * @author ${author}
 */
@Entity(name="${table.code}")
@MappedSuperclass
public class ${table.className} extends AbstractObject
{
    private static final long serialVersionUID = 1L;
#foreach($column in $table.columns)

#if(!$column.pkFlag)
	@Column(name="$!{column.code}"#if($!{column.jtype}=="String"), length=$!{column.len} #end)
    private $!{column.jtype} $!{column.prop}; //$!{column.name}
#end
#end
#foreach($column in $table.columns)
#if($!column.refId)
	@ManyToOne @JoinColumn(name="$!{column.code}", insertable=false, updatable=false)
    private $!column.ref.className $!column.ref.classProp;
#end
#end

	public ${table.className}(){}
#foreach($column in $table.columns)

#if(!$column.pkFlag)
    public $!{column.jtype} get$!stringtool.capitalize($!{column.prop}, false)()
    {
        return $!{column.prop};
    }
    public void set$!stringtool.capitalize($!{column.prop}, false)($!{column.jtype} $!{column.prop})
    {
        this.$!{column.prop}=$!{column.prop};
    }
#end
#if($!column.refId)
    public $!column.ref.className get$!{column.ref.className}()
    {
        return this.$!column.ref.classProp;
    }
    public void set$!{column.ref.className}($!column.ref.className $!column.ref.classProp)
    {
        this.$!column.ref.classProp = $!column.ref.classProp;
    }
#end
#end
}