package com.face.service;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class faceService{

	/**
	 * 图片识别
	 * @param 
	 * @return 
	 * @throws Exception 
	 */
	public Map<String,Object> pic(HttpServletRequest request,MultipartFile pic) throws Exception{
		Map<String,Object> all = new HashMap<String,Object>();
		//1.上传图片
		//获取图片名称
		String file_name = pic.getOriginalFilename();
		
		//获取图片后缀名
		String pre_file = FilenameUtils.getExtension(file_name);
		
		//UUID生成图片名
		String base_name=UUID.randomUUID().toString();
		file_name=base_name+"."+pre_file;
		
		//当前工程路径
		String storage = request.getServletContext().getRealPath("/images");
		
		//创建图片存储目录
		File path=new  File(storage);
		if(!path.exists())
		path.mkdirs();
		String actual = storage+'/'+file_name;
		if(!pic.isEmpty()){
			pic.transferTo(new File(actual));
		}
		all.put("src",file_name);
		FaceCore faceCore = new FaceCore();
		String content = faceCore.face(actual);
		
		JSONObject jsonObject = JSONObject.fromObject(content);
		JSONArray faceArray = jsonObject.getJSONArray("faces");
		System.out.println(faceArray);
		JSONObject faceObject = null;
		//循环遍历属性值
		for(int i = 0; i < faceArray.size(); i++){
			faceObject = faceArray.getJSONObject(i);
			JSONObject attr = faceObject.getJSONObject("attributes");
			JSONObject emotion = attr.getJSONObject("emotion");//情绪
			JSONObject beauty = attr.getJSONObject("beauty");//颜值
			JSONObject gender = attr.getJSONObject("gender");//性别
			JSONObject age = attr.getJSONObject("age");//年龄
			JSONObject glass = attr.getJSONObject("glass");//是否佩戴眼镜
			JSONObject skinstatus = attr.getJSONObject("skinstatus");//面部特征
			JSONObject smile = attr.getJSONObject("smile");//微笑
			JSONObject ethnicity = attr.getJSONObject("ethnicity");//人种
			
			Map<Double,String> emotionMap = new HashMap<Double,String>();
			emotionMap.put((Double)emotion.get("sadness"), "伤心");
			emotionMap.put((Double)emotion.get("neutral"), "平静");
			emotionMap.put((Double)emotion.get("disgust"), "厌恶");
			emotionMap.put((Double)emotion.get("anger"), "愤怒");
			emotionMap.put((Double)emotion.get("surprise"), "惊讶");
			emotionMap.put((Double)emotion.get("fear"), "恐惧");
			emotionMap.put((Double)emotion.get("happiness"), "高兴");
			Object [] emotionObj =emotionMap.keySet().toArray();
			Arrays.sort(emotionObj);
			all.put("emotion", emotionMap.get(emotionObj[emotionObj.length-1]));//情绪
			String ge = gender.get("value").toString();//性别
			if("Female".equals(ge)){
				all.put("gender", "女");
				all.put("beauty", beauty.get("female_score")); //颜值
			}else{
				all.put("gender", "男");
				all.put("beauty", beauty.get("male_score")); //颜值
			}
			all.put("age", age.get("value"));
			String glass_s = glass.get("value").toString();
			if("None".equals(glass_s)){
				all.put("glass", "不佩戴眼镜");
			}
			if("Dark".equals(glass_s)){
				all.put("glass", "佩戴墨镜");
			}
			if("Normal".equals(glass_s)){
				all.put("glass", "佩戴普通眼镜");
			}
			Map<Double,String> skinstatusMap = new HashMap<Double,String>();
			skinstatusMap.put((Double)skinstatus.get("dark_circle"), "黑眼圈");
			skinstatusMap.put((Double)skinstatus.get("stain"), "色斑");
			skinstatusMap.put((Double)skinstatus.get("acne"), "青春痘");
			skinstatusMap.put((Double)skinstatus.get("health"), "健康");
			Object [] skinstatusObj =skinstatusMap.keySet().toArray();
			Arrays.sort(skinstatusObj);
			all.put("skinstatus", skinstatusMap.get(skinstatusObj[skinstatusObj.length-1]));//面部特征
			double val = Double.valueOf(smile.get("value").toString());
			if(val > 50){
				all.put("smile", "微笑");
			}else{
				all.put("smile", "不开心");
			}
			String ethnicity_s = ethnicity.get("value").toString();
			if("Asian".equals(ethnicity_s)){
				all.put("ethnicity", "亚洲人");
			}
			if("White".equals(ethnicity_s)){
				all.put("ethnicity", "白人");
			}
			if("Black".equals(ethnicity_s)){
				all.put("ethnicity", "黑人");
			}
		}
		return all;
	}
}