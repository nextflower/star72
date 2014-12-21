package com.star72.cmsmain.cms.entity.assist;

import com.star72.cmsmain.cms.entity.assist.base.BaseCmsReceiverMessage;
import com.star72.cmsmain.common.util.StrUtils;

public class CmsReceiverMessage extends BaseCmsReceiverMessage {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsReceiverMessage() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsReceiverMessage(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsReceiverMessage(java.lang.Integer id,
			com.star72.cmsmain.core.entity.CmsUser msgReceiverUser,
			com.star72.cmsmain.core.entity.CmsUser msgSendUser,
			com.star72.cmsmain.core.entity.CmsSite site, java.lang.String msgTitle,
			java.lang.String msgContent, java.util.Date sendTime,
			boolean msgStatus, java.lang.Integer msgBox) {

		super(id, msgReceiverUser, msgSendUser, site, msgTitle, msgContent,
				sendTime, msgStatus, msgBox);
	}

	public CmsReceiverMessage(CmsMessage message) {
		super(message.getId(), message.getMsgReceiverUser(), message
				.getMsgSendUser(), message.getSite(), message.getMsgTitle(),
				message.getMsgContent(), message.getSendTime(), message
						.getMsgStatus(), message.getMsgBox());
	}
	public String getTitleHtml() {
		return StrUtils.txt2htm(getMsgTitle());
	}
	public String getContentHtml() {
		return StrUtils.txt2htm(getMsgContent());
	}

	/* [CONSTRUCTOR MARKER END] */

}