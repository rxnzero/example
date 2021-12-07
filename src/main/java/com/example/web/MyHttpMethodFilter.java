package com.example.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.HiddenHttpMethodFilter;

public class MyHttpMethodFilter extends HiddenHttpMethodFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		HttpServletRequest requestToUse = request;

		String method = requestToUse.getMethod();
		if (method.equalsIgnoreCase("delete") || method.equalsIgnoreCase("put")) {
			method = "POST";
		}

		requestToUse = new HttpMethodRequestWrapper(request, method);

		filterChain.doFilter(requestToUse, response);
	}

	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
			super(request);
			this.method = method;
		}

		public String getMethod() {
			return this.method;
		}
	}
}