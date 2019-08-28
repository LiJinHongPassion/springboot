https://blog.csdn.net/liweizhong193516/article/details/53244134

## 简述

代码demo：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_fastdfs_demo

## 安装jar包 - fastdfs-client-java

1. 先下载源代码

   https://github.com/happyfish100/fastdfs-client-java

2. 打开项目选择maven install

## 添加依赖

```xml
<dependency>
    <groupId>org.csource</groupId>
    <artifactId>fastdfs-client-java</artifactId>
    <version>1.27-SNAPSHOT</version>
</dependency>
```

##  fdfs_client.conf

该文件放置resources目录下

```properties
#连接tracker服务器超时时长
connect_timeout = 2  
#socket连接超时时长
network_timeout = 30
#文件内容编码 
charset = UTF-8 
#tracker服务器端口
http.tracker_http_port = 8080
#http.anti_steal_token = no
#http.anti_steal.check_token=true
#http.secret_key = FastDFS1234567890
#tracker服务器IP和端口（可以写多个）
tracker_server = 192.168.26.101:22122
```

##  FastDFSClient.java

FastDFS文件上传下载工具类

```java
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
/**
 * FastDFS文件上传下载工具类
 */
public class FastDFSClient{

	private static final String CONFIG_FILENAME = "E:\\技术学习\\1 springboot\\springboot_fastdfs_demo\\src\\main\\resources\\fdfs_client.conf";
	//自行定义
//	private static final String CONFIG_FILENAME = "D:\\ACTS\\acts_market\\src\\main\\resources\\fdfs_client.conf";
	private static final String GROUP_NAME = "market1";
	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient storageClient = null;

	static{
		try {
			ClientGlobal.init(CONFIG_FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
	public FastDFSClient() throws Exception {
		trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
		trackerServer = trackerClient.getConnection();
		storageServer = trackerClient.getStoreStorage(trackerServer);;
		storageClient = new StorageClient(trackerServer, storageServer);
	}

	/**
	 * 上传文件
	 * @param file 文件对象
	 * @param fileName 文件名
	 * @return
	 */
	public String[] uploadFile(File file, String fileName) {
		return uploadFile(file,fileName,null);
	}

	/**
	 * 上传文件
	 * @param file 文件对象
	 * @param fileName 文件名
	 * @param metaList 文件元数据
	 * @return
	 */
	public String[] uploadFile(File file, String fileName, Map<String,String> metaList) {
		try {
			byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
			NameValuePair[] nameValuePairs = null;
			if (metaList != null) {
				nameValuePairs = new NameValuePair[metaList.size()];
				int index = 0;
				for (Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<String,String> entry = iterator.next();
					String name = entry.getKey();
					String value = entry.getValue();
					nameValuePairs[index++] = new NameValuePair(name,value);
				}
			}
			return storageClient.upload_file(GROUP_NAME,buff,fileName,nameValuePairs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取文件元数据
	 * @param fileId 文件ID
	 * @return
	 */
	public Map<String,String> getFileMetadata(String groupname,String fileId) {
		try {
			NameValuePair[] metaList = storageClient.get_metadata(groupname,fileId);
			if (metaList != null) {
				HashMap<String,String> map = new HashMap<String, String>();
				for (NameValuePair metaItem : metaList) {
					map.put(metaItem.getName(),metaItem.getValue());
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除文件
	 * @param fileId 文件ID
	 * @return 删除失败返回-1，否则返回0
	 */
	public int deleteFile(String groupname,String fileId) {
		try {
			return storageClient.delete_file(groupname,fileId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 下载文件
	 * @param fileId 文件ID（上传文件成功后返回的ID）
	 * @param outFile 文件下载保存位置
	 * @return
	 */
	public int downloadFile(String groupName,String fileId, File outFile) {
		FileOutputStream fos = null;
		try {
			byte[] content = storageClient.download_file(groupName,fileId);
			fos = new FileOutputStream(outFile);
			InputStream ips = new ByteArrayInputStream(content);
			IOUtils.copy(ips,fos);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		FastDFSClient client = new FastDFSClient();
		File file = new File("D:\\寸照.png");
		String[] result = client.uploadFile(file, "png");
		System.out.println(result.length);
		System.out.println(result[0]);
		System.out.println(result[1]);
	}
}
```

## FileController.java

```java
package com.example.li.springboot_fastdfs_demo.controller;

import com.example.li.springboot_fastdfs_demo.utils.FastDFSClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * @author LJH
 * @date 2019/7/25-16:23
 * @QQ 1755497577
 */
@RequestMapping("/file")
@RestController
public class FileController {

    /**
     * 描述: 上传文件
     *
     * @author LJH-1755497577 2019/7/25 19:16
     * @param multipartFile
     * @return java.lang.String 成功返回组名和ID
     */
    @PostMapping("/upload")
    public String uploadFile( @RequestParam("file") MultipartFile multipartFile) {
        File savefile = new File("D:\\" + multipartFile.getOriginalFilename());
        FastDFSClient fastDFSClient = null;

        try {
            multipartFile.transferTo(savefile);
            fastDFSClient = new FastDFSClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] strings = fastDFSClient.uploadFile(savefile,
                multipartFile.getOriginalFilename()+System.currentTimeMillis());
        return strings[0] + "/" + strings[1];
    }


    /**
     * 描述: 删除文件
     *
     * @author LJH-1755497577 2019/7/25 19:47
     * @param groupName group名字 例子：group1
     * @param fileId 文件id 例子：M00/00/00/wKgaZV05lKCAcqUZAAEvZ9-NEB87.1.png
     * @return java.lang.String
     */
    @PostMapping("/del")
    public String delectFile( @RequestParam("groupName") String groupName,
                              @RequestParam("fileId") String fileId) {

        FastDFSClient fastDFSClient = null;

        try {

            fastDFSClient = new FastDFSClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int reulst = fastDFSClient.deleteFile(groupName, fileId);
        return String.valueOf(reulst);
    }

}

```

## fileupload.html

```html
<html>
<head>
    <title>文件上传</title>
</head>
<body>




<h1>上传文件</h1>
<form method="post" action="http://localhost:8080/file/upload" enctype="multipart/form-data">
    <input type="text" name="fileName"/>
    <input type="file" name="file"/>
    <input type="submit"/>
</form>

<h1>删除文件</h1>
<form method="post" action="http://localhost:8080/file/del">
    组名：<input type="text" name="groupName" />
    ID：<input type="text" name="fileId"/>
    <input type="submit"/>
</form>

</body>
</html>
```



## application.yml

```yml
# multipart.maxFileSize=10Mb是设置单个文件的大小, multipart.maxRequestSize=100Mb是设置单次请求的文件的总大小
#
#如果是想要不限制文件上传的大小，那么就把两个值都设置为-1就行啦
spring.servlet.multipart.max-file-size: 10MB
spring.servlet.multipart.max-request-size: 100MB
```





## 启动centos

前面使用nginx支持http方式访问文件，但所有人都能直接访问这个文件服务器了，所以做一下权限控制。

FastDFS的权限控制是在服务端开启token验证，客户端根据文件名、当前unix时间戳、秘钥获取token，在地址中带上token参数即可通过http方式访问文件。

- 服务端开启token验证

```shell
修改http.conf
# vim /etc/fdfs/http.conf

设置为true表示开启token验证
http.anti_steal.check_token=true

设置token失效的时间单位为秒(s)
http.anti_steal.token_ttl=1800

密钥，跟客户端配置文件的fastdfs.http_secret_key保持一致
http.anti_steal.secret_key=FASTDFS1234567890

如果token检查失败，返回的页面
http.anti_steal.token_check_fail=/ljzsg/fastdfs/page/403.html
```

- 配置客户端

客户端只需要设置如下两个参数即可，两边的密钥保持一致。

```
# token 防盗链功能
fastdfs.http_anti_steal_token=true
# 密钥
fastdfs.http_secret_key=FASTDFS1234567890
```

- 启动

```shell
systemctl stop firewalld.service # 关闭防火墙
/etc/init.d/fdfs_trackerd start # 启动tracker服务
/etc/init.d/fdfs_storaged start # 启动storage服务
/usr/local/nginx/sbin/nginx # 启动nginx
```

