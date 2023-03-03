/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.puglia.arpa.arpashelf.filters.SetCharacterEncodingFilter
 *  javax.servlet.Filter
 *  javax.servlet.FilterChain
 *  javax.servlet.FilterConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 */
package it.puglia.arpa.arpashelf.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter
implements Filter {
    protected String encoding = null;
    protected FilterConfig filterConfig = null;
    protected boolean ignore = true;

    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String encoding;
        if ((this.ignore || request.getCharacterEncoding() == null) && (encoding = this.selectEncoding(request)) != null) {
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        this.ignore = value == null ? true : (value.equalsIgnoreCase("true") ? true : value.equalsIgnoreCase("yes"));
    }

    protected String selectEncoding(ServletRequest request) {
        return this.encoding;
    }
}

