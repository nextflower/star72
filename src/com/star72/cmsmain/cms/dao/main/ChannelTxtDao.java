package com.star72.cmsmain.cms.dao.main;

import com.star72.cmsmain.cms.entity.main.ChannelTxt;
import com.star72.cmsmain.common.hibernate3.Updater;

public interface ChannelTxtDao {
	public ChannelTxt findById(Integer id);

	public ChannelTxt save(ChannelTxt bean);

	public ChannelTxt updateByUpdater(Updater<ChannelTxt> updater);
}