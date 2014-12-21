package com.star72.cmsmain.cms.dao.main;

import com.star72.cmsmain.cms.entity.main.ChannelExt;
import com.star72.cmsmain.common.hibernate3.Updater;

public interface ChannelExtDao {
	public ChannelExt save(ChannelExt bean);

	public ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}