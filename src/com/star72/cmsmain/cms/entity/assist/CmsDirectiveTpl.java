package com.star72.cmsmain.cms.entity.assist;

import com.star72.cmsmain.cms.entity.assist.base.BaseCmsDirectiveTpl;



public class CmsDirectiveTpl extends BaseCmsDirectiveTpl {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsDirectiveTpl () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsDirectiveTpl (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsDirectiveTpl (
		java.lang.Integer id,
		com.star72.cmsmain.core.entity.CmsUser user,
		java.lang.String name) {

		super (
			id,
			user,
			name);
	}

/*[CONSTRUCTOR MARKER END]*/


}