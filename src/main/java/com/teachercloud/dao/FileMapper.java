package com.teachercloud.dao;

import com.teachercloud.pojo.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {


    //根据文件的名称将文件移除数据库(后期与删除文件方法一起调用)+
    int deleteByPrimaryKey(Long fileId);

    //录入文件（上传文件时将文件存入数据库）+
    int insert(File record);


    int insertSelective(File record);


    File selectByPrimaryKey(Long fileId);


    //根据文件的id对文件的信息进行修改（文件状态等一些信息）+
    int updateByPrimaryKeySelective(File record);



    int updateByPrimaryKey(File record);


    //----------------------------------------------------------------------------//

    /**
     * 文件查找(文件部分信息可能相同)
     * 学生查找文件可以通过一级查询（课程类型）在进行二级查询（文件类型）
     */

    //1、通过文件名查找文件（全局查询）+
    List<File> lookupFileName(String name);


    //2、通过课程查找文件(一级查询（课程类型）)+
    List<File>  loadFirstFileCourse(Integer courseId);


    //3、通过文件类型查找文件(二级查询（文件类型）)+
    List<File> loadSecondFileType(@Param("courseId")Integer courseId ,@Param("type") Integer type);


    //4、模糊查询通过关键字查询
    List<File> vagueFile(@Param("courseId")Integer courseId, @Param("type") Integer type,
                         @Param("name")String name);


    //5、遍历所有的文件信息
    List<File> flieList();


    //----------------------------------------------------------------------------//


    //下载后---->下载次数+1  +
    int updateDownloads(@Param("name") String name);

    //修改文件状态
    int updateStatus(Integer flag) ;

    //更新文件信息
    int updateFileDetail(File record);

    /**
     * 根据关键字查找CourseID
     * @param keyword
     * @param column
     * @return CourseID
     */
    List<Integer> selectByKeyWordAndColumn(@Param("keyword")String keyword,
                                           @Param("column")String column);


    /**
     * 根据CourseIDs查找File文件
     * @param courseIds
     * @param keyword
     * @param column
     * @return 返回File
     */
    List<File>  selectFileByCourseId( @Param("courseIds") List<Integer> courseIds, @Param("keyword") String keyword, @Param("column") String column);

}
