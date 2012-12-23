
function alert_(msg, ac){
	$("#dialog_info").find(".modal-body").html(msg);
	if(ac)
		$("#dialog_info").on('hidden', function () {
			ac();
		}).modal('show');
	else
		$("#dialog_info").modal('show');
}

function dialog(jform, msg){
	var dm = $("#_dialog_msg_");
	if(dm.length>0)
		dm.remove();
	var as = '<div class="alert alert-error" id="_dialog_msg_"><button type="button" class="close" data-dismiss="alert">×</button>'+msg+'</div>';
	jform.before(as);
	setTimeout(function(){$("#_dialog_msg_").alert('close');}, 4200);
}

String.prototype.trim=function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.isValidDate=function()
{
    var result=this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if(result==null) return false;
    var d=new Date(result[1], result[3]-1, result[4]);
    return (d.getFullYear()==result[1]&&d.getMonth()+1==result[3]&&d.getDate()==result[4]);
}

String.prototype.isValidTime=function()
{
    var resule=this.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
    if (result==null) return false;
    if (result[1]>24 || result[3]>60 || result[4]>60) return false;
    return true;
}


String.prototype.isValidDatetime=function()
{
    var result=this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})$/);
    if(result==null) return false;
    var d= new Date(result[1], result[3]-1, result[4], result[5], result[6], 0);
    return (d.getFullYear()==result[1]&&(d.getMonth()+1)==result[3]&&d.getDate()==result[4]&&d.getHours()==result[5]&&d.getMinutes()==result[6]);
}

$.fieldValue = function(el, successful) {
    var n = el.name, t = el.type, tag = el.tagName.toLowerCase();
    if (successful === undefined) {
        successful = true;
    }

    if (successful && (!n || el.disabled || t == 'reset' || t == 'button' ||
        (t == 'checkbox' || t == 'radio') && !el.checked ||
        (t == 'submit' || t == 'image') && el.form && el.form.clk != el ||
        tag == 'select' && el.selectedIndex == -1)) {
            return null;
    }

    if (tag == 'select') {
        var index = el.selectedIndex;
        if (index < 0) {
            return null;
        }
        var a = [], ops = el.options;
        var one = (t == 'select-one');
        var max = (one ? index+1 : ops.length);
        for(var i=(one ? index : 0); i < max; i++) {
            var op = ops[i];
            if (op.selected) {
                var v = op.value;
                if (!v) { // extra pain for IE...
                    v = (op.attributes && op.attributes['value'] && !(op.attributes['value'].specified)) ? op.text : op.value;
                }
                if (one) {
                    return v;
                }
                a.push(v);
            }
        }
        return a;
    }
    return $(el).val();
};

function getFormData(form) {
	var ret = new FormData();
    var els = form.elements;
    if (!els) {
        return ret;
    }

    var i,j,n,v,el,max,jmax;
    for(i=0, max=els.length; i < max; i++) {
        el = els[i];
        n = el.name;
        if (!n) {
            continue;
        }
        v = $.fieldValue(el, true);
        if (v && v.constructor == Array) {
            for(j=0, jmax=v.length; j < jmax; j++) {
            	ret.append(n, v[j]);
            }
        }
        else if (el.type == 'file' && !el.disabled) {
            var files = el.files;
            if (files.length) {
                for (j=0; j < files.length; j++) {
                	ret.append(n, files[j]);
                }
            }
        }
        else if (v !== null && typeof v != 'undefined') {
        	ret.append(n, v);
        }
    }
    return ret;
}

function formValidation(jform)
{
	var isValidation = function(obj){
		piValue=obj.value.replace(/(^\s*)|(\s*$)/g, "");
		switch (obj.getAttribute("dataType")) {
			case "Chinese" : return /^[\u0391-\uFFE5]+$/.test(piValue);
			case "Email" : return piValue.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)!=null;
			case "Integer" : return piValue.match(/^(-|\+)?\d+$/)!=null;
			case "Double" : return /(^[-\+]?)(\d*)(\.?)(\d*)?$/.test(piValue);
			case "Date" : return piValue.isValidDate();
			case "Time" : return piValue.isValidTime();
			case "Datetime" : return piValue.isValidDatetime();
			case "English" : return piValue.match(/^[a-zA-Z]+$/)!=null;
			case "Repeat" : return piValue == document.getElementsByName(obj.getAttribute('to'))[0].value;
			default : return piValue!="";
		}
		return true;
	}
	
	var formObject = jform[0];
	for ( var i = 0; i < formObject.length; i++) {
		var jo = $(formObject.elements[i]);
		with (formObject.elements[i]) {
			if(nodeName=="FIELDSET") continue;
			if(getAttribute("require") != "true" && value.trim() == "") continue;
			if(!isValidation(formObject.elements[i]))
			{
				var msg = getAttribute("msg") || getAttribute("placeholder") || "请输入数据";
				dialog(jform, msg);
				$(formObject.elements[i]).focus();
				return false;
			}
		}
	}	
	return true;
};

function getAceEdit(editorId, mode)
{
    var editor = ace.edit(editorId);
    editor.setTheme("ace/theme/twilight");
    editor.getSession().setMode("ace/mode/"+mode);
    document.getElementById('editor').style.fontSize='18px';
    editor.getSession().setTabSize(4);
    editor.setHighlightActiveLine(false);
    editor.setShowPrintMargin(false);
    editor.commands.addCommand({
//        name: 'myCommand',
//        bindKey: {win: 'Ctrl-/',  mac: 'Command-M'},
//        exec: function(editor) {
//        	var c = editor.selection.getCursor();;
//        	var n = editor.session.getLine(c.row);
//        	alert(n);
//        }
    }); 
    
    return editor;
}

$(document).ready(function() {

	if($("#dialog_info").length<1){
		$("body").append('<div id="dialog_info" class="modal hide fade"><div class="modal-body"></div><div class="modal-footer"><a href="#" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a></div></div>');
	}	
	
    $(".selectall").click(function() {
        var chd = this.checked;
        $("."+$(this).attr("sub")).each(function() {
            $(this).attr("checked", chd);
        });
    });	
	
	$("a.ajax_refresh").each(function(o){
		$(this).click(function(){
				$.post($(this).attr("href"), null, function(json){
					location.reload();
				}, 'json');
			return false;
		})
	});
	
	$("a.ajax").live('click', function(o){
		var flag=true;
		var go=$(this).attr("go");
		if($(this).attr("confirm"))
			flag = confirm($(this).attr("confirm"));
		if(flag)
			$.post($(this).attr("href"), null, function(json){
				if (json.success) {
					if(go)
						location.href=go;
					else
						location.reload();
                } else {
                    alert_(json.message);
                }
            }, 'json');
        return false;
	});

	$(".ajaxform").submit(function(e){
		var jform = $(this);
		if(!formValidation(jform))
			return false;
	    var hasFileInputs = $('input:file:enabled[value]', jform).length > 0;
	    if(hasFileInputs)
	    {	        
	        $.ajax({
	        	  url: jform.attr("action"),
	        	  type: "POST",
	        	  data: getFormData(jform[0]),
	        	  processData: false, 
	        	  contentType: false,
	        	  dataType : "json",
					error : function(request) { // 设置表单提交出错
						dialog(jform, "表单提交出错，请稍候再试.....");
					},
					success : function(data) {
						if (data.success) {
							if (jform.attr("tu"))
								window.location.href = jform.attr("tu");
							else if (jform.attr("ac"))
								eval(jform.attr("ac"))(data);
							else {
								$(".modal").modal('hide');
								if (jform.attr("atu"))
									alert_(data.message, function(){window.location.href = jform.attr("atu");});
								else
									alert_(data.message);
							}
						} else {
							dialog(jform, data.message);
						}
					}	        	  
	        	});
	    } else {
			$.ajax({
				type : "post",
				url : jform.attr("action"),
				data : jform.serialize(), // 序列化
				dataType : "json",
				error : function(request) { // 设置表单提交出错
					dialog(jform, "表单提交出错，请稍候再试.....");
				},
				success : function(data) {
					if (data.success) {
						if (jform.attr("tu"))
							window.location.href = jform.attr("tu");
						else if (jform.attr("ac"))
							eval(jform.attr("ac"))(data);
						else {
							$(".modal").modal('hide');
							if (jform.attr("atu"))
								alert_(data.message, function(){window.location.href = jform.attr("atu");});
							else
								alert_(data.message);
						}
					} else {
						dialog(jform, data.message);
					}
				}
			});
	    }
		return false;
	});
});