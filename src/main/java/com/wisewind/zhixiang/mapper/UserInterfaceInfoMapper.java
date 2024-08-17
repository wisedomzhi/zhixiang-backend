package com.wisewind.zhixiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisewind.zhixiangcommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author ffz
* @description 针对表【user_interface_info(用户接口调用关系表)】的数据库操作Mapper
* @createDate 2024-08-10 16:02:12
* @Entity com.wisewind.zhixiang.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> sumByInterfaceId(Integer limit);

}




