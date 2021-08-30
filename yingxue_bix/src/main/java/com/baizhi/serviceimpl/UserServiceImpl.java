package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.FeedbackMapper;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.entity.User;
import com.baizhi.service.FeedbackService;
import com.baizhi.service.UserService;
import com.baizhi.util.AliyunOSSUtil;
import com.baizhi.util.UUIDUtil;
import com.baizhi.vo.CommonVo;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    UserMapper userMapper;

    @Resource
    HttpServletRequest request;
    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //设置条件查询对象
        FeedbackExample example = new FeedbackExample();
        //查询总数
        int count = userMapper.selectCountByExample(example);

        //设置总条数
        map.put("total",count);

        //创建的分页对象，参数，起始条数、数据数
        RowBounds rowBounds = new RowBounds((page - 1)*pageSize, pageSize);
        List<User> feedbacks = userMapper.selectByExampleAndRowBounds(example, rowBounds);

        //根据分页查询数据
        map.put("rows",feedbacks);
        return map;
    }

    @Override
    public HashMap<String, Object> update(User user) {
        return null;
    }

    @Override
    public void delete(User user) {
        //1.删除文件
        //根据文件信息
        User users = userMapper.selectOne(user);
        System.out.println(users);

        //获取图片网络路径
        String headImg = users.getHeadImg();

        //字符串处理
        String imgPath = headImg.replace("https://yingxue-2103.oss-cn-beijing.aliyuncs.com/", "");
        /**
         * 将阿里云文件删除
         *  参数：
         * @param bucketName（String）  存储空间名  yingx-2103
         * @param fileName（String）    文件名   目录名/文件名  lalala.png
         * */
        AliyunOSSUtil.deleteFile("yingxue-2103",imgPath);
        //2.删除数据
        userMapper.delete(user);
    }

    @Override
    public void add(User user) {
        log.info("user Service 获取用户数据：{}",user);
        user.setId(UUIDUtil.getUUID());
        log.info("user service 调用Dao添加数据: {}",user);
        userMapper.insertSelective(user);
    }

    @Override
    public String uploadHeadImg(MultipartFile headImg) {
        //1.获取文件名  logo.jpg
        String filename = headImg.getOriginalFilename();
        //加时间戳
        String newName = new Date().getTime()+"-"+filename;
        //2.获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("upload/headImg");
        System.out.println(realPath);

        File file = new File(realPath);
        //文件夹不存在
        if(!file.exists()){
            file.mkdirs();  //创建文件夹
        }

        String message=null;
        //3.文件上传
        try {
            headImg.transferTo(new File(newName,realPath));

            //返回文件名
            message=newName;

        } catch (IOException e) {
            e.printStackTrace();
            message=newName;
        }

        return message;
    }

    @Override
    public String uploadHeadImgAliyun(MultipartFile headImg) {
        //1.获取文件名
        String filename = headImg.getOriginalFilename();
        String newName= new Date().getTime()+"-"+filename;

        //配置存储空间名
        String bucketName = "yingxue-2103";
        //配置文件名
        String objectName = "userImg/"+newName;
        //拼接图片网络地址
        String netPath = "https://yingxue-2103.oss-cn-beijing.aliyuncs.com/"+objectName;
        String message=null;

        //文件上传
        try {
            /**
             * 将文件以字节数组的形式上传至阿里云
             *  参数：
             * @param bucketName（String）  存储空间名
             * @param fileName（String）    文件名   目录名/文件名
             * @param headImg（MultipartFile） MultipartFile类型的文件
             * */
            AliyunOSSUtil.uploadfileBytes(bucketName,objectName,headImg);
            message=netPath;
        } catch (Exception e) {
            e.printStackTrace();
            message=netPath;
        }
        return message;
    }

    @Override
    public User queryById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /* @Override
     public HashMap<String, Object> update(User user) {
          HashMap<String, Object> map = new HashMap<>();
        try {
             userMapper.updateByPrimaryKeySelective(user);
            return CommonVo.success("修改成功");
         } catch (Exception e) {
             e.printStackTrace();
             return CommonVo.faild("修改失败");
         }
         *//*try {
            userMapper.updateByPrimaryKeySelective(user);
            return CommonVO.success("message");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild();
       }*//*
        //return map;
    }*/
   @AddLog(value = "修改用户")
   @Override
   public CommonVo updates(User user) {
       try {
           userMapper.updateByPrimaryKeySelective(user);
           return CommonVo.success("修改成功");
       } catch (Exception e) {
           e.printStackTrace();
           return CommonVo.faild("修改失败");
       }
        /*try {
            userMapper.updateByPrimaryKeySelective(user);
            return CommonVO.success("message");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild();
       }*/
       //return map;
   }

}
