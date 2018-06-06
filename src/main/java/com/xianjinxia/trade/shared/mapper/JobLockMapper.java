package com.xianjinxia.trade.shared.mapper;

import org.apache.ibatis.annotations.Param;

import com.xianjinxia.trade.shared.domain.JobLock;

public interface JobLockMapper {
	
    int insert(JobLock jobLock);
    
    JobLock selectByJobName(String jobName);
   
    int update(@Param("id") long id,@Param("ukToken") long ukToken,@Param("jobName")String jobName);

}
