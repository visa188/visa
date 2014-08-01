/**
 * 富文本编辑品多图上传
 */
function initEditorUpload(config) {
    var button = document.createElement('span');
    var buttonContainer = document.getElementById(config.buttonContainnerId);
    buttonContainer.appendChild(button);

    var msgBox = document.getElementById('uploadErrorMsg');

    // 默认配置
    var uploadConfig = getDefaultUploadConfig();

    // 选择图片按钮容器ID
    uploadConfig.button_placeholder = button;

    // 上传Post请求其他参数
    uploadConfig.post_params = {
        type : config.type
    };

    // 自定义处理方法
    var customSettings = uploadConfig.custom_settings;
    customSettings.uploadSuccessCallback = config.uploadSuccessCallback;
    customSettings.uploadCompleteCallback = config.uploadCompleteCallback;

    // Override
    customSettings.showErrorMsg = function(msg) {
        var li = document.createElement('li');
        li.innerHTML = msg;

        msgBox.appendChild(li);
        msgBox.style.display = 'block';
    };
    // Override
    customSettings.clearErrorMsg = function() {
        msgBox.innerHTML = '';
        msgBox.style.display = 'none';
        document.getElementById('editorMsgBoxMsgDv').innerHTML = '';
    };
    // Override
    customSettings.fileQueued = function(swfUploader, file) {
        var fileId = file.id;

        var html = '<div class="name">' + this.getFilename(file) + '</div>';
        html += '<div class="size">' + this.getFileSize(file) + '</div>';
        html += '<div class="remove"></div>';

        var item = document.createElement('li');
        item.id = 'li_' + fileId;
        item.innerHTML = html;

        var a = document.createElement('a');
        a.href = 'javascript:void(0)';
        a.target = '_self';
        a.innerHTML = '&nbsp;';
        a.onclick = function() {
            swfUploader.cancelUpload(fileId);
            item.parentNode.removeChild(item);
        };
        item.childNodes[2].appendChild(a);

        document.getElementById('uploadFiles').appendChild(item);
    };
    // Override
    customSettings.uploadProgress = function(swfUploader, file, bytesLoaded, bytesTotal) {
        var percent = bytesLoaded / bytesTotal;

        var widthLi = 524;
        var widthBg = 800;
        var width = widthLi * percent - widthBg;

        var li = document.getElementById('li_' + file.id);
        li.className = 'uploading';
        li.style.backgroundPosition = width + 'px 0';
        
        if (bytesLoaded < bytesTotal) {
            li.childNodes[2].innerHTML = Math.ceil(percent * 100) + '%';
        } else {
            li.childNodes[2].innerHTML = '<span class="loading"></span>';
        }
    };
    // Override
    customSettings.uploadSuccess = function(swfUploader, file, serverData) {
        var li = document.getElementById('li_' + file.id);
        li.className = '';
        li.childNodes[2].innerHTML = '<span class="success"></span>';

        if (this.uploadSuccessCallback) {
            this.uploadSuccessCallback(file, serverData);
        }
    };
    // Override
    customSettings.uploadError = function(swfUploader, file, errorMsg) {
        var li = document.getElementById('li_' + file.id);
        li.className = '';
        li.childNodes[2].innerHTML = '<span class="failed"></span>';

        this.showErrorMsg(errorMsg);
    };
    // Override
    customSettings.uploadComplete = function(swfUploader, file) {
        var stats = swfUploader.getStats();
        if (stats.files_queued > 0) {
            swfUploader.startUpload();
        } else {
            if (stats.successful_uploads > 0) {
                if (this.uploadCompleteCallback) {
                    this.uploadCompleteCallback(stats);
                }
            } else {
                swfUploader.setButtonDisabled(false);
                swfUploader.setButtonCursor(SWFUpload.HAND);
            }
        }
    };

    config.swfUploader = new SWFUpload(uploadConfig);
}

/**
 * 后台管理单图上传
 */
function initAdminUpload(config) {
    var button = document.createElement('span');
    var buttonContainer = document.createElement('span');
    buttonContainer.className = 'uploadButtonContainer';
    buttonContainer.appendChild(button);

    var msgBox = document.createElement('span');
    msgBox.className = 'uploadErrorMsg';
    msgBox.appendChild(document.createElement('span'));
    msgBox.appendChild(document.createElement('span'));

    var containner = document.getElementById(config.uploadContainerId);
    containner.appendChild(buttonContainer);
    containner.appendChild(msgBox);

    // 默认配置
    var uploadConfig = getDefaultUploadConfig();

    // 上传个数限制
    uploadConfig.file_upload_limit = 0;

    // 单选
    uploadConfig.button_action = SWFUpload.BUTTON_ACTION.SELECT_FILE;

    // 选择图片按钮容器ID
    uploadConfig.button_placeholder = button;

    // 上传Post请求其他参数
    uploadConfig.post_params = {
        type : config.type
    };

    // 自定义处理方法
    var customSettings = uploadConfig.custom_settings;
    customSettings.uploadSuccessCallback = config.uploadSuccessCallback;

    // Override
    customSettings.showErrorMsg = function(msg) {
        msgBox.childNodes[1].innerHTML = ' (' + msg + ')';
    };
    // Override
    customSettings.clearErrorMsg = function() {
        msgBox.childNodes[0].innerHTML = '';
        msgBox.childNodes[1].innerHTML = '';
    };
    // Override
    customSettings.fileQueued = function(swfUploader, file) {
        var fileId = file.id;

        msgBox.childNodes[0].innerHTML = this.getFilename(file);
        this.showErrorMsg(this.getFileSize(file));
    };
    // Override
    customSettings.fileDialogComplete = function(swfUploader, numFilesSelected, numFilesQueued) {
        if (numFilesSelected > 0 && numFilesQueued > 0) {
            swfUploader.startUpload();
            swfUploader.setButtonDisabled(true);
            swfUploader.setButtonCursor(SWFUpload.ARROW);
        }
    };
    // Override
    customSettings.uploadProgress = function(swfUploader, file, bytesLoaded, bytesTotal) {
        if (bytesLoaded < bytesTotal) {
            var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
            this.showErrorMsg(percent + '%');
        } else {
            this.showErrorMsg('正在处理图片，请稍候...');
        }
    };
    // Override
    customSettings.uploadSuccess = function(swfUploader, file, serverData) {
        if (this.uploadSuccessCallback) {
            this.uploadSuccessCallback(file, serverData);
        }
        this.showErrorMsg('上传成功');
    };
    // Override
    customSettings.uploadError = function(swfUploader, file, errorMsg) {
        this.showErrorMsg(errorMsg);
    };
    // Override
    customSettings.uploadComplete = function(swfUploader, file) {
        var stats = swfUploader.getStats();
        if (stats.files_queued > 0) {
            swfUploader.startUpload();
        } else {
            if (stats.successful_uploads > 0) {
                if (this.uploadCompleteCallback) {
                    this.uploadCompleteCallback(stats);
                }
            }
            swfUploader.setButtonDisabled(false);
            swfUploader.setButtonCursor(SWFUpload.HAND);
        }
    };

    config.swfUploader = new SWFUpload(uploadConfig);
}

/**
 * 图片上传组件默认设置 button_placeholder_id: 选择图片按钮容器ID; post_params: 上传Post请求其他参数; custom_settings: 自定义处理方法，具体参考extend对象
 * 
 * @returns Default upload config
 */
function getDefaultUploadConfig() {
    var extend = {
        showErrorMsg : function(msg) {
            // can be override
        },
        clearErrorMsg : function() {
            // can be override
        },
        fileQueued : function(swfUploader, file) {
            // can be override
        },
        fileDialogComplete : function(swfUploader, numFilesSelected, numFilesQueued) {
            // can be override
        },
        uploadStart : function(swfUploader, file) {
            // can be override
        },
        uploadProgress : function(swfUploader, file, bytesLoaded, bytesTotal) {
            // can be override
        },
        uploadSuccess : function(swfUploader, file, serverData) {
            // can be override
        },
        uploadError : function(swfUploader, file, errorMsg) {
            // can be override
        },
        uploadComplete : function(swfUploader, file) {
            // can be override
        },
        getFileSize : function(file) {
            var size = file.size;
            if (size < 1024) {
                return size + ' B';
            } else if (size < 1048576) {
                return (Math.round(size * 100 / 1024) / 100) + ' KB';
            } else if (size < 1073741824) {
                return (Math.round(size * 100 / 1048576) / 100) + ' MB';
            } else if (size < 1099511627776) {
                return (Math.round(size * 100 / 1073741824) / 100) + ' GB';
            }
            return size;
        },
        getFilename : function(file) {
            var filename = file.name;
            if (filename.length > 28) {
                filename = filename.substring(0, 20) + '...' + filename.substring(filename.length - 6, filename.length);
            }
            return filename;
        }
    };

    var defaultHandler = {
        preLoad : function() {
            if (!this.support.loading) {
                this.customSettings.showErrorMsg('提示：本页面的图片上传功能需要您的浏览器安装v10以上版本的Flash播放器。');
                return false;
            }
        },
        loadFailed : function() {
            this.customSettings.showErrorMsg('加载图片上传组件出现错误，请刷新页面重试或联系管理员。');
        },
        fileDialogStart : function() {
            this.customSettings.clearErrorMsg();
        },
        fileQueued : function(file) {
            try {
                // call customer's function
                this.customSettings.fileQueued(this, file);
            } catch (e) {
                this.debug(e);
            }
        },
        fileQueueError : function(file, errorCode, message) {
            try {
                var msg = null;
                switch (errorCode) {
                case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                    msg = (message == 0 ? '您选择的文件太多了。' : '您最多只能选择 ' + message + ' 个文件。');
                    break;
                case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                    msg = '文件[' + this.customSettings.getFilename(file) + '](' + this.customSettings.getFileSize(file)
                            + ')太大了。';
                    break;
                case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                    msg = '文件[' + this.customSettings.getFilename(file) + '](' + this.customSettings.getFileSize(file)
                            + ')太小了。';
                    break;
                case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                    msg = '不合法的文件类型[' + this.customSettings.getFilename(file) + ']('
                            + this.customSettings.getFileSize(file) + ')。';
                    break;
                default:
                    if (file !== null) {
                        msg = '出错啦，错误代码[' + errorCode + ']。';
                    }
                    break;
                }
                if (msg) {
                    this.customSettings.showErrorMsg(msg);
                }
            } catch (e) {
                this.debug(e);
            }
        },
        fileDialogComplete : function(numFilesSelected, numFilesQueued) {
            try {
                // call customer's function
                this.customSettings.fileDialogComplete(this, numFilesSelected, numFilesQueued);
            } catch (e) {
                this.debug(e);
            }
        },
        uploadStart : function(file) {
            try {
                // call customer's function
                this.customSettings.uploadStart(this, file);
            } catch (e) {
                this.debug(e);
            }
        },
        uploadProgress : function(file, bytesLoaded, bytesTotal) {
            try {
                // call customer's function
                this.customSettings.uploadProgress(this, file, bytesLoaded, bytesTotal);
            } catch (e) {
                this.debug(e);
            }
        },
        uploadSuccess : function(file, serverData) {
            try {
                var dataHeader = serverData.substring(0, 6).toUpperCase();
                if (dataHeader === 'HTTP:/') {
                    // call customer's function
                    this.customSettings.uploadSuccess(this, file, serverData);
                } else {
                    var stats = this.getStats();
                    stats.upload_errors += 1;
                    stats.successful_uploads -= 1;
                    this.setStats(stats);

                    var errorMsg;
                    if (dataHeader === 'ERROR:') {
                        errorMsg = serverData.substring(6);
                    } else {
                        errorMsg = '文件[' + this.customSettings.getFilename(file) + ']上传错误，请稍候再试。';
                    }
                    // call customer's function
                    this.customSettings.uploadError(this, file, errorMsg);
                }
            } catch (e) {
                this.debug(e);
            }
        },
        uploadError : function(file, errorCode, message) {
            try {
                var msg = null;

                switch (errorCode) {
                case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
                    // msg = 'Upload limit exceeded.';
                    break;
                case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
                    // msg = 'Cancelled';
                    break;
                case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
                    // msg = 'Stopped';
                    break;
                default:
                    msg = '文件[' + this.customSettings.getFilename(file) + ']上传错误，请稍候再试。';
                    this.debug('Error Code: ' + errorCode + ', File name: ' + file.name + ', File size: '
                            + this.customSettings.getFileSize(file) + ', Message: ' + message);
                    break;
                }

                if (msg) {
                    // call customer's function
                    this.customSettings.uploadError(this, file, msg);
                }
            } catch (e) {
                this.debug(e);
            }
        },
        uploadComplete : function(file) {
            try {
                // call customer's function
                this.customSettings.uploadComplete(this, file);
            } catch (e) {
                this.debug(e);
            }
        }
    };

    return {
        // Backend Settings
        upload_url : '/upload/image',
        file_post_name : 'Filedata',
        post_params : null,

        // Flash Settings
        flash_url : '/upload/swfupload.swf',
        flash9_url : '/upload/swfupload_fp9.swf',

        // File Upload Settings
        file_size_limit : "2 MB",
        file_types : "*.jpg;*.jpeg;*.gif;*.png",
        file_types_description : "jpg、gif、png图片",
        file_upload_limit : 10,
        file_queue_limit : 0,

        // Button Settings
        button_placeholder : null, // button_placeholder and button_placeholder_id will be set at least one
        button_placeholder_id : '',
        button_image_url : "/upload/btn.png",
        button_width : 69,
        button_height : 24,
        button_action : SWFUpload.BUTTON_ACTION.SELECT_FILES,
        button_cursor : SWFUpload.CURSOR.HAND,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,

        use_query_string : false,
        requeue_on_error : false,
        http_success : [ 201, 202 ],
        assume_success_timeout : 0,
        prevent_swf_caching : true,
        preserve_relative_urls : false,
        custom_settings : extend,

        // Event Handler Settings
        // 加载flash检查
        swfupload_preload_handler : defaultHandler.preLoad,
        // flash加载失败
        swfupload_load_failed_handler : defaultHandler.loadFailed,
        // 弹出文件选择框
        file_dialog_start_handler : defaultHandler.fileDialogStart,
        // 文件添加成功
        file_queued_handler : defaultHandler.fileQueued,
        // 文件添加失败
        file_queue_error_handler : defaultHandler.fileQueueError,
        // 文件选择框关闭
        file_dialog_complete_handler : defaultHandler.fileDialogComplete,
        // 开始上传文件
        upload_start_handler : defaultHandler.uploadStart,
        // 上传进度
        upload_progress_handler : defaultHandler.uploadProgress,
        // 上传失败
        upload_error_handler : defaultHandler.uploadError,
        // 上传成功
        upload_success_handler : defaultHandler.uploadSuccess,
        // 上传完成
        upload_complete_handler : defaultHandler.uploadComplete,

        // Debug Settings
        debug : false
    };
}
