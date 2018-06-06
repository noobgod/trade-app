package com.xianjinxia.trade.shared.filter;

import com.xianjinxia.logcenter.http.filter.CatHttpFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName="LogCenterFilter",urlPatterns="/*")
public class LogCenterFilter extends CatHttpFilter {
}
