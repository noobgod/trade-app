package com.xianjinxia.trade.platform.schedule.job;


import java.util.Collection;

import com.xianjinxia.trade.platform.schedule.dto.BaseDTO;
import com.xianjinxia.trade.platform.schedule.dto.MinMaxIdDTO;

public abstract class MinMaxIDScanCollectionJob<M extends BaseDTO, K extends Collection<M>> extends AbstractJob<K> {

	@Override
	protected K fetchItem(JobRuntimeContext jrc) {
		MinMaxIdScanJobContext context = (MinMaxIdScanJobContext)jrc;
		K item = null;
		if(context.currentIndex+this.fetchSize() > context.endIndex){
			item = this.fetch(context.currentIndex, context.endIndex+1);
			context.currentIndex = context.endIndex+1;
		}else{
			item = this.fetch(context.currentIndex, context.currentIndex+this.fetchSize());
			context.currentIndex = context.currentIndex+this.fetchSize();
		}
		if(item != null){
			context.fetchedRecords += item.size();
			context.currentFetchedRecords = item.size();
		}else{
			context.currentFetchedRecords = 0;
		}
		return item;
	}
	
	@Override
	protected JobRuntimeContext createContext(){
		MinMaxIdDTO dto = this.range();
		JobRuntimeContext context = null;
		if(dto == null){
			context = new MinMaxIdScanJobContext(0,0,0,this.fetchSize());
		}else{
			//Logger.info(this,String.format("createContext min:%s max:%s", dto.getMinId(), dto.getMaxId()));
			if(null == dto.getMinId() || null == dto.getMaxId()){
				context = new MinMaxIdScanJobContext(0,0,0,this.fetchSize());
			}else{
				context = new MinMaxIdScanJobContext(dto.getMinId(),dto.getMaxId(),dto.getMinId(),this.fetchSize());
			}
		}
		
		return context;
	}
	
	@Override
	protected boolean isContinue(JobRuntimeContext jrc, K item){
		MinMaxIdScanJobContext context = (MinMaxIdScanJobContext)jrc;
		if(this.threshold() > 0){
			if(context.fetchedRecords >= this.threshold()){
				jrc.isAdvancedOver = true;
				return false;
			}
		}
		if(context.currentIndex >  context.endIndex){
			return false;
		}
		return true;
	}
	public int fetchSize(){
		return 1000;
	}
	
	public abstract MinMaxIdDTO range();
	
	/**[begin, end)
	 * @param begin
	 * @param end
	 * @return
	 */
	public abstract K fetch(long begin,long end);
	

}

class MinMaxIdScanJobContext extends JobRuntimeContext{
	long startIndx;
	long endIndex;
	long currentIndex;
	long step;
	public MinMaxIdScanJobContext(long startIndx, long endIndex,
			long currentIndex, long step) {
		super();
		this.startIndx = startIndx;
		this.endIndex = endIndex;
		this.currentIndex = currentIndex;
		this.step = step;
	}
}