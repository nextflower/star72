package com.star72.cmsmain.core.dao;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.core.entity.DbFile;

public interface DbFileDao {
	public DbFile findById(String id);

	public DbFile save(DbFile bean);

	public DbFile updateByUpdater(Updater<DbFile> updater);

	public DbFile deleteById(String id);
}