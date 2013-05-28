package com.resgain.lion.entity.base;

import java.util.*;
import javax.persistence.*;
import com.resgain.lion.abst.PersistentObject;
import com.resgain.lion.annotation.*;

/**
 * ${table.name}实体类, 创建于$vt.fdt($!{today})
 * @author gyl
 */
@Entity(name="${table.code}")
@MappedSuperclass
@Desc("${table.name}")
public class ${table:class} extends PersistentObject
{
#foreach($column in $columns)
#if(!$column.pkFlag)
    @Column(name="${column.code}")
    @Label(name="${column.name}", nullFlag=$!{column.nullFlag} ${column:maxLength})
    private $!{column:type} $!{column:prop}; //$!{column.comment}
    
#end
#end

#foreach($column in $columns)
#if($!column.refId)
    @ManyToOne
    @JoinColumn(name="$!{column.code}", insertable=false, updatable=false)
    private $!column.ref:class $!column.ref:prop;
    
#end
#end

#foreach($column in $columns)
#if(!$column.pkFlag)
    public $!{column:type} $!{column:get}(){
        return $!{column:prop};
    }
    public void $!{column:set}($!{column:type} $!{column:prop}){
        this.$!{column:prop} = $!{column:prop};
    }    
    
#end
#end

#foreach($column in $columns)
#if($!column.refId)
    public $!column.ref:class $!{column.ref:get}(){
        return $!{column.ref:prop};
    }
    public void $!{column.ref:set}($!column.ref:class $!{column.ref:prop}){
        this.$!{column.ref:prop} = $!{column.ref:prop};
    }      
#end
#end
}