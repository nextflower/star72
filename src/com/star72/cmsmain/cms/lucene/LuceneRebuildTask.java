package com.star72.cmsmain.cms.lucene;


/**
 * 索引重建任务
 * 
 * @author wz
 *
 */
public class LuceneRebuildTask {
	
	private static LuceneRebuildTask task = new LuceneRebuildTask();
	
	private LuceneRebuildTask() {
	}
	
	public static LuceneRebuildTask getRebuildTask() {
		return task;
	}
	
	/**
	 * 开启任务
	 * @param totalCount
	 */
	public synchronized void start() {
		setOnUse(true);
	}
	
	/**
	 * 清空状态
	 */
	public synchronized void clear() {
		setOnUse(false);
	}

	/**
	 * 是否正在执行
	 */
	private boolean onUse = false;
	
	
	public boolean isOnUse() {
		return onUse;
	}
	
	private void setOnUse(boolean onUse) {
		this.onUse = onUse;
	}
	
}
