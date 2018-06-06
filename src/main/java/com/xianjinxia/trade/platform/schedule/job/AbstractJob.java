package com.xianjinxia.trade.platform.schedule.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;
import com.xianjinxia.trade.shared.domain.JobLock;
import com.xianjinxia.trade.shared.mapper.JobLockMapper;


@Service
public abstract class AbstractJob<K>{
	
	private static Logger logger = LoggerFactory.getLogger(AbstractJob.class);

	@Autowired
	private JobLockMapper jobLockMapper;

	public boolean before(){
		return true;
	}
	
	public void after(){
		
	}

	public void execute() {
		String lockKey = getJobLockKey();
		long lockId = 0L;
		try {
			lockId = tryLock(lockKey);//加锁
			doExecute();
		}catch (Exception e) {
			logger.error("the job named {} execute fail,error message is {}", this.getClass().getSimpleName(),e.getMessage());
		}finally {
			if(lockId>0) {
				releaseLock(lockId, lockKey);
			}
		}

	}

	private boolean doExecute() {
		if(!this.before()){
			return false;
		}
		//JobRunLogDTO jobRunDto = this.logRepository.insertPtpJobRunLog(new PtpJobRunLogDTO(this.getClass().getName()));
		JobRuntimeContext jrc = this.createContext();
		K item = null;
		while(this.isContinue(jrc, item)){
			item = this.fetchItem(jrc);
			try{
				this.process(item);
				jrc.proccesedRecds += jrc.currentFetchedRecords;
				this.log(item,null);
			}catch(Throwable e){
				jrc.failedRecds += jrc.currentFetchedRecords;
                logger.error("the job named {} doExecute fail,error message is {}", this.getClass().getSimpleName(),e.getMessage());
			}finally{
				this.after();
			}
		}
		//jobRunDto.setTotalCounts(jrc.proccesedRecds);
		//this.logRepository.updatePtpJobRunLog(jobRunDto.id(),JobStatus.SUCCESS.getCode(), jrc.proccesedRecds, jrc.failedRecds);
		
		if(jrc.failedRecds>0 || jrc.isAdvancedOver){
			return false;
		}else{
			return true;
		}
	}

	
	protected String getJobLockKey(){
		return this.getClass().getSimpleName();
	}
	
	/**threshold that's how many records can be handled
	 * the returned number(X) is not precise but the range which is from X to X+pageSize 
	 * @return
	 */
	public int threshold(){
		return -1;
	}
	
	protected abstract boolean isContinue(JobRuntimeContext jrc, K item);
	
	/**Business logic here which is implemented by users
	 * @param item
	 */
	public abstract void process(K item);
	
	/** Handle logs about job processing
	 * @param item
	 * @param e
	 */
	public void log(K item,Throwable e){
		if(e == null){
			//Logger.debug(this.getClass(), "Job execute successfully!");
		}else{
			//Logger.warn(this.getClass(), "Job execute with Exception!",new Exception(e));
		}
		
	}
	
	protected abstract K fetchItem(JobRuntimeContext context);
	
	protected abstract JobRuntimeContext createContext();
	
	/**
	 * 定时任务加锁
	 * @return
	 */
	public long tryLock(String lockKey) {
		JobLock jobLock = new JobLock();
		jobLock.setJobName(lockKey);
		jobLock.setUkToken(0L);
		jobLockMapper.insert(jobLock);
		return jobLock.getId();
	}

	/***
	 * 定时任务释放锁
	 */
	public void releaseLock(long lockId ,String lockKey) {
		int num = jobLockMapper.update(lockId,lockId,lockKey);
		if(num == 0) {
			logger.error("release lock fail,need check,lockId:{},lockKey:{}",lockId,lockKey);
		}else {
			logger.info("job {} releaseLock success",this.getClass().getSimpleName());
		}
	}

}

abstract class JobRuntimeContext{
	JobRuntimeContext(){
	}
	boolean isAdvancedOver = false;
	long fetchedRecords = 0;
	long proccesedRecds = 0;
	long failedRecds = 0;
	long currentFetchedRecords = 0;
}