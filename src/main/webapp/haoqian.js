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
	if(typeof node != "undefined"){
	if(isNaN(node.value)){
		var tips="只能是数字！"
		GLB.setTips(node,pnode,tips);	
		GLB.vari.submit_flag = false;
		return;	
	}
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
GLB.clientOpr = function(node,addBtn,delBtn,type){
	var oprTb = $("table",node);
	var delBtns = $(".client_delete");
	if(delBtns&&addBtn&&delBtn){
		$(delBtns).each(function(i){
			$(delBtns)[i].onclick = function(){
				delRow($(delBtns)[i]);
			};
		});
		addBtn.onclick = function(){
			if(typeof type == "undefined"){
				addRow();
			}else{
				addRow(1);
			}
		}
		delBtn.onclick = function(){
			delRow(delBtn);
		}
	}
	function addRow(type){
		var vTrTemp;
		if(typeof type == "undefined"){
			vTrTemp = createTr();
		}else{
			vTrTemp = createTr(1);
		}
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

	function createTr(type){
		var t_tr = document.createElement("tr");
		if(typeof type == "undefined"){
			$(t_tr).html('<td>姓名:<input type="text" class="tb_text" name="tempName" value="" placeholder="姓名" style="width:20%;"/>' +
					'联系方式:<input type="text" class="tb_text" name="tempContact" value="" placeholder="联系方式" style="width:30%;"/>' +
					'地址:<input type="text" class="tb_text" name="tempAddr" value="" placeholder="地址" style="width:40%;"/>' +
					'<span class="client_delete" title="删除行" onclick="delTempRow(this)">-</span></td>');
			
		}else{
			$(t_tr).html('<td><input type="hidden" id="tempId" name="tempId" value="" />姓名:<input type="text" class="tb_text" name="tempName" value="" placeholder="客户姓名" style="width:8%;"/>' +
					'&nbsp;性别:<select name="tempSex" placeholder="客人性别"><option value="1">男</option><option value="2">女</option></select>' +
					'&nbsp;类型:<select name="tempAgeType" placeholder="客人类型"><option value="1">成人</option><option value="2">儿童(占床)</option><option value="3">儿童(不占床)</option></select>'+                            
					'&nbsp;押金:<input type="text" class="tb_text" name="tempDeposit" value="" placeholder="押金" style="width:8%;"/>'+
					'&nbsp;资料:<input type="text" class="tb_text" name="tempDatum" value="" placeholder="资料" style="width:10%;"/>'+
					'&nbsp;房间数:<input type="text" class="tb_text" name="tempRoom" value="" placeholder="房间数" style="width:8%;"/>'+
					'&nbsp;备注:<input type="text" class="tb_text" name="tempComment" value="" placeholder="备注" style="width:5%;"/>'+
					'&nbsp;<span class="client_delete" style="margin-left:5%" title="删除行" onclick="delTempRow(this,'+type+')">-</span></td>');
		}
		return t_tr;
	}
}

function delTempRow(delBtn,type){
	var node;
	if(typeof type == "undefined"){
		node = $(".js_client_operate");
	}else{
		node = $(".js_line_client_operate");
	}
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
		var len = $(":input",formItem_wrap).length;
		for(var i = 0; i < len; i++){
			if($(".js_phone",formItem_wrap)[i]){
				phone_flag = true;
			}
			if($(".js_repeat",formItem_wrap)[i]){
				repeat_flag = true;
			}
			if($(".js_nan",formItem_wrap)[i]){
				nan_flag = true;
			}
			if($(".js_non_empty",formItem_wrap)[i]){
				nonEmpty_flag = true;
			}
			if(phone_flag || repeat_flag || nan_flag || nonEmpty_flag){
				var formItem = nan_flag ? $(".js_nan",formItem_wrap)[i] : $(".js_non_empty",formItem_wrap)[i];
				if(repeat_flag){
					formItem = repeat_flag ? $(".js_repeat",formItem_wrap)[i] : $(".js_non_empty",formItem_wrap)[i];
				}
				if(phone_flag){
					formItem = phone_flag ? $(".js_phone",formItem_wrap)[i] : $(".js_non_empty",formItem_wrap)[i];
				}
				$(formItem).bind("blur",function(e){
					setVerify(this,i);
				}).bind("change",function(e){
					setVerify(this,i);
				})
				function setVerify(formItem,i){
					var placeholder = formItem.getAttribute("placeholder");
					$(".error_tip",formItem_wrap).each(function(){
						var tip = $(this).text();
						if(tip.indexOf(placeholder)==0){
							$(this).prev().remove();
							$(this).remove();
						}
						
					});  
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
		}
	})

	// 表单提交时的验证调用
	$(".hq_form").each(function(){
		var thisForm = this;
		var submitBtn = $(".btn_save",thisForm);
		submitBtn.bind("click",function(e){
			if('tj'==jQuery(this).attr('name')){
                jQuery('#status').val('8');                                
            }
			$(this).attr("disabled",true);
			GLB.vari.submit_flag = true;
			if($(thisForm).attr('class').indexOf("line") >=0){
				setClientValue(1);		
			}else{
				setClientValue();
			}
			e.preventDefault();
			$(".controls").each(function(){
				var formItem_wrap = this;
				var nan_flag = false;
				var nonEmpty_flag = false;
				var repeat_flag = false;
				var phone_flag = false;
				
				var len = $(":input",formItem_wrap).length;
				for(var i = 0; i < len; i++){
					if($(".js_phone",formItem_wrap)[i]){
						phone_flag = true;
					}
					if($(".js_repeat",formItem_wrap)[i]){
						repeat_flag = true;
					}
					if($(".js_nan",formItem_wrap)[i]){
						nan_flag = true;
					}
					$(".js_non_empty",formItem_wrap).each(function(){
						GLB.nonEmpty(this,formItem_wrap);
					})
					
					if(phone_flag || repeat_flag || nan_flag){
						var formItem = nan_flag ? $(".js_nan",formItem_wrap)[i] : $(".js_non_empty",formItem_wrap)[i];
						if(repeat_flag){
							formItem = repeat_flag ? $(".js_repeat",formItem_wrap)[i] : $(".js_non_empty",formItem_wrap)[i];
						}
						if(phone_flag){
							formItem = phone_flag ? $(".js_phone",formItem_wrap)[i] : $(".js_non_empty",formItem_wrap)[i];
						}
						var placeholder = formItem.getAttribute("placeholder");
						$(".error_tip",formItem_wrap).each(function(){
							var tip = $(this).text();
							if(tip.indexOf(placeholder)==0){
								$(this).prev().remove();
								$(this).remove();
							}
							
						}); 
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
			if (GLB.vari.submit_flag) {
				if(confirm("确认提交?")){
					thisForm.submit();	
				}
				$(this).attr("disabled",false);
			}else{
				$(this).attr("disabled",false);
			}
		});
	})

	// 客人信息的添加和删除
	if($(".js_client_operate")[0]){
		var node = $(".js_client_operate");
		var addBtn = $(".client_add",node)[0];
		var delBtn = $(".client_delete",node)[0];
		GLB.clientOpr(node,addBtn,delBtn);
	}
	
	// 线路客人信息的添加和删除
	if($(".js_line_client_operate")[0]){
		var node = $(".js_line_client_operate");
		var addBtn = $(".client_add",node)[0];
		var delBtn = $(".client_delete",node)[0];
		GLB.clientOpr(node,addBtn,delBtn,1);
	}
	
	function setClientValue(type){
		if(typeof type == "undefined"){
			var continentId = $("#continentId")[0];
			var checkNotEmpty = false;
			if (continentId && continentId.value!=4 && continentId.value!=5){
				checkNotEmpty = true;
			}
			
			var names=document.getElementsByName("tempName");
			var contacts=document.getElementsByName("tempContact");
			var addrs=document.getElementsByName("tempAddr");
			var result="";
			
			if(checkNotEmpty){
				$(names).each(function(){
					$(this).addClass("js_non_empty");
				});
				$(contacts).each(function(){
					$(this).addClass("js_non_empty");
				});
				$(addrs).each(function(){
					$(this).addClass("js_non_empty");
				});
			}else{
				$(names).each(function(){
					$(this).removeClass("js_non_empty");
				});
				$(contacts).each(function(){
					$(this).removeClass("js_non_empty");
				});
				$(addrs).each(function(){
					$(this).removeClass("js_non_empty");
				});
			}
			
			$(names).each(function(i){
				result+=$(names)[i].value+"_"+$(contacts)[i].value+"_"+$(addrs)[i].value+",";
			});
			if(result&&result.length>0){
				result=result.substring(0, result.length-1);
			}
		}else{
			var nameListSize = $("#nameListSize")[0];
			var checkNotEmpty = false;
			if (nameListSize.value>0){
				checkNotEmpty = true;
			}
			
			var ids=document.getElementsByName("tempId");
			var names=document.getElementsByName("tempName");
			var sexs=document.getElementsByName("tempSex");
			var agetypes=document.getElementsByName("tempAgeType");
			var deposits=document.getElementsByName("tempDeposit");
			var datums=document.getElementsByName("tempDatum");
			var rooms=document.getElementsByName("tempRoom");
			var comments=document.getElementsByName("tempComment");
			var result="";
			
			if(checkNotEmpty){
				$(names).each(function(){
					$(this).addClass("js_non_empty");
				});
				$(agetypes).each(function(){
					$(this).addClass("js_non_empty");
				});
				$(deposits).each(function(){
					$(this).addClass("js_non_empty");
				});
				$(datums).each(function(){
					$(this).addClass("js_non_empty");
				});
				$(rooms).each(function(){
					$(this).addClass("js_non_empty");
				});
				$(comments).each(function(){
					$(this).addClass("js_non_empty");
				});
			}else{
				$(names).each(function(){
					$(this).removeClass("js_non_empty");
				});
				$(agetypes).each(function(){
					$(this).removeClass("js_non_empty");
				});
				$(deposits).each(function(){
					$(this).removeClass("js_non_empty");
				});
				$(datums).each(function(){
					$(this).removeClass("js_non_empty");
				});
				$(rooms).each(function(){
					$(this).removeClass("js_non_empty");
				});
				$(comments).each(function(){
					$(this).removeClass("js_non_empty");
				});
			}
			
			$(names).each(function(i){
				result+=($(names)[i].value==''?'#':$(names)[i].value)+
				"_"+($(sexs)[i].value==''?'#':$(sexs)[i].value)+
				"_"+($(agetypes)[i].value==''?'#':$(agetypes)[i].value)+
				"_"+($(deposits)[i].value==''?'#':$(deposits)[i].value)+
				"_"+($(datums)[i].value==''?'#':$(datums)[i].value)+
				"_"+($(rooms)[i].value==''?'#':$(rooms)[i].value)+
				"_"+($(comments)[i].value==''?'#':$(comments)[i].value)+
				"_"+($(ids)[i].value==''?'#':$(ids)[i].value)+",";
			});
			if(result&&result.length>0){
				result=result.substring(0, result.length-1);
			}
			// line service
			var ldserv ='0';
			if($('#ldserv').is(':checked')==true){
				var ldservId = $('#ldservId').val();
				ldserv = '1_' + (ldservId==''?'#':ldservId);
				ldserv += '_' + $('#ldtype').val();
				var ldpai = $('#ldpai').val();
				if(typeof ldpai == 'undefined' || ldpai == ''){
					ldpai = '#';
				}
				ldserv += '_'+ldpai;
			}
			$('#ld').val(ldserv);
			
			var qzserv ='0';
			if($('#qzserv').is(':checked')==true){
				var qzservId = $('#qzservId').val();
				qzserv = '1_' + (qzservId==''?'#':qzservId);
				qzserv += '_' + $('#qztype').val();
				var qzysdj = $('#qzysdj').val();
				var qzyshj = $('#qzyshj').val();
				qzserv += '_' + (qzysdj==''?'#':qzysdj);
				qzserv += '_' + (qzyshj==''?'#':qzyshj);
				var qzpai = $('#qzpai').val();
				if(typeof qzpai == 'undefined' || qzpai == ''){
					qzpai = '#';
				}
				qzserv += '_'+qzpai;
			}
			$('#qz').val(qzserv);
			
			var jpserv ='0';
			if($('#jpserv').is(':checked')==true){
				var jpservId = $('#jpservId').val();
				jpserv = '1_' + (jpservId==''?'#':jpservId);
				jpserv += '_' + $('#jptype').val();
				var jpysdj = $('#jpysdj').val();
				var jpyshj = $('#jpyshj').val();
				jpserv += '_' + (jpysdj==''?'#':jpysdj);
				jpserv += '_' + (jpyshj==''?'#':jpyshj);
				var jppai = $('#jppai').val();
				if(typeof jppai == 'undefined' || jppai == ''){
					jppai = '#';
				}
				jpserv += '_'+jppai;
			}
			$('#jp').val(jpserv);
			
			var djserv ='0';
			if($('#djserv').is(':checked')==true){
				var djxing = $('#djxing').val();
				var djfx = $('#djfx').val();
				var djbz = $('#djbz').val();
				var djtsjd = $('#djtsjd').val();
				var djservId = $('#djservId').val();
				djserv = '1_' + (djservId==''?'#':djservId);
				djserv += '_' + (djxing==''?'#':djxing);
				djserv += '_' + (djfx==''?'#':djfx);
				djserv += '_' + (djbz==''?'#':djbz);
				djserv += '_' + (djtsjd==''?'#':djtsjd);
				
				if($('#djdyra').is(':checked')==true){
					var djbs = $('#djbs').val();
					var djbsbz = $('#djbsbz').val();
					var djsfxydy = $('#djsfxydy').val();
					var djsfxydybz = $('#djsfxydybz').val();
					
					djserv += '_a_'+ (djbs==''?'#':djbs);
					djserv += '_' + (djbsbz==''?'#':djbsbz);
					djserv += '_' + (djsfxydy==''?'#':djsfxydy);
					djserv += '_' + (djsfxydybz==''?'#':djsfxydybz);
				}else{
					var djsjjdybz = $('#djsjjdybz').val();
					djserv += '_b_'+ (djsjjdybz==''?'#':djsjjdybz);
					djserv += '_#_#_#';
				}
				var djysdj = $('#djysdj').val();
				var djyshj = $('#djyshj').val();
				djserv += '_' + (djysdj==''?'#':djysdj);
				djserv += '_' + (djyshj==''?'#':djyshj);
				var djpai = $('#djpai').val();
				if(typeof djpai == 'undefined' || djpai == ''){
					djpai = '#';
				}
				djserv += '_'+djpai;
			}
			$('#dj').val(djserv);
			
			var qtserv ='0';
			if($('#qtserv').is(':checked')==true){
				var qtservId = $('#qtservId').val();
				qtserv = '1_' + (qtservId==''?'#':qtservId);
				qtserv += '_' + $('#qtbz').val();
				var qtysdj = $('#qtysdj').val();
				var qtyshj = $('#qtyshj').val();
				qtserv += '_' + (qtysdj==''?'#':qtysdj);
				qtserv += '_' + (qtyshj==''?'#':qtyshj);
			}
			$('#qt').val(qtserv);
			
		}
		$("#nameList").val(result);
	}
});


