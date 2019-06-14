package cn.com.afcat.shcpemonitor.common.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;




public class FileUtil {
	
	/**
	 * 写文件操作
	 * @param request
	 */
	public static void doWrite(String fileDirPath,String fileName,String context) throws  Exception{
		
		File fileDir = new File(fileDirPath);
		boolean fileOperFlag = true;
		if (!(fileDir.isDirectory())) {
			fileOperFlag = fileDir.mkdirs();
		}
		if(!fileOperFlag){
			throw new Exception("创建文件夹:"+fileDir.getAbsolutePath()+"失败");
		}
		
		BufferedWriter fileWriter = null;
		try {
			fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
			fileWriter.write(context);
		} catch (Exception e) {
//			LOG.error("报文写入文件失败:");
//			LOG.error(context);
		} finally {
			try {
				if(fileWriter!=null){
					fileWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 处理重复文件，文件重复后重新命名a.jpg=>a(1).jpg
	 * @param fileP 文件存放路径
	 * @param fName 要存放文件
	 * @param mark 默认为"",如果是删除则是a.jpg=>a_del.jpg
	 * @return 处理后文件名
	 * @author yl_yaozhigang
	 * @date 20190118
	 */
	public static String getFileName(File fileP, String fName, String mark){
		String[] fileNames = fileP.list();
		List<String> fnList = Arrays.asList(fileNames);
		
		int inx = fName.lastIndexOf(".");
		String pre = fName.substring(0,inx) + (mark==null?"":mark);
		String aft = fName.substring(inx);
		
		int num = 1;
		String name = pre + aft;
		while(fnList.contains(name)){
			name = pre + "("+(num++)+")" + aft;
		}
		return name;
	}
}
