package com.star72.cmsmain.core.dao;

import java.util.List;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.core.entity.Ftp;

public interface FtpDao {
	public List<Ftp> getList();

	public Ftp findById(Integer id);

	public Ftp save(Ftp bean);

	public Ftp updateByUpdater(Updater<Ftp> updater);

	public Ftp deleteById(Integer id);
}