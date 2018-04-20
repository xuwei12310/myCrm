var todoAttach = {
	//定义上传插件
	uploaderTodo:null,	
	init: function () {
		//new 附件上传的uploaderTodo
		todoAttach.uploaderTodo = new plupload.Uploader({
            runtimes : 'html5,flash,silverlight',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html
			flash_swf_url : ctx+'/resources/comp/plupload-2.1.9/js/Moxie.swf',
			silverlight_xap_url : ctx+'/resources/comp/plupload-2.1.9/js/Moxie.xap',
	        url : ctx + "/admin/myWorkbench/todo/uploadFile.jhtml",//上传文件路径
            max_file_size : '3gb',//100b, 10kb, 10mb, 1gb
            chunk_size : '200mb',//分块大小，小于这个大小的不分块
            unique_names : true,//生成唯一文件名
            browse_button : 'uploaderTodo',
            filters : [ {
                title : 'Image files',
                extensions : 'jpg,jpeg,gif,png'
            }, {
                title : 'Zip files',
                extensions : 'zip,7z,rar'
            } , {
                title : 'Office files',
                extensions : 'doc,docx,xls,xlsx,pptx,ppt,txt'
            }, {
                title : 'Video files',
                extensions : 'avi,rmvb,rm,asf,divx,mpg,mpeg,mpe,wmv,mp4,mkv,vob'
            }, {
                title : 'Audio files',
                extensions : 'mp3,wav,wma,ogg,ape,acc'
            }],
            init : {
				FilesAdded: function(up, files) {
					for (var i = 0; i < files.length; i++) {
						var file = files[i];
						todoAttach.createAttach(up,file);
					}
					todoAttach.uploaderTodo.start();
					return false;
				},
				BeforeUpload:function(uploaderTodo,file){
					$("#waiting_"+file.id).hide();
				},
                FileUploaded : function(up, file, info) {//文件上传完毕触发
                    var response = $.parseJSON(info.response);
                    if (response.status) {
                     	$("#att_del_"+file.id).show();
                    	$("#att_download_"+file.id).show();
                    	$("#uploader_file_"+file.id).attr("attachId",response.attachId);
                    }
                },
                UploadComplete:function( uploaderTodo,files ) {
                	
                },
                UploadProgress : function(uploaderTodo,file ) {
                	$("#progress_"+file.id).html("上传进度为：" + file.percent + "%");
                	if(file.percent==100){
                		$("#progress_"+file.id).hide();
                	}
                }
            }
        });
		todoAttach.uploaderTodo.init();
	},
	createAttach:function(up,file){
		if(!file.size){
			file.size = 0;
		}
		var div = $('<div class="uploader_file" id="uploader_file_'+file.id+'"></div>');
		div.append('<input type="button" class="uploader_file_icon icon-attach"/>');
		div.append('<span class="uploader_file_name">'+file.name+'</span>');
		div.append('<span class="uploader_file_waiting" id="waiting_'+file.id+'">等待上传</span>');
		div.append('<span class="uploader_file_progress attchUploadstyle" id="progress_'+file.id+'"></span>');
		
		var ctrlSpan =  $('<span class="upload_ctrl"></span>'); 
		ctrlSpan.append('&nbsp;<a class="att_del" id="att_del_'+file.id+'" fileId="'+file.id+'" style="display:none;cursor:pointer;">删除  </a>');
		ctrlSpan.append('<a class="att_download" id="att_download_'+file.id+'" fileId="'+file.id+'" style="display:none;cursor:pointer;">下载</a>');
		div.append(ctrlSpan);
		$("#todofilelist").append(div);
		//删除
		$('#att_del_'+file.id).click(function(){
			var fileId = $(this).attr('fileId');
			var attachId = $("#uploader_file_"+fileId).attr("attachId");
			$("#uploader_file_"+fileId).remove();
			up.removeFile(file);
			var url=ctx + "/admin/myWorkbench/todo/deleteFile.jhtml";
    		var data ={attachId:attachId};
    		CSIT.syncCallService(url,data);
		});
		//下载
		$('#att_download_'+file.id).click(function(){
			var fileId = $(this).attr('fileId');
			var attachId = $("#uploader_file_"+fileId).attr("attachId");
			window.open(ctx + "/admin/myWorkbench/todo/downloadFile.jhtml"+"?attachId="+attachId);
		});
		if(file.attachId){
			$("#waiting_"+file.id).hide();
			$("#att_del_"+file.id).show();
			$("#att_download_"+file.id).show();
			$("#uploader_file_"+file.id).attr("attachId",file.attachId);
		}
	},
}