Ext.ns("${table.classProp}");
Ext.onReady(function(){
    var store = Withub.store('${table.classProp}!list.do', [#foreach($column in $table.columns)'$!{column.prop}'#if($velocityCount<$table.columns.size()),#end#end], true);
    var win, win1;
    var sm = new Ext.grid.CheckboxSelectionModel();
    var grid = new Ext.grid.EditorGridPanel({
                store      : store,
                columns    : [
	                	new Ext.grid.RowNumberer(), 
	                	sm, 
	                	#foreach($column in $table.columns)
	                	{header : '$!{column.name}', dataIndex : '$!{column.prop}', sortable  : true}#if($velocityCount<$table.columns.size()),#end
	                	#end
	                ],
                viewConfig : {forceFit : true},
                sm         : sm,
                tbar       : [
                		{
                            text    : '添加',
                            handler : function(){
			                   
	
                        	}
                        }, {
                            text    : '批量删除',
                            handler : function(){

                            }
                        }],
                bbar       : Withub.pagingToolbar(store)
            });

    var viewport = new Ext.Viewport({
        layout   : 'border',
        defaults : {layout : 'fit'},
        items    : [{
                    region : 'center',
                    items  : grid
                }]
    });
    store.load();
});