package com.star72.cmsmain.cms.manager.main;

import com.star72.cmsmain.cms.entity.main.Channel;
import com.star72.cmsmain.cms.entity.main.ChannelExt;

public interface ChannelExtMng {
	public ChannelExt save(ChannelExt ext, Channel channel);

	public ChannelExt update(ChannelExt ext);
}