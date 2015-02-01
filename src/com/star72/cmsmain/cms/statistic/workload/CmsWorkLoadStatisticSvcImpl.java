package com.star72.cmsmain.cms.statistic.workload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.star72.cmsmain.cms.entity.main.Channel;
import com.star72.cmsmain.cms.manager.main.ChannelMng;
import com.star72.cmsmain.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticDateKind;
import com.star72.cmsmain.cms.statistic.workload.CmsWorkLoadStatistic.CmsWorkLoadStatisticGroup;
import com.star72.cmsmain.core.entity.CmsUser;
import com.star72.cmsmain.core.manager.CmsUserMng;
import com.star72.common.utils.StarDateUtils;

@Service
@Transactional(readOnly = true)
public class CmsWorkLoadStatisticSvcImpl implements CmsWorkLoadStatisticSvc {
	public List<CmsWorkLoadStatistic> statistic(Integer channelId,
			Integer reviewerId, Integer authorId, Date beginDate, Date endDate,
			CmsWorkLoadStatisticGroup group, CmsWorkLoadStatisticDateKind kind) {
		Long count;
		CmsWorkLoadStatistic bean;
		Channel channel = null;
		CmsUser author = null;
		CmsUser reviewer = null;
		Date begin = beginDate;
		Date end = null;
		int add = 1;
		if (channelId != null) {
			channel = channelMng.findById(channelId);
		}
		if (reviewerId != null) {
			reviewer = userMng.findById(reviewerId);
		}
		if (authorId != null) {
			author = userMng.findById(authorId);
		}
		begin = getNextDate(group, beginDate, 0);
		end = getNextDate(group, beginDate, add);
		List<CmsWorkLoadStatistic> list = new ArrayList<CmsWorkLoadStatistic>();
		while (end.before(endDate)) {
			count = dao.statistic(channelId, reviewerId, authorId, begin, end,
					kind);
			bean = new CmsWorkLoadStatistic(channel, author, reviewer, begin,
					count);
			list.add(bean);
			begin = end;
			end = getNextDate(group, beginDate, ++add);
		}
		count = dao.statistic(channelId, reviewerId, authorId, begin, end, kind);
		bean = new CmsWorkLoadStatistic(channel, author, reviewer, begin, count);
		list.add(bean);
		return list;
	}

	private Date getNextDate(CmsWorkLoadStatisticGroup group, Date date,
			int amount) {
		Date result = null;
		if (group == CmsWorkLoadStatisticGroup.year) {
			result = StarDateUtils.getSpecficYearStart(date, amount);
		} else if (group == CmsWorkLoadStatisticGroup.month) {
			result = StarDateUtils.getSpecficMonthStart(date, amount);
		} else if (group == CmsWorkLoadStatisticGroup.week) {
			result = StarDateUtils.getSpecficWeekStart(date, amount);
		} else if (group == CmsWorkLoadStatisticGroup.day) {
			result = StarDateUtils.getSpecficDateStart(date, amount);
		}
		return result;
	}

	@Autowired
	private CmsWorkLoadStatisticDao dao;
	@Autowired
	private CmsUserMng userMng;
	@Autowired
	private ChannelMng channelMng;
}
