package com.xianjinxia.trade.platform.schedule.job;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.Collection;

public abstract class PagebleScanCollectionJob<M, K extends Collection<M>> extends AbstractJob<K> {

	@Override
	protected boolean isContinue(JobRuntimeContext jrc, K item) {
		PagebleScanCollectionJobContext context = (PagebleScanCollectionJobContext)jrc;
		if(this.threshold() > 0){
			if(context.fetchedRecords >= this.threshold()){
				jrc.isAdvancedOver = true;
				return false;
			}
		}
		if(context.currentPageIndex < context.endPageIndex){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected K fetchItem(
			JobRuntimeContext context) {
		PagebleScanCollectionJobContext cnt = (PagebleScanCollectionJobContext)context;
		Page<M> item = null;
		PageHelper.startPage(cnt.currentPageIndex,this.pageSize());
		item =  this.fetch();
		cnt.currentPageIndex ++;
		if(item != null){
			cnt.fetchedRecords += item.size();
			cnt.currentFetchedRecords = item.size();
			cnt.endPageIndex = item.getPages();
		}else{
			cnt.currentFetchedRecords = 0;
			return null;
		}
		return (K)item.getResult();
	}

	@Override
	protected JobRuntimeContext createContext() {
		JobRuntimeContext context = new PagebleScanCollectionJobContext(this.pageSize());
		return context;
	}
	
	public abstract int pageSize();
	
	public abstract Page<M> fetch();
	

}

class PagebleScanCollectionJobContext extends JobRuntimeContext{
	int startPageIndex;
	int endPageIndex;
	int currentPageIndex;
	int pageSize;
	int fetchedRecords = 0;
	public PagebleScanCollectionJobContext(int pageSize) {
		super();
		this.startPageIndex = 1;
		this.pageSize = pageSize;
		this.currentPageIndex = 1;
		this.endPageIndex = 2;
	}

}
