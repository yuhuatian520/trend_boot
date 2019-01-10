package com.yl.trend.filter;

import com.yl.trend.entity.Admin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/back/*")
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		//获取Session
		HttpSession session = req.getSession();
		
		Admin admin = (Admin)session.getAttribute("userinfo");
		if(uri.contains("login")) {
			chain.doFilter(request, response);
		}else if(admin!=null) {
			chain.doFilter(request, response);
			
		}else {
			req.getRequestDispatcher("/back/login").forward(request, response);
		}
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
