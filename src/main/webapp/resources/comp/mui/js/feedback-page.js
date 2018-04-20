(function(mui, window, document, undefined) {
	mui.init();
	var get = function(id) {
		return document.getElementById(id);
	};
	var qsa = function(sel) {
		return [].slice.call(document.querySelectorAll(sel));
	};
	var ui = {
		imageList: get('image-list'),
	};
	ui.clearForm = function() {
		ui.imageList.innerHTML = '';
		ui.newPlaceholder();
	};
	ui.getFileInputArray = function() {
		return [].slice.call(ui.imageList.querySelectorAll('input[type="file"]'));
	};
	ui.getFileInputIdArray = function() {
		var fileInputArray = ui.getFileInputArray();
		var idArray = [];
		fileInputArray.forEach(function(fileInput) {
			if (fileInput.value != '') {
				idArray.push(fileInput.getAttribute('id'));
			}
		});
		return idArray;
	};
	var imageIndexIdNum = 0;
	ui.newPlaceholder = function(attachId,extention) {
		var fileInputArray = ui.getFileInputArray();
		if (fileInputArray &&
			fileInputArray.length > 0 &&
			fileInputArray[fileInputArray.length - 1].parentNode.classList.contains('space')) {
			return;
		}
		imageIndexIdNum++;
		var placeholder = document.createElement('div');
		placeholder.setAttribute('class', 'image-item space');
		var closeButton = document.createElement('div');
		closeButton.setAttribute('class', 'image-close');
		closeButton.innerHTML = 'X';
		if(attachId!=undefined &&attachId!=null&&attachId!=''){
			placeholder.setAttribute('attachId',attachId);
			placeholder.classList.remove('space');
			var audio_Video_files = "avi,rmvb,rm,asf,divx,mpg,mpeg,mpe,wmv,mp4,mkv,vob,mp3,wav,wma,ogg,ape,acc";
    		var files = "doc,docx,pptx,ppt,txt,xls,xlsx,pdf";
    		var zipfiles = "zip,7z,rar";
    		var imgfiles = "jpg,jpeg,gif,png,JPG,GIF,PNG";
			if(imgfiles.indexOf(extention) >= 0){
				placeholder.style.backgroundImage = 'url('+ctx+'/getImgAttachId.jhtml?attachId='+attachId+')';
			}else if(audio_Video_files.indexOf(extention) >= 0){
				placeholder.style.backgroundImage = "url("+ctx+"/resources/images/file_img/video.png)";
    		}else if(files.indexOf(extention) >= 0){
				placeholder.style.backgroundImage = 'url('+ctx+'/resources/images/file_img/file.png)';
    		}else if(zipfiles.indexOf(extention) >= 0){
				placeholder.style.backgroundImage = 'url('+ctx+'/resources/images/file_img/zipfile.png)';
    		}
		}
		closeButton.addEventListener('click', function(event) {
			event.stopPropagation();
			event.cancelBubble = true;
			setTimeout(function() {
				ui.imageList.removeChild(placeholder);
				var attachIds = "";
				var attachIdObj   = document.getElementsByClassName('image-item');
				for(var i=0; i<attachIdObj.length; i++){
					if(attachIdObj[i].getAttribute('attachId')!=undefined&&attachIdObj[i].getAttribute('attachId')!=null&&attachIdObj[i].getAttribute('attachId')!=''){
						attachIds = attachIds+attachIdObj[i].getAttribute('attachId')+'^';
					}
				}
				if(attachIds.length>0){
					document.getElementById("attachIds").value = attachIds.substring(0,attachIds.length-1);
				}else{
					document.getElementById("attachIds").value = "";
				}
				ui.newPlaceholder(null);
			}, 0);
			return false;
		}, false);
		var fileInput = document.createElement('input');
		fileInput.setAttribute('type', 'file');
		fileInput.setAttribute('accept', 'image/*');
		fileInput.setAttribute('id', 'image-' + imageIndexIdNum);
		fileInput.addEventListener('change', function(event) {

			var file = fileInput.files[0];
			if (file) {
				//获取文件名
				var fileName = file.name;
				var one = fileName.lastIndexOf(".");
				//获取文件后缀
				var extention = fileName.substring((one+1),fileName.length);
				//自定义表单
				var formData = new FormData();
				formData.append("file",file);
				formData.append("fileName",fileName);
				formData.append("extention",extention);
				var path = document.getElementById("path").value;

				mui.ajax({
					url : ctx+"/wx/"+path,
					type: 'post',
					data : formData,
					timeout: 5000,
					async: true,
					cache: false,
					processData : false,
					contentType : false,
					success:function(data) {
						if(data.isSucess){
							placeholder.setAttribute('attachId',data.attachId);
							//把文件读成base64
							var reader = new FileReader();
							reader.onload = function() {
								//处理 android 4.1 兼容问题
								var base64 = reader.result.split(',')[1];
								//判断是不是图片 如果是展示图片、不是展示文件默认图片
								var audio_Video_files = "avi,rmvb,rm,asf,divx,mpg,mpeg,mpe,wmv,mp4,mkv,vob,mp3,wav,wma,ogg,ape,acc";
								var files = "doc,docx,pptx,ppt,txt,xls,xlsx";
								var zipfiles = "zip,7z,rar";
								var imgfiles = "jpg,jpeg,gif,png,JPG,GIF,PNG";
								var dataUrl = 'data:image/png;base64,' + base64;
								if(imgfiles.indexOf(extention) >= 0){
									placeholder.style.backgroundImage = 'url(' + dataUrl + ')';
								}else if(audio_Video_files.indexOf(extention) >= 0){
									placeholder.style.backgroundImage = "url("+ctx+"/resources/images/file_img/video.png)";
								}else if(files.indexOf(extention) >= 0){
									placeholder.style.backgroundImage = 'url('+ctx+'/resources/images/file_img/file.png)';
								}else if(zipfiles.indexOf(extention) >= 0){
									placeholder.style.backgroundImage = 'url('+ctx+'/resources/images/file_img/zipfile.png)';
								}
							};
							reader.readAsDataURL(file);
							placeholder.classList.remove('space');
							var attachIds = "";
							var attachIdObj   = document.getElementsByClassName('image-item');
							for(var i=0; i<attachIdObj.length; i++){
								if(attachIdObj[i].getAttribute('attachId')!=undefined&&attachIdObj[i].getAttribute('attachId')!=null&&attachIdObj[i].getAttribute('attachId')!=''){
									attachIds = attachIds+attachIdObj[i].getAttribute('attachId')+'^';
								}
							}
							if(attachIds.length>0){
								document.getElementById("attachIds").value = attachIds.substring(0,attachIds.length-1);
							}
						}else{
							mui.alert(data.message);
						}
					},
					error:function(xhr,type,errorThrown) {
						if(type=='abort'){
							mui.alert('服务器异常，请重试！');
						}else if(type=='timeout'){
							mui.alert('连接服务器超时，请重试！');
						}else{
							mui.alert(type);
						}
					}
				});
			}
		}, false);

        if((typeof  previewType=="undefined")||previewType!=1){
            placeholder.appendChild(closeButton);
            placeholder.appendChild(fileInput);
        }
		ui.imageList.appendChild(placeholder);
	};
	//初始化图片数据
	if(attachId!=undefined&&attachId!=null&&attachId!=''){
		var initPath = document.getElementById("initPath").value;
		var AttachList = CSIT.syncCallService(ctx+"/wx/"+initPath,{attachId:attachId});
		if(AttachList.length>0){
			for(var i=0; i<AttachList.length; i++){
				ui.newPlaceholder(AttachList[i].attachId,AttachList[i].extention);
			}
		}
		var attachIds = "";
		var attachIdObj   = document.getElementsByClassName('image-item');
		for(var i=0; i<attachIdObj.length; i++){
			if(attachIdObj[i].getAttribute('attachId')!=undefined&&attachIdObj[i].getAttribute('attachId')!=null&&attachIdObj[i].getAttribute('attachId')!=''){
				attachIds = attachIds+attachIdObj[i].getAttribute('attachId')+'^';
			}
		}
		if(attachIds.length>0){
			document.getElementById("attachIds").value = attachIds.substring(0,attachIds.length-1);   
		}else{
			document.getElementById("attachIds").value = "";
		}
	}else{
		ui.newPlaceholder();
	}
})(mui, window, document, undefined);