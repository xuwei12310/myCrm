var pluploadUtil = {		
	createAttach:function(file,fileList){
		if(!file.size){
			file.size = 0;
		}
		var div = $('<div class="uploader_file" id="uploader_file_'+file.id+'"></div>');
		div.append('<input type="button" class="uploader_file_icon icon-attach"/>');
		div.append('<span class="uploader_file_name">'+file.name+'</span>');
		div.append('<span class="uploader_file_size">('+CSIT.showSize(file.size)+')</span>');
		div.append('<span class="uploader_file_waiting" id="waiting_'+file.id+'">等待上传</span>');
		div.append('<span class="uploader_file_progress attchUploadstyle" id="progress_'+file.id+'"></span>');
		
		var ctrlSpan =  $('<span class="upload_ctrl"></span>'); 
		ctrlSpan.append('<a class="att_del" id="att_del_'+file.id+'" fileId="'+file.id+'" style="display:none;cursor:pointer;">删除  </a>');
		ctrlSpan.append('<a class="att_download" id="att_download_'+file.id+'" fileId="'+file.id+'" style="display:none;cursor:pointer;">下载</a>');
		div.append(ctrlSpan);
		$(fileList).append(div);
		
		
		$('#att_del_'+file.id).click(function(){
			var fileId = $(this).attr('fileId');
			$("#uploader_file_"+fileId).remove();
		});
		
		$('#att_download_'+file.id).click(function(){
			var fileId = $(this).attr('fileId');
			var attachId = $("#uploader_file_"+fileId).attr("attachId");
			window.open("yibaniban/admin/sys/work/downloadWorkAttach.jhtml?attachId="+attachId);
	     });
		if(file.attachId){
			$("#waiting_"+file.id).hide();
			$("#att_del_"+file.id).show();
			$("#att_download_"+file.id).show();
			$("#uploader_file_"+file.id).attr("attachId",file.attachId);
		}
	}
}