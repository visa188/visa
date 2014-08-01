package com.visa.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.visa.common.constant.Constant;

/**
 * @author zxwu
 */
public class LoginFilter implements Filter {

    private String redirectJs;
    private List<String> excludeUrlList;

    @Override
    public void init(FilterConfig config) throws ServletException {
        String loginUrl = config.getInitParameter("loginUrl");
        redirectJs = "<script type='text/javascript'>if (parent) {parent.location.href='" + loginUrl
                + "'} else {window.location.href='" + loginUrl + "'}</script>";
        String excludeUrl = config.getInitParameter("excludeUrl");
        if (excludeUrl != null) {
            excludeUrlList = Arrays.asList(excludeUrl.split(","));
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        String servletPath = httpReq.getRequestURI();
        if (!isExclude(servletPath)) {
            HttpSession session = httpReq.getSession(false);
            if (session == null || session.getAttribute(Constant.SESSION_USER) == null) {
                PrintWriter writer = httpRes.getWriter();
                writer.write(redirectJs);
                writer.flush();
                writer.close();
                return;
            }
        }

        chain.doFilter(httpReq, httpRes);
    }

    @Override
    public void destroy() {
    }

    private boolean isExclude(String url) {
        if (excludeUrlList == null) {
            // If excludeUrlList is null, then any url will not be excluded.
            return false;
        }

        for (String excludeUrl : excludeUrlList) {
            if (excludeUrl.equals(url)) {
                return true;
            }
        }
        return false;
    }

}
