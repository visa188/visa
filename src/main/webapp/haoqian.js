var GLB = {};
GLB.vari = {};
GLB.vari.submit_flag = true;
// 非空
GLB.nonEmpty = function(node,pnode){
	var tips="不能为空！"
	if(node.tagName.toLowerCase() == "input"){
		if(node.value == ""){
			GLB.setTips(node,pnode,tips);	
			GLB.vari.submit_flag = false;
			return;
		}	
	}
	if(node.tagName.toLowerCase() == "select"){
		var nValue = $("option:selected",node).attr("value");
		if( nValue == ""){
			GLB.setTips(node,pnode,tips);	
			GLB.vari.submit_flag = false;
			return;
		}
	}
}

// 非数字
GLB.notNun = function(node,pnode){
	if(isNaN(node.value)){
		var tips="只能是数字！"
		GLB.setTips(node,pnode,tips);	
		GLB.vari.submit_flag = false;
		return;	
	}
}

//重复输入
GLB.repeat = function(node,pnode){
	var newpwd=$('#newpwd').val();
	if(node.value && node.value!=newpwd){
		var tips="输入不一致！"
		GLB.setTips(node,pnode,tips);	
		GLB.vari.submit_flag = false;
		return;	
	}
}

//手机号码
GLB.phone = function(node,pnode){
	if(node.value){
		var reg = /^1[3|5|8][0-9]\d{8}$/;  
		if (!reg.test(node.value)){ 
			var tips="不是正常的手机号码！";
			GLB.setTips(node,pnode,tips);	
			GLB.vari.submit_flag = false;
			return;	
		}
		var customerId = $('#customerId').val();
		var data;
		if(customerId){
			data = {telephone:node.value,customerId:customerId};
		}else{
			data = {telephone:node.value};
		}
		$.ajax({
			   type: "POST",
			   url: "/customer/hasThisCustomer.do",
			   data:data,
			   async:false,
			   success: function(result){
				   if(result!=0){
					   var tips="此手机客户已存在！";
						GLB.setTips(node,pnode,tips);	
						GLB.vari.submit_flag = false;
						return;	
					}
			   }
			});
	}
}

// 写入提示信息
GLB.setTips = function(node,pnode,tips){
	var erroricoEle = GLB.createErroricoEle();
	var errortx = node.getAttribute("placeholder")+tips;
	var errortipEle = GLB.createErrortipEle(errortx);
	pnode.appendChild(erroricoEle);
	pnode.appendChild(errortipEle);	
}

// 创建错误提示图标
GLB.createErroricoEle = function(){
	var erroricoEle = document.createElement("span");
	erroricoEle.className = "ico_error";
	return erroricoEle;
}

// 创建错误提示节点
GLB.createErrortipEle = function(errortx){
	var errortipEle = document.createElement("span");
	errortipEle.className = "error_tip";
	errortipEle.innerHTML = errortx;
	return errortipEle;
}

// 客人信息操作
GLB.clientOpr = function(node,addBtn,delBtn){
	var oprTb = $("table",node);
	var delBtns = $(".client_delete");
	if(delBtns&&addBtn&&delBtn){
		$(delBtns).each(function(i){
			$(delBtns)[i].onclick = function(){
				delRow($(delBtns)[i]);
			};
		});
		addBtn.onclick = function(){
			addRow();
		}
		delBtn.onclick = function(){
			delRow(delBtn);
		}
	}
	function addRow(){    
		var vTrTemp = createTr();
		$(vTrTemp).appendTo(oprTb);
		$("input",vTrTemp)[0].focus();
	}

	function delRow(delBtn){
		var tbodyTrs = $("tbody tr",oprTb);
		var vNum = tbodyTrs.size();
		if(vNum < 2){
			alert("已是最后一行，不能再删除");
			return;
		}else{
			$(delBtn).parent("td").parent("tr").remove();
		}
	}

	function createTr(){
		var t_tr = document.createElement("tr");
		$(t_tr).html('<td><input type="text" class="tb_text" name="tempName" value="" placeholder="点击此处修改" /><span class="client_delete" title="删除行" onclick="delTempRow(this)">-</span></td>');
		return t_tr;
	}
}

function delTempRow(delBtn){
	var node = $(".js_client_operate");
	var oprTb = $("table",node);
	var tbodyTrs = $("tbody tr",oprTb);
	var vNum = tbodyTrs.size();
	if(vNum < 2){
		alert("已是最后一行，不能再删除");
		return;
	}else{
		$(delBtn).parent("td").parent("tr").remove();
	}
}


$(function() {
	// 设置日期选择
	$(".js_datepicker").datepicker({
		dateFormat: 'yy-mm-dd'
	});

	// 表单填写过程中的验证调用
	$(".controls").each(function(){
		var formItem_wrap = this;
		var nan_flag = false;
		var nonEmpty_flag = false;
		var repeat_flag = false;
		var phone_flag = false;
		if($(".js_phone",formItem_wrap)[0]){
			phone_flag = true;
		}
		if($(".js_repeat",formItem_wrap)[0]){
			repeat_flag = true;
		}
		if($(".js_nan",formItem_wrap)[0]){
			nan_flag = true;
		}
		if($(".js_non_empty",formItem_wrap)[0]){
			nonEmpty_flag = true;
		}
		if(phone_flag || repeat_flag || nan_flag || nonEmpty_flag){
			var formItem = nan_flag ? $(".js_nan",formItem_wrap)[0] : $(".js_non_empty",formItem_wrap)[0];
			if(repeat_flag){
				formItem = repeat_flag ? $(".js_repeat",formItem_wrap)[0] : $(".js_non_empty",formItem_wrap)[0];
			}
			if(phone_flag){
				formItem = phone_flag ? $(".js_phone",formItem_wrap)[0] : $(".js_non_empty",formItem_wrap)[0];
			}
			$(formItem).bind("blur",function(e){
				setVerify();
			}).bind("change",function(e){
				setVerify();
			})
			function setVerify(){
				if($(".ico_error",formItem_wrap)[0]){
		    		$(".ico_error",formItem_wrap).remove();
		    		$(".error_tip",formItem_wrap).remove();
		    	}  
		    	if(nonEmpty_flag){
		    		GLB.nonEmpty(formItem,formItem_wrap);
		    	}
		    	if(nan_flag){
		    		GLB.notNun(formItem,formItem_wrap);
		    	}
		    	if(repeat_flag){
		    		GLB.repeat(formItem,formItem_wrap);
		    	}
		    	if(phone_flag){
		    		GLB.phone(formItem,formItem_wrap);
		    	}
			}
		}	
	})

	// 表单提交时的验证调用
	$(".hq_form").each(function(){
		var thisForm = this;
		var submitBtn = $(".btn_save",thisForm);
		submitBtn.bind("click",function(e){
			setClientValue();
			e.preventDefault();
			GLB.vari.submit_flag = true;
			$(".controls").each(function(){
				var formItem_wrap = this;
				var nan_flag = false;
				var nonEmpty_flag = false;
				var repeat_flag = false;
				var phone_flag = false;
				if($(".js_phone",formItem_wrap)[0]){
					phone_flag = true;
				}
				if($(".js_repeat",formItem_wrap)[0]){
					repeat_flag = true;
				}
				if($(".js_nan",formItem_wrap)[0]){
					nan_flag = true;
				}
				if($(".js_non_empty",formItem_wrap)[0]){
					nonEmpty_flag = true;
				}
				if(phone_flag || repeat_flag || nan_flag || nonEmpty_flag){
					var formItem = nan_flag ? $(".js_nan",formItem_wrap)[0] : $(".js_non_empty",formItem_wrap)[0];
					if(repeat_flag){
						formItem = repeat_flag ? $(".js_repeat",formItem_wrap)[0] : $(".js_non_empty",formItem_wrap)[0];
					}
					if(phone_flag){
						formItem = phone_flag ? $(".js_phone",formItem_wrap)[0] : $(".js_non_empty",formItem_wrap)[0];
					}
					if($(".ico_error",formItem_wrap)[0]){
			    		$(".ico_error",formItem_wrap).remove();
			    		$(".error_tip",formItem_wrap).remove();
			    	}  
					if(nonEmpty_flag){
			    		GLB.nonEmpty(formItem,formItem_wrap);
			    	}
			    	if(nan_flag){
			    		GLB.notNun(formItem,formItem_wrap);
			    	}	
			    	if(repeat_flag){
			    		GLB.repeat(formItem,formItem_wrap);
			    	}
			    	if(phone_flag){
			    		GLB.phone(formItem,formItem_wrap);
			    	}
				}					
			})	
			if (GLB.vari.submit_flag) {
				thisForm.submit();
			};
			
		});
	})

	// 客人信息的添加和删除
	if($(".js_client_operate")[0]){
		var node = $(".js_client_operate");
		var addBtn = $(".client_add",node)[0];
		var delBtn = $(".client_delete",node)[0];
		GLB.clientOpr(node,addBtn,delBtn);
	}
	
	function setClientValue(){
		var names=document.getElementsByName("tempName");
		var result="";
		$(names).each(function(i){
			result+=$(names)[i].value+",";
		});
		if(result&&result.length>0){
			result=result.substring(0, result.length-1);
		}
		$("#nameList").val(result);
	}
});


